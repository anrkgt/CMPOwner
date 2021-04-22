package com.campaign.owner.campaignowner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private String subscriptionId;

    private String userId;

    private LocalDate startDate;

    private LocalDate endDate;

}
