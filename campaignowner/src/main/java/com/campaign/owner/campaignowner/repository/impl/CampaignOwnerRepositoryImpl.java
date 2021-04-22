package com.campaign.owner.campaignowner.repository.impl;

import com.campaign.owner.campaignowner.entity.Owner;
import com.campaign.owner.campaignowner.template.TemplateMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public long remove(Owner owner) {
       return templateMongo.getTemplateMongo().remove(owner).getDeletedCount();
    }

    public List<Owner> findAll() {
        return templateMongo.getTemplateMongo().findAll(Owner.class);
    }

    public Owner updateCampaign(Query query, Update update, Class<Owner> ownerClass) {
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return templateMongo.getTemplateMongo().findAndModify(query, update, options ,Owner.class);
    }
}
