package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

//truncate Flight_Planes table before testing

class FlightsPlanesServiceTest {
    private static FlightsService flightsService = new FlightsService();
    private static PlanesService planesService = new PlanesService();
    @BeforeAll
    public static void fillPlanesFlightsTables() throws SQLException {

        PlanesEntity passengerPlane = new PlanesEntity(2,"Boeing-777",800, PlaneType.PASSENGER);
        PlanesEntity cargoPlane = new PlanesEntity(9,"Airbus-Beluga",10,PlaneType.CARGO);
        FlightsEntity flight = new FlightsEntity
                (10,1,1,2,"20190619","110000","20190620","193000",3000);
        FlightsEntity flight2 = new FlightsEntity
                (8,1,1,3,"20190619","110000","20190620","193000",3000);
        planesService.create(passengerPlane);
        planesService.create(cargoPlane);
        flightsService.create(flight);
        flightsService.create(flight2);
    }

    @Test
    public void create_test() throws SQLException,NoSuchDataException{
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,9);
        flightsPlanesService.create(new FlightsPlanesEntity(primaryKey,planesService.getById(primaryKey.getPlaneId()).getCapacity()));
        Assertions.assertDoesNotThrow(() -> flightsPlanesService.getById(primaryKey));
        Assumptions.assumeTrue(planesService.getById(primaryKey.getPlaneId()).getCapacity() == flightsPlanesService.getById(primaryKey).getSeatNum());
        PK_FlightsPlanes primaryKey2 = new PK_FlightsPlanes(8,2);
        flightsPlanesService.create(new FlightsPlanesEntity(primaryKey2,1000));
        Assertions.assertDoesNotThrow(() -> flightsPlanesService.getById(primaryKey2));
        Assertions.assertEquals(planesService.getById(primaryKey2.getPlaneId()).getCapacity(),flightsPlanesService.getById(primaryKey2).getSeatNum());
    }

    @Test
    public void creating_row_with_non_existing_pk_throws_an_exception() throws SQLException{
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(100,200);
        flightsPlanesService.create(primaryKey);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsPlanesService.getById(primaryKey));
        flightsPlanesService.create(new FlightsPlanesEntity(primaryKey,1000));
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsPlanesService.getById(primaryKey));
    }

    @Test
    public void createWithPK_test() throws SQLException,NoSuchDataException{
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,2);
        flightsPlanesService.create(primaryKey);
        Assertions.assertDoesNotThrow(() -> flightsPlanesService.getById(primaryKey));
        Assumptions.assumeTrue(planesService.getById(primaryKey.getPlaneId()).getCapacity() == flightsPlanesService.getById(primaryKey).getSeatNum());
    }

    @AfterAll
    public static void update_test() throws SQLException,NoSuchDataException{
        FlightsPlanesService flightsPlanesService = new FlightsPlanesService();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(8,2);
        flightsPlanesService.create(primaryKey);
        Assertions.assertDoesNotThrow(() -> flightsPlanesService.getById(primaryKey));
        int maxSeatNum = planesService.getById(primaryKey.getPlaneId()).getCapacity();

        flightsPlanesService.update(new FlightsPlanesEntity(primaryKey,maxSeatNum+2));
        Assertions.assertEquals(maxSeatNum,flightsPlanesService.getById(primaryKey).getSeatNum());
        flightsPlanesService.update(new FlightsPlanesEntity(primaryKey,maxSeatNum-1));
        Assertions.assertEquals(maxSeatNum-1,flightsPlanesService.getById(primaryKey).getSeatNum());
        flightsPlanesService.update(new FlightsPlanesEntity(primaryKey,-2));
        Assertions.assertEquals(0,flightsPlanesService.getById(primaryKey).getSeatNum());

        flightsPlanesService.updateSeatNum(primaryKey,maxSeatNum+2);
        Assertions.assertEquals(maxSeatNum,flightsPlanesService.getById(primaryKey).getSeatNum());
        flightsPlanesService.updateSeatNum(primaryKey,maxSeatNum-1);
        Assertions.assertEquals(maxSeatNum-1,flightsPlanesService.getById(primaryKey).getSeatNum());
        flightsPlanesService.updateSeatNum(primaryKey,-2);
        Assertions.assertEquals(0,flightsPlanesService.getById(primaryKey).getSeatNum());

    }
    @AfterAll
    public static void emptyPlanesFlightsTables() throws NoSuchDataException,SQLException {
        FlightsService flightsService = new FlightsService();
        PlanesService planesService = new PlanesService();
        flightsService.delete(10);
        flightsService.delete(8);
        planesService.delete(2);
        planesService.delete(9);
    }
}