package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.FlightsEntity;

public class FlightsEntityFactory extends EntityFactory<FlightsEntity,Integer> {
    @Override
    protected FlightsEntity create(Integer id) {
//        logger.info("Input flight id:");
//        int id = Reader.readInt();
        logger.info("Input company id , that provides the flight:");
        int companyId = Reader.readInt();
        logger.info("Input departure town id:");
        int departureTownId = Reader.readInt();
        logger.info("Input arrival town id:");
        int arrivalTownId = Reader.readInt();
        logger.info("Input departure date( yyMMdd or yy-MM-dd ):");
        String departureDate = Reader.readDateString();
        logger.info("Input departure time( hhMMss or hh:MM:ss ):");
        String departureTime = Reader.readTimeString();
        logger.info("Input arrival date( yyMMdd ot yy-MM-dd ):");
        String arrivalDate = Reader.readDateString();
        logger.info("Input arrival time( hhMMss or hh-MM-ss):");
        String arrivalTime = Reader.readTimeString();
        logger.info("Input company plane id:");
        int planeCompanyId = Reader.readInt();
        logger.info("Input flight price:");
        double price = Reader.readDouble();

        return new FlightsEntity
                (id,companyId,departureTownId,arrivalTownId,departureDate,departureTime,arrivalDate,arrivalTime,planeCompanyId,price);
    }
}
