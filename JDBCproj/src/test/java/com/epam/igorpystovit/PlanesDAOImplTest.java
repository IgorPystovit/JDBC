package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.PlanesDAOImpl;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PlanesDAOImplTest {
    @Test
    public void create_test() throws SQLException {
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        planesDAO.create(4,"Boeing-747",120, PlaneType.PASSENGER);
    }

    @Test
    public void getById_test() throws SQLException,NoSuchDataException{
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        Assertions.assertEquals("Boeing-747",planesDAO.getById(4).getName());
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        planesDAO.delete(4);
        Assertions.assertThrows(NoSuchDataException.class,() -> planesDAO.getById(4));
    }

    @Test
    public void deleting_non_exist_row_throws_an_exception(){
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> planesDAO.delete(1));
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        create_test();
        planesDAO.update(new PlanesEntity(4,"Airbus-Beluga",1000,PlaneType.CARGO));
        Assertions.assertEquals("Airbus-Beluga",planesDAO.getById(4).getName());
    }

    @Test
    public void update_non_existing_row_throws_an_exception(){
        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> planesDAO.update(new PlanesEntity(90,"Airbus-Beluga",1000,PlaneType.CARGO)));
    }


}
