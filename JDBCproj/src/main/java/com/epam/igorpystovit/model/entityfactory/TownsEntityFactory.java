package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.TownsEntity;

public class TownsEntityFactory extends EntityFactory<TownsEntity,Integer>{
    @Override
    protected TownsEntity create(Integer id) {
//        logger.info("Input town entity id:");
//        int townId = Reader.readInt();
        logger.info("Input town name:");
        String name = Reader.readString();
        return new TownsEntity(id,name);
    }
}
