package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.PlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.controller.Controller;
import com.epam.igorpystovit.controller.services.TownsController;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.PlanesEntity;
import com.epam.igorpystovit.model.entities.TownsEntity;

import java.sql.SQLException;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        FlightsEntity testFlight = new FlightsEntity
                (4,1,1,2,"20190619","110000","20190620","193000",3000);
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        DateTimeParser dateTimeParser = new DateTimeParser();
        System.out.println(dateTimeParser.dateParser("20091212"));

    }
}
