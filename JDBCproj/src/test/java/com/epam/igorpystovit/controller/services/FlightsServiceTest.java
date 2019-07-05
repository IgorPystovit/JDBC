package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.FlightDateTimeType;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
//truncate Flights table before testing
public class FlightsServiceTest {
    private FlightsService flightsService = new FlightsService();
    private final FlightsEntity testFlight = new FlightsEntity
            (4,1,1,2,"20190619","110000","20190620","193000",3000);
    private final FlightsEntity duplicateFlight = new FlightsEntity
            (1,1,1,3,"20191109","093000","20191109","110000",2900);
    private final FlightsEntity flight = new FlightsEntity
            (5,1,1,1,"20191109","093000","20191109","110000",2900);

    @Test
    public void create_test() throws SQLException {
        flightsService.create(flight);
        Assertions.assertDoesNotThrow(() -> flightsService.getById(flight.getId()));
        flightsService.create(testFlight);
        Assertions.assertDoesNotThrow(() -> flightsService.getById(testFlight.getId()));
        flightsService.create(duplicateFlight);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsService.getById(duplicateFlight.getId()));
    }

    @Test
    public void updateDateTime_test() throws SQLException,NoSuchDataException{
        flightsService.updateDateTime(4,"2019-11-09","09:30:00", FlightDateTimeType.DEPARTURE);
        Assumptions.assumeTrue(flightsService.getById(4).getDepartureDate().toString().equals("2019-11-09"));
        Assumptions.assumeTrue(flightsService.getById(4).getDepartureTime().toString().equals("09:30:00"));
        flightsService.updateDateTime(4,"20191109","110000",FlightDateTimeType.ARRIVAL);
        Assertions.assertEquals("2019-06-20",flightsService.getById(4).getArrivalDate().toString());
        Assertions.assertEquals("19:30:00",flightsService.getById(4).getArrivalTime().toString());
    }

}
