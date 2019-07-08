package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.TownsEntity;

public class TownsEntityFactory extends EntityFactory<TownsEntity>{
    @Override
    protected TownsEntity createEntity() {
        logger.info("Input town entity id:");
        int townId = Reader.readInt();
        logger.info("Input town name:");
        String name = Reader.readString();
        return new TownsEntity(townId,name);
    }
}
