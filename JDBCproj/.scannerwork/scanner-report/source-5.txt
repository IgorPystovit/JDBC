package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightDateTimeType;
import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.model.datetime.DateTimeParser;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.controller.services.CompaniesService;
import com.epam.igorpystovit.controller.services.PlanesCompaniesService;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class FlightsDAOImplTest {
    private final FlightsEntity testFlight = new FlightsEntity
            (4,1,2,"20190619","110000","20190620","193000",1,3000);
    private final FlightsEntity updateTestFlight = new FlightsEntity
            (4,1,3,"20191109","093000","20191109","110000",2,2900);
    private final FlightsEntity notOnTheTableFlight = new FlightsEntity
            (101,1,1,"20191109","093000","20191109","110000",3,22900);
    private final FlightsEntity flightsEntity = new FlightsEntity
            (5,2,3,"20191109","093000","20191109","110000",1,3000);

    private static PlanesService planesService = new PlanesService();
    private static CompaniesService companiesService = new CompaniesService();
    private static PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();

    @BeforeAll
    public static void fillTables() throws SQLException{
        planesService.create(1,"Boeing-777",700, PlaneType.PASSENGER);
        planesService.create(2,"Airbus-Beluga",50,PlaneType.CARGO);
        companiesService.create(1,"Lufthansa");
        companiesService.create(2,"Panamerica");
        planesCompaniesService.create(new PlanesCompaniesEntity(1,1,1,700));
        planesCompaniesService.create(new PlanesCompaniesEntity(2,2,1,650));
        planesCompaniesService.create(new PlanesCompaniesEntity(3,1,2,10));
    }

    @Test
    public void create_test() throws SQLException, NoSuchDataException {
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        flightsDAO.create(flightsEntity);
//        int companyId = flightsDAO.getById(5).getCompanyId();
        double price = flightsDAO.getById(5).getPrice();
//        Assertions.assertEquals(1,companyId);
        Assertions.assertEquals(3000,price);
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        flightsDAO.create(testFlight);
        Assertions.assertDoesNotThrow(() -> flightsDAO.getById(4));
        flightsDAO.delete(4);
        Assertions.assertThrows(NoSuchDataException.class,() ->flightsDAO.getById(4));
    }

    @Test
    public void deleting_non_existing_row_throws_an_exception() throws SQLException,NoSuchDataException{
        FlightsDAO flightsDAO = new FlightsDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsDAO.delete(4));
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        flightsDAO.delete(testFlight.getId());
        flightsDAO.create(testFlight);
        Assumptions.assumeTrue(2 == flightsDAO.getById(4).getArrivalTownId());
        Assumptions.assumeTrue(3000 == flightsDAO.getById(4).getPrice());
        flightsDAO.update(updateTestFlight);
        Assertions.assertEquals(3,flightsDAO.getById(4).getArrivalTownId());
        Assertions.assertEquals(2900,flightsDAO.getById(4).getPrice());
    }

    @Test
    public void updating_non_existing_row_throws_an_exception() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> flightsDAO.update(notOnTheTableFlight));
    }

    @Test
    public void update_date_time_test() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        DateTimeParser dateTimeParser = new DateTimeParser();
        flightsDAO.create(testFlight);
        Assumptions.assumeFalse(flightsDAO.getById(4) == null);
        flightsDAO.updateDateTime(4,"2000-11-11","110023", FlightDateTimeType.ARRIVAL);
        Assertions.assertEquals("2000-11-11",flightsDAO.getById(4).getArrivalDate().toString());
        Assertions.assertEquals(dateTimeParser.timeParser("110023"),flightsDAO.getById(4).getArrivalTime());
        flightsDAO.updateDateTime(4,"2002-08-11","120119",FlightDateTimeType.DEPARTURE);
        Assertions.assertEquals("2002-08-11",flightsDAO.getById(4).getDepartureDate().toString());
        Assertions.assertEquals(dateTimeParser.timeParser("120119"),flightsDAO.getById(4).getDepartureTime());
    }

    @Test
    public void update_price() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        flightsDAO.create(testFlight);
        flightsDAO.updatePrice(4,10000);
        Assertions.assertEquals(10000,flightsDAO.getById(4).getPrice());
    }


}
