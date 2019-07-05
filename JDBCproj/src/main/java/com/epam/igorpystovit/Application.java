package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.FlightsPlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.controller.services.FlightsPlanesService;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.entities.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class Application {
    private static FlightsEntity flight = new FlightsEntity
            (10,1,1,2,"20190619","110000","20190620","193000",3000);
    private static FlightsEntity flight2 = new FlightsEntity
            (8,1,1,3,"20190619","110000","20190620","193000",3000);
    private static PlanesEntity passengerPlane = new PlanesEntity(2,"Boeing-777",800, PlaneType.PASSENGER);
    private static PlanesEntity cargoPlane = new PlanesEntity(9,"Airbus-Beluga",10,PlaneType.CARGO);


    public static void main(String[] args) {
        FlightsService flightsService = new FlightsService();
        PlanesService planesService = new PlanesService();
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        try{
//
//            PK_FlightsPlanes primary = new PK_FlightsPlanes(8,2);
//            PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(8,9);
//            PK_FlightsPlanes primaryKey2 = new PK_FlightsPlanes(10,2);
////            flightsPlanesService.create(new FlightsPlanesEntity(primaryKey2,1000));
//            //            flightsPlanesService.create(primary);
////            flightsPlanesService.create(new FlightsPlanesEntity(primaryKey,10));
////            flightsService.getAll().stream().forEach(System.out::println);
//            planesService.getAll().stream().forEach(System.out::println);
//            flightsPlanesService.getAll().stream().forEach(System.out::println);
        } catch (Exception  e){
            e.printStackTrace();
        }

    }
}
