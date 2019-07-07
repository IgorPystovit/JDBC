package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.FlightsPlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.OrdersDAOImpl;
import com.epam.igorpystovit.DAOPattern.PlanesCompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.controller.services.CompaniesService;
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
            (10,1,1,2,"20190619","110000","20190620","193000",2,3000);
    private static FlightsEntity flight2 = new FlightsEntity
            (8,1,1,3,"20190619","110000","20190620","193000",2,3000);
    private static PlanesEntity passengerPlane = new PlanesEntity(2,"Boeing-777",800, PlaneType.PASSENGER);
    private static PlanesEntity cargoPlane = new PlanesEntity(9,"Airbus-Beluga",10,PlaneType.CARGO);
    private static CompaniesEntity company = new CompaniesEntity(2,"Lufthansa");

    public static void main(String[] args) {
        FlightsService flightsService = new FlightsService();
        PlanesService planesService = new PlanesService();
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        CompaniesService companiesService = new CompaniesService();
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        try{
            flightsService.create(new FlightsEntity(12,1,1,3,"2020-04-06","17:00:00",
                    "2020-04-06","13:00:00",4,1500));
            flightsService.getAll().stream().forEach(System.out::println);
        } catch (Exception  e){
            e.printStackTrace();
        }

    }
}
