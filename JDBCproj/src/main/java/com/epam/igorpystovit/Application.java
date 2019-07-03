package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.FlightsPlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.PK_FlightsPlanes;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        FlightsEntity testFlight = new FlightsEntity
                (10,1,1,2,"20190619","110000","20190620","193000",3000);
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        try{
//            flightsPlanesDAO.getAll().stream().forEach(System.out::println);
            System.out.println(flightsPlanesDAO.getById(new PK_FlightsPlanes(1,41)));
        } catch (SQLException|NoSuchDataException e){
            System.out.println(e);
        }

    }
}
