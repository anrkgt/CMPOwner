package com.campaign.owner.campaignowner.repository.impl;

import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.template.TemplateMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CampaignOwnerRepositoryImpl {
    @Autowired
    private TemplateMongo templateMongo;

    public CampaignOwnerRepositoryImpl(TemplateMongo templateMongo) {
        this.templateMongo = templateMongo;
    }

    public Owner save(Owner entity) {
        templateMongo.getTemplateMongo().save(entity);
        return entity;
    }

    public Owner findById(String id) {
        return templateMongo.getTemplateMongo().findById(id, Owner.class);
    }

    public Owner remove(Owner owner) {
        templateMongo.getTemplateMongo().remove(owner);
        return  owner;
    }

    public List<Owner> findAll() {

        return templateMongo.getTemplateMongo().findAll(Owner.class);
    }
}
