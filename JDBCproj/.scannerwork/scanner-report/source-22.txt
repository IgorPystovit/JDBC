package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.*;
import com.epam.igorpystovit.model.entityfactory.*;

public enum TableType {
    CLIENTS(new ClientsService(),new ClientsEntityFactory()),
    TOWNS(new TownsService(),new TownsEntityFactory()),
    PLANES(new PlanesService(),new PlanesEntityFactory()),
    PLANES_COMPANIES(new PlanesCompaniesService(),new PlanesCompaniesEntityFactory()),
    ORDERS(new OrdersService(),new OrdersEntityFactory()),
    FLIGHTS(new FlightsService(),new FlightsEntityFactory()),
    COMAPNIES(new CompaniesService(),new CompaniesEntityFactory());

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
