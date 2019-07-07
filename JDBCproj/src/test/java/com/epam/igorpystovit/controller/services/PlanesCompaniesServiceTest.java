package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.PlanesCompaniesDAOImpl;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PlanesCompaniesServiceTest {
    private static PlanesService planesService = new PlanesService();
    private static CompaniesService companiesService = new CompaniesService();
    private static PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();

    @BeforeAll
    public static void fillPlanesCompaniesTables() throws SQLException {
        planesService.create(1,"Boeing-777",700, PlaneType.PASSENGER);
        planesService.create(2,"Airbus-Beluga",50,PlaneType.CARGO);
        companiesService.create(1,"Lufthansa");
        companiesService.create(2,"Panamerica");
    }

    @Test
    public void create_test() throws SQLException{
        planesCompaniesService.create(new PlanesCompaniesEntity(1,1,1,700));
        Assertions.assertDoesNotThrow(() -> planesCompaniesService.getById(1));
    }

    @Test
    public void create_method_seat_validation() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(2,1,2,100));
        Assertions.assertDoesNotThrow(() -> planesCompaniesService.getById(2));
        Assertions.assertEquals(50,planesCompaniesService.getById(2).getAvailableSeats());
        planesCompaniesService.create(new PlanesCompaniesEntity(5,1,2,-2));
        Assertions.assertDoesNotThrow(() -> planesCompaniesService.getById(5));
        Assertions.assertEquals(0,planesCompaniesService.getById(5).getAvailableSeats());
        planesCompaniesService.create(new PlanesCompaniesEntity(4,1,2,34));
        Assertions.assertDoesNotThrow(() -> planesCompaniesService.getById(4));
        Assertions.assertEquals(34,planesCompaniesService.getById(4).getAvailableSeats());
    }

    @Test
    public void creating_row_with_unacceptable_parameters_makes_any_changes() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(10,100,20,902));
        Assertions.assertThrows(NoSuchDataException.class,() -> planesCompaniesService.getById(10));
    }

    @Test
    public void update_method_seat_validation() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(3,2,2,30));
        planesCompaniesService.update(new PlanesCompaniesEntity(3,2,2,300));
        Assertions.assertEquals(50,planesCompaniesService.getById(3).getAvailableSeats());
        planesCompaniesService.update(new PlanesCompaniesEntity(3,2,2,-2));
        Assertions.assertEquals(0,planesCompaniesService.getById(3).getAvailableSeats());
        planesCompaniesService.update(new PlanesCompaniesEntity(3,2,2,11));
        Assertions.assertEquals(11,planesCompaniesService.getById(3).getAvailableSeats());
    }

    @Test
    public void updating_row_with_unacceptable_parameters_makes_any_changes() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(5,1,1,290));
        Assertions.assertThrows(NoSuchDataException.class,() ->
                planesCompaniesService.update(new PlanesCompaniesEntity(5,22,89,100)));
    }

    @Test
    public void updateSeatNum_method_seat_validation() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(6,2,1,600));
        planesCompaniesService.updateSeatNum(6,900);
        Assertions.assertEquals(700,planesCompaniesService.getById(6).getAvailableSeats());
        planesCompaniesService.updateSeatNum(6,-2);
        Assertions.assertEquals(0,planesCompaniesService.getById(6).getAvailableSeats());
        planesCompaniesService.updateSeatNum(6,100);
        Assertions.assertEquals(100,planesCompaniesService.getById(6).getAvailableSeats());
    }

    @Test
    public void updating_non_existing_row_with_updateSeatNum_method_throws_an_exception() throws SQLException{
        Assertions.assertThrows(NoSuchDataException.class,() ->
                planesCompaniesService.updateSeatNum(109,20));
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        planesCompaniesService.create(new PlanesCompaniesEntity(7,1,1,110));
        Assertions.assertDoesNotThrow(() -> planesCompaniesService.getById(7));
        planesCompaniesService.delete(7);
        Assertions.assertThrows(NoSuchDataException.class,() -> planesCompaniesService.getById(7));
    }


    @AfterAll
    public static void emptyPlanesCompaniesTables() throws SQLException, NoSuchDataException{
        planesService.delete(1);
        planesService.delete(2);
        companiesService.delete(1);
        companiesService.delete(2);
    }
}
