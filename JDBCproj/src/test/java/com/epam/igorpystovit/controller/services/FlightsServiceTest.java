package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightDateTimeType;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
//truncate Flights table before testing
public class FlightsServiceTest {
    private FlightsService flightsService = new FlightsService();
    private final FlightsEntity testFlight = new FlightsEntity
            (4,1,1,2,"20190619","110000",
                    "20190620","193000",2,3000);
    private final FlightsEntity unacceptableFlight = new FlightsEntity
            (1,101,122,3,"20191109","093000",
                    "20191109","110000",11,2900);
    private final FlightsEntity flight = new FlightsEntity
            (5,1,1,1,"20191109","093000",
                    "20191109","110000",2,2900);
    private final FlightsEntity updateFlight1 = new FlightsEntity
            (10,1,1,3,"20200203","200000",
                    "20200204","110000",1,2000);
    private final FlightsEntity updateFlight2 = new FlightsEntity
            (12,1,1,3,"20200406","170000",
                    "20200406","130000",1,300);
    @Test
    public void create_test() throws SQLException,NoSuchDataException {
        flightsService.create(flight);
        Assertions.assertDoesNotThrow(() -> flightsService.getById(flight.getId()));
        flightsService.create(testFlight);
        Assertions.assertDoesNotThrow(() -> flightsService.getById(testFlight.getId()));
        flightsService.create(unacceptableFlight);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsService.getById(unacceptableFlight.getId()));
    }

    @Test
    public void updateDateTime_test() throws SQLException,NoSuchDataException{
        flightsService.create(updateFlight1);
        flightsService.create(updateFlight2);
        FlightsEntity flightsEntity = new FlightsEntity
                (10,1,1,3,"20200406","170000",
                        "20200204","110000",1,2000);
        Assertions.assertDoesNotThrow(() -> flightsService.getById(10));
        Assertions.assertDoesNotThrow(() -> flightsService.getById(12));
        flightsService.update(flightsEntity);
        Assumptions.assumeTrue(flightsService.getById(10).getDepartureDate().toString().equals("2020-04-06"));
        Assumptions.assumeTrue(flightsService.getById(10).getDepartureTime().toString().equals("17:00:00"));
        flightsService.updateDateTime(10,"2020-04-06","13:00:00",FlightDateTimeType.ARRIVAL);
        Assertions.assertEquals("2020-02-04",flightsService.getById(10).getArrivalDate().toString());
        Assertions.assertEquals("11:00:00",flightsService.getById(10).getArrivalTime().toString());
    }

}
