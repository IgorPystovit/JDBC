package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.ClientsEntity;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.OrdersEntity;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdersServiceTest {
    private static FlightsService flightsService = new FlightsService();
    private static ClientsService clientsService = new ClientsService();
    private static PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();

    private static List<FlightsEntity> flights = new ArrayList<>(Arrays.asList(
            new FlightsEntity(4,1,1,2,"2019-06-19","11:00:00",
                            "2019-06-20","19:30:00",2,3000),
            new FlightsEntity(1,1,2,3,"2019-11-09","09:30:00",
                            "2019-11-09","11:00:00",1,1900),
            new FlightsEntity(5,1,1,2,"2019-11-09","09:30:00",
                            "2019-11-09","11:00:00",2,2900),
            new FlightsEntity(10,1,1,3,"2020-02-03","20:00:00",
                            "2020-02-04","11:00:00",1,2000),
            new FlightsEntity(12,1,1,3,"2020-04-06","17:00:00",
                            "2020-04-06","13:00:00",3,1500)));
    private static ClientsEntity igor = new ClientsEntity(1,"Igor","Pystovit",110000);
    private static ClientsEntity bot = new ClientsEntity(2,"bot"," ",128000);

    @BeforeAll
    public static void fillFlightsClientsTables() throws SQLException {
        for (FlightsEntity tempFlight : flights){
            flightsService.create(tempFlight);
        }
        clientsService.create(igor);
        clientsService.create(bot);
    }

    @BeforeAll
    public static void tablesOccupancy_test() throws SQLException,NoSuchDataException{
        for (FlightsEntity tempFlight : flights){
            assertDoesNotThrow(() -> flightsService.getById(tempFlight.getId()));
            assertEquals(
                    tempFlight.getDepartureDate().toString(),
                    flightsService.getById(tempFlight.getId()).getDepartureDate().toString());
            assertEquals(
                    tempFlight.getArrivalDate().toString(),
                    flightsService.getById(tempFlight.getId()).getArrivalDate().toString());
            assertEquals(
                    tempFlight.getPlaneId(),
                    flightsService.getById(tempFlight.getId()).getPlaneId());
        }

        assertDoesNotThrow(() -> clientsService.getById(igor.getId()));
        assertEquals(igor.getName(),clientsService.getById(igor.getId()).getName());
        assertEquals(igor.getCash(),clientsService.getById(igor.getId()).getCash());

        assertDoesNotThrow(() -> clientsService.getById(bot.getId()));
        assertEquals(bot.getName(),clientsService.getById(bot.getId()).getName());
        assertEquals(bot.getCash(),clientsService.getById(bot.getId()).getCash());
    }

    @Test
    public void create_test() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();
        System.out.println();
        ordersService.create(new OrdersEntity(1,1,4));

        assertDoesNotThrow(() -> ordersService.getById(1));
        assertTrue((ordersService.getById(1).getClientId() == 1) && (ordersService.getById(1).getFlightId() == 4));
    }

    @Test
    public void creating_row_with_unacceptable_parameters_applies_any_changes() throws SQLException{
        OrdersService ordersService = new OrdersService();
        ordersService.create(new OrdersEntity(5,1009,2091));

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(5));
    }

    @Test
    public void if_funds_insufficient_then_rollback() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();
        igor.setCash(200);

        assertDoesNotThrow(() -> clientsService.getById(igor.getId()));

        clientsService.update(igor);
        assertEquals(200,clientsService.getById(igor.getId()).getCash());

        ordersService.create(new OrdersEntity(5,1,1));

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(5));

        igor.setCash(11000);
        clientsService.update(igor);
    }

    @Test
    public void if_there_is_no_available_seats_then_rollback() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();
        Integer planesCompaniesId = flightsService.getById(5).getPlaneId();

        assertDoesNotThrow(() -> flightsService.getById(5));
        assertDoesNotThrow(() -> planesCompaniesService.getById(planesCompaniesId));

        planesCompaniesService.updateSeatNum(planesCompaniesId,0);

        assertEquals(0,planesCompaniesService.getById(planesCompaniesId).getAvailableSeats());

        igor.setCash(11000.00);
        clientsService.update(igor);

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(5));

        ordersService.create(new OrdersEntity(5,igor.getId(),5));

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(5));

        planesCompaniesService.updateSeatNum(planesCompaniesId,500);
    }

    @Test
    public void clients_cash_and_available_seats_decreases_after_creating_new_order() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertDoesNotThrow(() -> flightsService.getById(10));
        assertDoesNotThrow(() -> clientsService.getById(igor.getId()));

        Integer planesCompaniesId = flightsService.getById(10).getPlaneId();
        PlanesCompaniesEntity oldPlanesCompaniesEntity = planesCompaniesService.getById(planesCompaniesId);
        ClientsEntity oldClientsEntity = clientsService.getById(igor.getId());
        ordersService.create(new OrdersEntity(6,igor.getId(),10));

        assertDoesNotThrow(() -> ordersService.getById(6));
        assertEquals(igor.getId(),ordersService.getById(6).getClientId());
        assertEquals(10,ordersService.getById(6).getFlightId());

        PlanesCompaniesEntity newPlanesCompaniesEntity = planesCompaniesService.getById(planesCompaniesId);
        ClientsEntity newClientEntity = clientsService.getById(igor.getId());

        assertEquals(oldPlanesCompaniesEntity.getAvailableSeats() - 1,newPlanesCompaniesEntity.getAvailableSeats());
        assertEquals(oldClientsEntity.getCash() - flightsService.getById(10).getPrice(), newClientEntity.getCash());
    }


    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(15));

        ordersService.create(new OrdersEntity(15,igor.getId(),4));

        assertDoesNotThrow(() -> ordersService.getById(15));
        assertEquals(igor.getId(),ordersService.getById(15).getClientId());
        assertEquals(4,ordersService.getById(15).getFlightId());

        ordersService.update(new OrdersEntity(15,bot.getId(),1));

        assertEquals(bot.getId(),ordersService.getById(15).getClientId());
        assertEquals(1,ordersService.getById(15).getFlightId());
    }

    @Test
    public void updating_non_existing_row_throws_an_exception() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();
        assertThrows(NoSuchDataException.class,() -> ordersService.update(new OrdersEntity(1123,2,2)));
    }

    @Test
    public void updating_row_with_unacceptable_data_throws_an_exception() throws SQLException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(11));

        ordersService.create(new OrdersEntity(11,igor.getId(),4));

        assertDoesNotThrow(() -> ordersService.getById(11));
        assertThrows(NoSuchDataException.class,() -> ordersService.update(new OrdersEntity(15,101,123)));
    }

    @Test
    public void if_funds_insufficient_for_updated_flight_then_rollback() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(23));

        ordersService.create(new OrdersEntity(23,bot.getId(),1));

        assertDoesNotThrow(() -> ordersService.getById(23));

        bot.setCash(0);

        assertDoesNotThrow(() -> clientsService.getById(bot.getId()));

        clientsService.update(bot);

        assertEquals(0,clientsService.getById(bot.getId()).getCash());

        ordersService.update(new OrdersEntity(23,bot.getId(),4));

        assertEquals(1,ordersService.getById(23).getFlightId());

        bot.setCash(8000);
        clientsService.update(bot);
    }

    @Test
    public void if_there_is_no_available_for_updated_flight_then_rollback() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(20));

        ordersService.create(new OrdersEntity(20,igor.getId(),10));

        assertDoesNotThrow(() -> ordersService.getById(20));

        Integer planesCompaniesId = flightsService.getById(4).getPlaneId();
        PlanesCompaniesEntity planesCompaniesEntity = planesCompaniesService.getById(planesCompaniesId);
        planesCompaniesService.updateSeatNum(planesCompaniesId,0);

        assertEquals(0,planesCompaniesService.getById(planesCompaniesId).getAvailableSeats());

        ordersService.update(new OrdersEntity(20,igor.getId(),4));

        assertEquals(10,ordersService.getById(20).getFlightId());

        planesCompaniesService.updateSeatNum(planesCompaniesId,planesCompaniesEntity.getAvailableSeats());
    }

    @Test
    public void if_flight_id_changed_decrease_new_flight_id_available_seats_num_and_clients_cash() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(18));

        ClientsEntity oldClientsEntity = clientsService.getById(igor.getId());
        PlanesCompaniesEntity oldPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(4).getPlaneId());

        ordersService.create(new OrdersEntity(18,igor.getId(),4));

        PlanesCompaniesEntity newPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(4).getPlaneId());
        ClientsEntity newClientsEntity = clientsService.getById(igor.getId());

        assertDoesNotThrow(() -> ordersService.getById(18));
        assertEquals(igor.getId(),ordersService.getById(18).getClientId());
        assertEquals(4,ordersService.getById(18).getFlightId());
        assertEquals(oldPlanesCompaniesEntity.getAvailableSeats() - 1,
                newPlanesCompaniesEntity.getAvailableSeats());
        assertEquals(oldClientsEntity.getCash() - flightsService.getById(4).getPrice(),
                newClientsEntity.getCash());

        PlanesCompaniesEntity finalPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(1).getPlaneId());

        ordersService.update(new OrdersEntity(18,igor.getId(),1));

        ClientsEntity finalClientsEntity = clientsService.getById(igor.getId());

        assertEquals(igor.getId(),ordersService.getById(18).getClientId());
        assertEquals(1,ordersService.getById(18).getFlightId());
        assertEquals(newClientsEntity.getCash() - flightsService.getById(1).getPrice(),finalClientsEntity.getCash());
        assertEquals(newPlanesCompaniesEntity.getAvailableSeats() + 1,planesCompaniesService.getById(newPlanesCompaniesEntity.getId()).getAvailableSeats());
        assertEquals(finalPlanesCompaniesEntity.getAvailableSeats() - 1,planesCompaniesService.getById(flightsService.getById(1).getPlaneId()).getAvailableSeats());
    }

    @Test
    public void if_client_id_changed_available_seats_num_remains_constant_clients_cash_decreased() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(17));

        igor.setCash(900000);
        clientsService.update(igor);

        ClientsEntity oldIgorClientsEntity = clientsService.getById(igor.getId());
        PlanesCompaniesEntity oldPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(10).getPlaneId());

        ordersService.create(new OrdersEntity(17,igor.getId(),10));
        PlanesCompaniesEntity newPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(10).getPlaneId());
        ClientsEntity newIgorClientsEntity = clientsService.getById(igor.getId());

        assertEquals(10,ordersService.getById(17).getFlightId());
        assertEquals(oldPlanesCompaniesEntity.getAvailableSeats() - 1,
                newPlanesCompaniesEntity.getAvailableSeats());
        assertEquals(oldIgorClientsEntity.getCash() - flightsService.getById(10).getPrice(),
                newIgorClientsEntity.getCash());

        ClientsEntity oldBotClientsEntity = clientsService.getById(bot.getId());
        ordersService.update(new OrdersEntity(17,bot.getId(),10));
        PlanesCompaniesEntity finalPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(10).getPlaneId());
        ClientsEntity newBotClientsEntity = clientsService.getById(bot.getId());
        ClientsEntity finalIgorClientsEntity = clientsService.getById(igor.getId());

        assertEquals(10,ordersService.getById(17).getFlightId());
        assertEquals(newPlanesCompaniesEntity.getAvailableSeats(),finalPlanesCompaniesEntity.getAvailableSeats());
        assertEquals(newIgorClientsEntity.getCash(),finalIgorClientsEntity.getCash());
        assertEquals(oldBotClientsEntity.getCash() - flightsService.getById(10).getPrice(),newBotClientsEntity.getCash());
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        OrdersService ordersService = new OrdersService();

        bot.setCash(10000);
        clientsService.update(bot);

        ClientsEntity oldClientsEntity = clientsService.getById(bot.getId());
        PlanesCompaniesEntity oldPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(5).getPlaneId());

        assertThrows(NoSuchDataException.class,() -> ordersService.getById(16));

        ordersService.create(new OrdersEntity(16,bot.getId(),5));

        ClientsEntity newClientsEntity = clientsService.getById(bot.getId());
        PlanesCompaniesEntity newPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(5).getPlaneId());

        assertDoesNotThrow(() -> ordersService.getById(16));
        assertEquals(bot.getId(),ordersService.getById(16).getClientId());
        assertEquals(5,ordersService.getById(16).getFlightId());
        assertEquals(oldClientsEntity.getCash() - flightsService.getById(5).getPrice(),newClientsEntity.getCash());
        assertEquals(oldPlanesCompaniesEntity.getAvailableSeats() - 1,newPlanesCompaniesEntity.getAvailableSeats());

        ordersService.delete(16);


        ClientsEntity finalClientsEntity = clientsService.getById(bot.getId());
        PlanesCompaniesEntity finalPlanesCompaniesEntity = planesCompaniesService.getById(flightsService.getById(5).getPlaneId());


        assertEquals(oldClientsEntity.getCash(),finalClientsEntity.getCash());
        assertEquals(oldPlanesCompaniesEntity.getAvailableSeats() ,finalPlanesCompaniesEntity.getAvailableSeats());


    }

    @AfterAll
    public static void emptyFlightsClientsTables() throws SQLException, NoSuchDataException {
        for (FlightsEntity tempFlight : flights){
            flightsService.delete(tempFlight.getId());
        }
        clientsService.delete(igor.getId());
        clientsService.delete(bot.getId());
    }
}