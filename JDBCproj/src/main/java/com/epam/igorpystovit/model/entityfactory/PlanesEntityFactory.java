package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesEntity;

public class PlanesEntityFactory extends EntityFactory<PlanesEntity>{
    @Override
    protected PlanesEntity createEntity() {
        logger.info("Input plane entity id:");
        int planeId = Reader.readInt();
        logger.info("Input plane name:");
        String name = Reader.readString();
        logger.info("Input plane's capacity:");
        int capacity = Reader.readInt();
        logger.info("Input plane's type(Passenger / Cargo)");
        PlaneType planeType = Reader.readPlaneType();
        return new PlanesEntity(planeId,name,capacity,planeType);
    }
}
