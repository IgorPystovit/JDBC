package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.PlanesCompaniesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.controller.services.CompaniesService;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PlanesCompaniesDAOImplTest {
    private static PlanesService planesService = new PlanesService();
    private static CompaniesService companiesService = new CompaniesService();

    @BeforeAll
    public static void fillCompaniesPlanesTables() throws SQLException {
        planesService.create(1,"Boeing-777",300, PlaneType.PASSENGER);
        planesService.create(2,"Airbus-Beluga",10,PlaneType.CARGO);
        companiesService.create(1,"Panamerica");
        companiesService.create(2,"Lufthansa");
    }

    @Test
    public void create_test() throws SQLException,NoSuchDataException{
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        planesCompaniesDAO.create(new PlanesCompaniesEntity(1,1,1,300));
        assertDoesNotThrow(() -> planesCompaniesDAO.getById(1));
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        planesCompaniesDAO.create(new PlanesCompaniesEntity(1,1,1,800));
        planesCompaniesDAO.update(new PlanesCompaniesEntity(1,2,1,400));
        assertEquals(2,planesCompaniesDAO.getById(1).getCompanyId());
        assertEquals(400,planesCompaniesDAO.getById(1).getAvailableSeats());
    }

    @Test
    public void updating_non_existing_row_throws_an_exception(){
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        assertThrows(NoSuchDataException.class,() -> planesCompaniesDAO.update(
                new PlanesCompaniesEntity(100,2,4,100)));

    }

    @Test
    public void updateSeatsNum_test() throws SQLException,NoSuchDataException{
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        assertDoesNotThrow(() -> planesCompaniesDAO.getById(1));
        planesCompaniesDAO.updateSeatNum(1,2999);
        assertEquals(2999,planesCompaniesDAO.getById(1).getAvailableSeats());
        assertThrows(NoSuchDataException.class,() -> planesCompaniesDAO.updateSeatNum(1000,30));
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        planesCompaniesDAO.create(new PlanesCompaniesEntity(1,1,1,800));
        assertDoesNotThrow(() -> planesCompaniesDAO.getById(1));
        planesCompaniesDAO.delete(1);
        assertThrows(NoSuchDataException.class,() -> planesCompaniesDAO.getById(1));
    }

    @Test
    public void deleting_non_existing_row_throws_an_exception() throws SQLException{
        PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();
        assertThrows(NoSuchDataException.class,() -> planesCompaniesDAO.delete(1000));
    }

    @AfterAll
    public static void emptyCompaniesPlanesTables() throws SQLException, NoSuchDataException {
        planesService.delete(1);
        planesService.delete(2);
        companiesService.delete(1);
        companiesService.delete(2);
    }
}