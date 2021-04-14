package com.campaign.owner.campaignowner.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Campaign-Owners")
public class Owner {
    @Schema(description = "Unique identifier for Owner", required = true)
    @Id
    private String id;

    @Schema(description = "Name of Owner", required = true)
    private String name;

    @Schema(description = "Contact", required = true)
    private String contact;

    @Schema(required = true)
    private List<String> channels;

    @Schema(description = "Status can be Active, Suspended, Terminated", required = true)
    private String state;
}
