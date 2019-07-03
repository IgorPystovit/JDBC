package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.model.entities.FlightsEntity;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        FlightsEntity testFlight = new FlightsEntity
                (1,100,1,2,"20190619","110000","20190620","193000",3000);
        GeneralDAO townsDAO = new TownsDAOImpl();
        FlightsDAO flightsDAO = new FlightsService();
        try{
            flightsDAO.create(testFlight);
        } catch (SQLException e){
            System.out.println(e);
        }

    }
}
