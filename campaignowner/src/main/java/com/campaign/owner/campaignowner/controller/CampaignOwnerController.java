package com.campaign.owner.campaignowner.controller;

import com.campaign.owner.campaignowner.dto.OwnerDeleteResponseDTO;
import com.campaign.owner.campaignowner.dto.OwnerRequestDTO;
import com.campaign.owner.campaignowner.dto.OwnerGetResponseDTO;
import com.campaign.owner.campaignowner.dto.OwnerUpdateRequestDTO;
import com.campaign.owner.campaignowner.entity.Campaign;
import com.campaign.owner.campaignowner.dto.CampaignGetResponseDTO;
import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.service.CampaignOwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/v1/owners")
@Tag(name = "CampaignOwners", description = "Campaign Owner Management")
public class CampaignOwnerController {

    @Autowired
    private CampaignOwnerService campaignOwnerService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    @Operation(summary = "Create campaign owners" , description = "API to create new campaign owners for Campaign", method = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Owner created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<OwnerGetResponseDTO> createOwner(@Valid @RequestBody OwnerRequestDTO ownerRequestDTO){
        Owner actualOwner = getObjectMapper().convertValue(ownerRequestDTO, Owner.class);
        Owner owner = this.campaignOwnerService.saveOwner(actualOwner);
        OwnerGetResponseDTO ownerGetResponseDTO = getObjectMapper().convertValue(owner, OwnerGetResponseDTO.class);
        return new ResponseEntity<>(ownerGetResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get owner by id" , description = "API to fetch owner with a given id for Campaign", method = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<OwnerGetResponseDTO> getOwner(@PathVariable("id") String id){
        Owner owner  = campaignOwnerService.getOwnerDetails(id);
        return new ResponseEntity<>(formatResponse(owner), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete specific owner" , description = "API to delete owner with given id for Campaign", method = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<OwnerDeleteResponseDTO> removeOwner(@PathVariable("id") String id) {
        long noOfDocument = this.campaignOwnerService.deleteOwner(id);
        OwnerDeleteResponseDTO ownerDeleteResponseDTO = new OwnerDeleteResponseDTO();
        ownerDeleteResponseDTO.setNoOfDocument(noOfDocument);
        return new ResponseEntity<>(ownerDeleteResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing owner" , description = "API to update owner details for CMP", method = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public ResponseEntity<OwnerGetResponseDTO> updateOwner(@RequestBody OwnerUpdateRequestDTO ownerUpdateRequestDTO, @PathVariable("id") String id){
        Owner actualOwner = new Owner();
        actualOwner.setId(id);
        BeanUtils.copyProperties(ownerUpdateRequestDTO, actualOwner);
        Owner owner = this.campaignOwnerService.updateOwner(actualOwner, id);
        return new ResponseEntity<>(formatResponse(owner), HttpStatus.OK);
    }

    @PutMapping("/{id}/campaigns/{campaign-id}")
    @Operation(summary = "Associate owner with Campaign" , description = "API to associate owner with Campaign", method = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public ResponseEntity<OwnerGetResponseDTO> associateCampaign(@PathVariable("id") String id, @PathVariable("campaign-id") String campaignId){
        URI targetUrl= UriComponentsBuilder.fromUriString("http://campaign/api/v1/campaigns")
                .path("/"+campaignId)
                .build()
                .encode()
                .toUri();
        ResponseEntity<CampaignGetResponseDTO> campaignDTO = restTemplate.getForEntity(targetUrl, CampaignGetResponseDTO.class);
        if(Optional.ofNullable(campaignDTO).isPresent()) {
            Campaign campaign = getObjectMapper().convertValue(campaignDTO.getBody(), Campaign.class);
            Owner owner = campaignOwnerService.updateCampaign(campaign, id);
            return new ResponseEntity<>(formatResponse(owner), HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("Campaign-Id not found");
        }
    }

    @DeleteMapping("/{id}/campaigns/{campaign-id}")
    @Operation(summary = "Remove owner for Campaign" , description = "API to remove the owner for Campaign", method = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")})
    public ResponseEntity<OwnerGetResponseDTO> removeCampaign(@PathVariable("id") String id, @PathVariable("campaign-id") String campaignId){
        URI targetUrl= UriComponentsBuilder.fromUriString("http://campaign/api/v1/campaigns")
                .path("/"+campaignId)
                .build()
                .encode()
                .toUri();
        restTemplate.getForEntity(targetUrl, CampaignGetResponseDTO.class);
        Owner owner = campaignOwnerService.removeCampaign(id);
        return new ResponseEntity<>(formatResponse(owner), HttpStatus.OK);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    private OwnerGetResponseDTO formatResponse(Owner owner) {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.convertValue(owner, OwnerGetResponseDTO.class);
    }
}
