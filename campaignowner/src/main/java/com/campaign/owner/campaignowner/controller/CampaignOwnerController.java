package com.campaign.owner.campaignowner.controller;

import com.campaign.owner.campaignowner.dto.OwnerRequestDTO;
import com.campaign.owner.campaignowner.dto.OwnerUpdateRequestDTO;
import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.service.CampaignOwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.validation.Valid;
@Validated
@RestController
@RequestMapping("/")
@Tag(name = "CampaignOwners", description = "Campaign Owner Management")
public class CampaignOwnerController {

    @Autowired
    private CampaignOwnerService campaignOwnerService;

    @PostMapping("createOwner")
    @Operation(summary = "Create campaign owners" , description = "API to create new campaign owners for Campaign", method = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Owner created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> createOwner(@Valid @RequestBody OwnerRequestDTO ownerRequestDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        Owner actualOwner = objectMapper.convertValue(ownerRequestDTO, Owner.class);
        this.campaignOwnerService.saveOwner(actualOwner);
        return new ResponseEntity<>(actualOwner.getId(), HttpStatus.OK);
    }

    @GetMapping("getOwner/{id}")
    @Operation(summary = "Get owner by id" , description = "API to fetch owner with a given id for Campaign", method = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<Owner> getOwner(@PathVariable("id") String id){
        return new ResponseEntity<>(campaignOwnerService.getOwnerDetails(id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOwner/{id}")
    @Operation(summary = "Delete specific owner" , description = "API to delete owner with given id for Campaign", method = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public void removeOwner(@PathVariable("id") String id) {
        this.campaignOwnerService.deleteOwner(id);
    }

    @PutMapping("/updateOwner/{id}")
    @Operation(summary = "Update existing owner" , description = "API to update owner details for CMP", method = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found"),
    })
    public void updateOwner(@RequestBody OwnerUpdateRequestDTO ownerUpdateRequestDTO, @PathVariable("id") String id){
        Owner actualOwner = new Owner();
        actualOwner.setId(id);
        BeanUtils.copyProperties(ownerUpdateRequestDTO, actualOwner);
        this.campaignOwnerService.updateOwner(actualOwner, id);
    }
}
