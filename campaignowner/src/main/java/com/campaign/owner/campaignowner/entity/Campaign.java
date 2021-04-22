package com.campaign.owner.campaignowner.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Campaign {
    @Schema(description = "Unique identifier for Campaign", required = true)
    @Id
    private String id;

    @NotNull
    @Schema(description = "Name for Campaign", required = true)
    private String name;

    @Schema(description = "Status can be Active, Suspended, Terminated", required = true)
    private String state;

    @NotNull
    @Schema(description = "Campaign start date", required = true)
    @JsonProperty("startDate")
    private LocalDate startDate;

    @NotNull
    @Schema(description = "Campaign end date", required = true)
    @JsonProperty("endDate")
    private LocalDate endDate;

    @NotNull
    @Schema(required = true)
    private List<String> channels;

    @Schema(required = true)
    private Double price;

    private String category;

    List<Subscription> subscriptions;

}
