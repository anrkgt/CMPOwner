package com.campaign.owner.campaignowner.service;

import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.repository.impl.CampaignOwnerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveOwner(Owner owner) {
        this.campaignOwnerRepositoryImpl.save(owner);
    }

    public Owner getOwnerDetails(String id) {
        Owner owner = this.campaignOwnerRepositoryImpl.findById(id);
        return Optional.ofNullable(owner).orElseThrow(() -> new IllegalArgumentException(String.format(OWNER_NOT_FOUND, id)));
    }

    public void deleteOwner(String id) {
        Owner owner = getOwnerDetails(id);
        this.campaignOwnerRepositoryImpl.remove(owner);

    }

    public void updateOwner(Owner owner, String id) {
        Owner existingOwner =  getOwnerDetails(id);
        owner.setName(existingOwner.getName());
        this.campaignOwnerRepositoryImpl.save(owner);
    }
}
