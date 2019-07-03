package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsPlanesEntity;
import com.epam.igorpystovit.model.entities.PK_FlightsPlanes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class FlightsPlanesDAOImplTest {

    @Test
    public void create_test() throws SQLException, NoSuchDataException {
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,2);
        flightsPlanesDAO.create(new FlightsPlanesEntity(primaryKey,900));
        Assertions.assertEquals(900,flightsPlanesDAO.getById(primaryKey).getSeatNum());
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,1);
        flightsPlanesDAO.create(new FlightsPlanesEntity(primaryKey,900));
        Assumptions.assumeTrue(flightsPlanesDAO.getById(primaryKey).getSeatNum() == 900);
        flightsPlanesDAO.update(new FlightsPlanesEntity(primaryKey,299));
        Assertions.assertEquals(299,flightsPlanesDAO.getById(primaryKey).getSeatNum());
    }

    @Test
    public void updating_non_exist_row_throws_an_exception(){
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(100,200);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsPlanesDAO.update(new FlightsPlanesEntity(primaryKey,1000)));

    }

    @Test
    public void updateSeatNum_test() throws SQLException,NoSuchDataException{
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,1);
        Assumptions.assumeTrue(flightsPlanesDAO.getById(primaryKey).getSeatNum() == 299);
        flightsPlanesDAO.updateSeatNum(primaryKey,298);
        Assertions.assertEquals(298,flightsPlanesDAO.getById(primaryKey).getSeatNum());
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(10,1);
        flightsPlanesDAO.create(new FlightsPlanesEntity(primaryKey,900));
        Assertions.assertDoesNotThrow(() -> flightsPlanesDAO.getById(primaryKey));
        flightsPlanesDAO.delete(primaryKey);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsPlanesDAO.getById(primaryKey));
    }

    @Test
    public void deleting_non_exist_row_throws_an_exception() {
        FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
        PK_FlightsPlanes primaryKey = new PK_FlightsPlanes(109,11);
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsPlanesDAO.delete(primaryKey));
    }

}