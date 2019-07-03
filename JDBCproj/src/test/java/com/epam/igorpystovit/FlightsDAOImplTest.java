package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.FlightDateTimeType;
import com.epam.igorpystovit.DAOPattern.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class FlightsDAOImplTest {
    private final FlightsEntity testFlight = new FlightsEntity
            (4,1,1,2,"20190619","110000","20190620","193000",3000);
    private final FlightsEntity updateTestFlight = new FlightsEntity
            (4,1,1,3,"20191109","093000","20191109","110000",2900);
    private final FlightsEntity notOnTheTableFlight = new FlightsEntity
            (101,1,1,1,"20191109","093000","20191109","110000",2900);
    @Test
    public void create_test() throws SQLException,NoSuchDataException {
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
        flightsDAO.delete(testFlight.getId());
        flightsDAO.create(testFlight);
        int companyId = flightsDAO.getById(4).getCompanyId();
        double price = flightsDAO.getById(4).getPrice();
        Assertions.assertEquals(1,companyId);
        Assertions.assertEquals(3000,price);
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
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
        flightsDAO.updateDateTime(4,"2000-11-11","110023",FlightDateTimeType.ARRIVAL);
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
