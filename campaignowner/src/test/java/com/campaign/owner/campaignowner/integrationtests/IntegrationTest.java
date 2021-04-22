package com.campaign.owner.campaignowner.integrationtests;

import com.campaign.owner.campaignowner.dto.OwnerRequestDTO;
import com.campaign.owner.campaignowner.dto.OwnerGetResponseDTO;
import com.campaign.owner.campaignowner.dto.OwnerUpdateRequestDTO;
import com.campaign.owner.campaignowner.repository.EmbeddedMongoDbIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(EmbeddedMongoDbIntegrationTest.class)
class IntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    void testCreateOwner() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        ownerRequestDTO.setState("Active");
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        //When
        ResponseEntity<String> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, String.class);
        //Then
        assertEquals(HttpStatus.OK, createOwner_Response.getStatusCode());
        assertThat(createOwner_Response).isNotNull();
        assertThat(createOwner_Response.getBody()).isNotNull();
        assertThat(createOwner_Response.getBody()).isNotBlank();
    }

    @Test
    void testGetOwner() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        ownerRequestDTO.setState("Active");
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        ResponseEntity<OwnerGetResponseDTO> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, OwnerGetResponseDTO.class);
        String ownerId = createOwner_Response.getBody().getId();

        //When
        ResponseEntity<OwnerGetResponseDTO> getOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners/" +ownerId , HttpMethod.GET,null, OwnerGetResponseDTO.class);

        //Then
        OwnerGetResponseDTO db_owner = getOwner_Response.getBody();
        assertEquals(HttpStatus.OK, getOwner_Response.getStatusCode());
        assertThat(getOwner_Response).isNotNull();
        assertThat(getOwner_Response.getBody()).isNotNull();
        assertEquals(HttpStatus.OK, getOwner_Response.getStatusCode());
        assertThat(getOwner_Response).isNotNull();
        assertThat(getOwner_Response.getBody()).isNotNull();
        assertEquals(db_owner.getName(), ownerRequestDTO.getName());
        assertEquals(db_owner.getChannels(), ownerRequestDTO.getChannels());
        assertEquals(db_owner.getContact(), ownerRequestDTO.getContact());
        assertEquals(db_owner.getState(), ownerRequestDTO.getState());
        assertEquals(db_owner.getId(), ownerId);
    }

    @Test
    void testDeleteOwner() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        ownerRequestDTO.setState("Active");
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        ResponseEntity<OwnerGetResponseDTO> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, OwnerGetResponseDTO.class);
        String ownerId = createOwner_Response.getBody().getId();

        //When
        ResponseEntity<Void> deleteOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners/" +ownerId , HttpMethod.DELETE,null, Void.class);

        //Then
        assertEquals(HttpStatus.OK, deleteOwner_Response.getStatusCode());
    }

    @Test
    void testUpdateOwner() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        ownerRequestDTO.setState("Active");
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        ResponseEntity<OwnerGetResponseDTO> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, OwnerGetResponseDTO.class);
        String ownerId = createOwner_Response.getBody().getId();

        OwnerUpdateRequestDTO ownerUpdateDto = new OwnerUpdateRequestDTO();
        ownerUpdateDto.setState("Suspended");
        ownerUpdateDto.setContact("0987654321");
        ownerUpdateDto.setChannels(Arrays.asList("Amazon Prime"));
        HttpEntity<OwnerUpdateRequestDTO> ownerUpdateEntity = new HttpEntity<>(ownerUpdateDto);
        //When
        ResponseEntity<OwnerGetResponseDTO> updateOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners/" +ownerId , HttpMethod.PUT,ownerUpdateEntity, OwnerGetResponseDTO.class);
        //Then
        ResponseEntity<OwnerGetResponseDTO> getOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners/" +ownerId , HttpMethod.GET,null, OwnerGetResponseDTO.class);
        OwnerGetResponseDTO db_owner = getOwner_Response.getBody();

        assertEquals(HttpStatus.OK, createOwner_Response.getStatusCode());
        assertEquals(HttpStatus.OK, updateOwner_Response.getStatusCode());
        assertEquals(HttpStatus.OK, getOwner_Response.getStatusCode());

        assertEquals(ownerRequestDTO.getName(), db_owner.getName());
        assertEquals(ownerUpdateDto.getChannels(), db_owner.getChannels());
        assertEquals(ownerUpdateDto.getContact(), db_owner.getContact());
        assertEquals(ownerUpdateDto.getState(), db_owner.getState());
        assertEquals(ownerId, db_owner.getId());
    }

    @Test()
    void testUpdateOwner_With_Invalid_Id() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        ownerRequestDTO.setState("Active");
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        ResponseEntity<String> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, String.class);

        OwnerUpdateRequestDTO ownerUpdateDto = new OwnerUpdateRequestDTO();
        ownerUpdateDto.setState("Suspended");
        ownerUpdateDto.setContact("0987654321");
        ownerUpdateDto.setChannels(Arrays.asList("Amazon Prime"));
        HttpEntity<OwnerUpdateRequestDTO> owner_http_entity = new HttpEntity<>(ownerUpdateDto);
        //When
        ResponseEntity<Void> updateOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners/" +"999999999" , HttpMethod.PUT,owner_http_entity, Void.class);
        //Then
        assertEquals(HttpStatus.OK, createOwner_Response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, updateOwner_Response.getStatusCode());
    }

    @Test
    void testCreateOwner_withInvalidState() {
        //Given
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setName("Anisa");
        ownerRequestDTO.setContact("gmail@anisa.com");
        ownerRequestDTO.setChannels(Arrays.asList("TV", "Netflix"));
        HttpEntity<OwnerRequestDTO> ownerEntity = new HttpEntity<>(ownerRequestDTO);
        //When
        ResponseEntity<String> createOwner_Response = this.testRestTemplate.withBasicAuth("admin", "test123")
                .exchange("/api/v1/owners", HttpMethod.POST,ownerEntity, String.class);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, createOwner_Response.getStatusCode());
    }
}
