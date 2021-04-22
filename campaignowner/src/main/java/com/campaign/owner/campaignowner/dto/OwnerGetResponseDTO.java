package com.campaign.owner.campaignowner.dto;

import com.campaign.owner.campaignowner.entity.Campaign;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerGetResponseDTO {
    private String id;

    @Schema(description = "Name of Owner", required = true)
    private String name;

    @Schema(description = "Contact", required = true)
    private String contact;

    @Schema(required = true)
    private List<String> channels;

    @Schema(description = "Status can be Active, Suspended, Terminated", required = true)
    private String state;

    private Campaign campaign;
}
