package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.ClientsService;
import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.entityfactory.ClientsEntityFactory;
import com.epam.igorpystovit.model.entityfactory.EntityFactory;

public enum TableType {
    CLIENTS(new ClientsService(),new ClientsEntityFactory());

    private Service service;
    private EntityFactory entityFactory;

    TableType(Service service, EntityFactory entityFactory){
        this.entityFactory = entityFactory;
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }
}
