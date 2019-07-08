package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.daoimplementations.OrdersDAOImpl;
import com.epam.igorpystovit.DAOPattern.daoimplementations.PlanesCompaniesDAOImpl;
import com.epam.igorpystovit.controller.services.CompaniesService;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.datetime.DateTimeParser;
import com.epam.igorpystovit.model.entities.*;

import java.util.regex.Pattern;

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
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        CompaniesService companiesService = new CompaniesService();
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        try{
            PlaneType.valueOf(Reader.readString());
        } catch (Exception  e){
            e.printStackTrace();
        }

    }
}
