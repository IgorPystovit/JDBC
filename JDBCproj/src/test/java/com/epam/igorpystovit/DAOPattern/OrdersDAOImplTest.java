package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daoimplementations.OrdersDAOImpl;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.controller.services.ClientsService;
import com.epam.igorpystovit.controller.services.FlightsService;
import com.epam.igorpystovit.model.entities.ClientsEntity;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.OrdersEntity;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

class OrdersDAOImplTest {
    private static ClientsService clientsService = new ClientsService();
    private static FlightsService flightsService = new FlightsService();

    private static final FlightsEntity firstFlight = new FlightsEntity
            (4,1,2,"20190619","110000","20190620","193000",1,3000);
    private static final FlightsEntity secondFlight = new FlightsEntity
            (1,1,3,"20191109","093000","20191109","110000",2,2900);

    @BeforeAll
    public static void fillClientsFlightsTables() throws SQLException {
        clientsService.create(new ClientsEntity(2,"Nastya","Hrynyshyn",2500));
        clientsService.create(new ClientsEntity(3,"Igor","Wage",3100));
        flightsService.create(firstFlight);
        flightsService.create(secondFlight);
    }

    @Test
    public void create_test() throws SQLException,NoSuchDataException{
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        ordersDAO.create(new OrdersEntity(11,2,4));

        Assertions.assertDoesNotThrow(() -> ordersDAO.getById(11));
        Assertions.assertEquals(2,ordersDAO.getById(11).getClientId());
        Assertions.assertEquals(4,ordersDAO.getById(11).getFlightId());
    }

    @Test
    public void creating_duplicate_id_makes_any_changes() throws SQLException, NoSuchDataException {
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        ordersDAO.create(new OrdersEntity(10,2,1));
        ordersDAO.create(new OrdersEntity(10,3,1));

        Assertions.assertEquals(2,ordersDAO.getById(10).getClientId());
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        ordersDAO.create(new OrdersEntity(10,2,1));
        Assertions.assertDoesNotThrow(() -> ordersDAO.getById(10));
        ordersDAO.update(new OrdersEntity(10,3,1));

        Assertions.assertEquals(3,ordersDAO.getById(10).getClientId());
        Assertions.assertEquals(1,ordersDAO.getById(10).getFlightId());
    }

    @Test
    public void updating_non_existing_row_throws_an_exception(){
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> ordersDAO.update(new OrdersEntity(100,3,3)));
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        ordersDAO.create(new OrdersEntity(10,2,1));
        Assertions.assertDoesNotThrow(() -> ordersDAO.getById(10));
        ordersDAO.delete(10);
        Assertions.assertThrows(NoSuchDataException.class,() -> ordersDAO.getById(10));
    }

    @Test
    public void deleting_non_existing_row_throws_an_exception(){
        OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> ordersDAO.delete(100));
    }

    @AfterAll
    public static void emtyClientsFlightsTables() throws SQLException,NoSuchDataException{
        clientsService.delete(2);
        clientsService.delete(3);
        flightsService.delete(4);
        flightsService.delete(1);
    }

}