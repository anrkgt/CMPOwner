package com.campaign.owner.campaignowner.template;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile("!test")
@Configuration
public class TemplateMongo {
    public MongoTemplate getTemplateMongo() {
        String connectionUrl = "mongodb://localhost:27017/CampaignOwners";
        return new MongoTemplate(MongoClients.create(String.format(connectionUrl)), "CampaignOwners");
    }
}
