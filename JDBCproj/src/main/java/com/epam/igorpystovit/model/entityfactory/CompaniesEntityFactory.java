package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.CompaniesEntity;

public class CompaniesEntityFactory extends EntityFactory<CompaniesEntity,Integer> {
    @Override
    protected CompaniesEntity create(Integer id) {
//        logger.info("Input company id:");
//        int id = Reader.readInt();
        logger.info("Input company name:");
        String name = Reader.readString();
        return new CompaniesEntity(id,name);
    }
}
