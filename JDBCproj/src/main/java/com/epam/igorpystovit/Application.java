package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.daoimplementations.OrdersDAOImpl;
import com.epam.igorpystovit.DAOPattern.daoimplementations.PlanesCompaniesDAOImpl;
import com.epam.igorpystovit.controller.services.CompaniesService;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.datetime.DateTimeParser;
import com.epam.igorpystovit.model.entities.*;
import com.epam.igorpystovit.view.MainMenu;

import java.util.regex.Pattern;

public class Application {
    private static FlightsEntity flight = new FlightsEntity
            (10,1,2,"20190619","110000","20190620","193000",2,3000);
    private static FlightsEntity flight2 = new FlightsEntity
            (8,1,3,"20190619","110000","20190620","193000",2,3000);
    private static PlanesEntity passengerPlane = new PlanesEntity(2,"Boeing-777",800, PlaneType.PASSENGER);
    private static PlanesEntity cargoPlane = new PlanesEntity(9,"Airbus-Beluga",10,PlaneType.CARGO);
    private static CompaniesEntity company = new CompaniesEntity(2,"Lufthansa");

    public static void main(String[] args) {
        try{
            FlightsService flightsService = new FlightsService();
            flightsService.update(new FlightsEntity(2,1,2,
                    "20190202","110000","20190203","190000",1,400));
            flightsService.getAll().forEach(System.out::println);
//            new MainMenu().launch();
        } catch (Exception  e){
            e.printStackTrace();
        }

    }
}
