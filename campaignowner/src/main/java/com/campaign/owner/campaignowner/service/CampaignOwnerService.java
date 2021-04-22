package com.campaign.owner.campaignowner.service;

import com.campaign.owner.campaignowner.entity.Campaign;
import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.repository.impl.CampaignOwnerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampaignOwnerService {

    public static final String OWNER_NOT_FOUND = "Owner not found with id::%s";
    @Autowired
    private CampaignOwnerRepositoryImpl campaignOwnerRepositoryImpl;

    public CampaignOwnerService(CampaignOwnerRepositoryImpl campaignOwnerRepositoryImpl) {
        this.campaignOwnerRepositoryImpl = campaignOwnerRepositoryImpl;
    }

    public Owner saveOwner(Owner owner) {
        return this.campaignOwnerRepositoryImpl.save(owner);
    }

    public Owner getOwnerDetails(String id) {
        Owner owner = this.campaignOwnerRepositoryImpl.findById(id);
        return Optional.ofNullable(owner).orElseThrow(() -> new IllegalArgumentException(String.format(OWNER_NOT_FOUND, id)));
    }

    public long deleteOwner(String id) {
        Owner owner = getOwnerDetails(id);
        return this.campaignOwnerRepositoryImpl.remove(owner);
    }

    public Owner updateOwner(Owner owner, String id) {
        Owner existingOwner =  getOwnerDetails(id);
        owner.setName(existingOwner.getName());
        return this.campaignOwnerRepositoryImpl.save(owner);
    }

    public Owner updateCampaign(Campaign campaign, String id) {
        getOwnerDetails(id);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("campaign", campaign);
        return this.campaignOwnerRepositoryImpl.updateCampaign(query, update, Owner.class);

    }

    public Owner removeCampaign(String id) {
        getOwnerDetails(id);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().unset("campaign");
        return this.campaignOwnerRepositoryImpl.updateCampaign(query, update, Owner.class);
    }
}
