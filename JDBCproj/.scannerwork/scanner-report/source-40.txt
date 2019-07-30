package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;

public class PlanesCompaniesEntityFactory extends EntityFactory<PlanesCompaniesEntity,Integer> {
    @Override
    protected PlanesCompaniesEntity create(Integer id) {
//        logger.info("Input plane id provided by the company:");
//        int planeCompanyId = Reader.readInt();
        logger.info("Input company-provider id:");
        int companyId = Reader.readInt();
        logger.info("Input plane id (present on the Planes table):");
        int planeId = Reader.readInt();
        logger.info("Input number of available seats:");
        int availableSeats = Reader.readInt();
        return new PlanesCompaniesEntity(id,companyId,planeId,availableSeats);
    }
}
