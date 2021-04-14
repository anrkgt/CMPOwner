package com.campaign.owner.campaignowner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CampaignOwnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignOwnerApplication.class, args);
	}

}
