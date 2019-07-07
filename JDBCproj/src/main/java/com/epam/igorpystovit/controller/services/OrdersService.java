package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.OrdersDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.OrdersDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.ClientsEntity;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.OrdersEntity;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;

import java.sql.SQLException;
import java.util.List;

public class OrdersService implements OrdersDAO {
    private OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    private FlightsService flightsService = new FlightsService();
    private ClientsService clientsService = new ClientsService();
    private PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();

    @Override
    public List<OrdersEntity> getAll() throws SQLException {
        return ordersDAO.getAll();
    }

    @Override
    public OrdersEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return ordersDAO.getById(id);
    }

    @Override
    public void create(OrdersEntity order) throws SQLException {
        try {
            clientsService.checkIfPresent(order.getClientId());
            flightsService.checkIfPresent(order.getFlightId());
            ClientsEntity client = clientsService.getById(order.getClientId());
            FlightsEntity flight = flightsService.getById(order.getFlightId());
            if (isFundsSufficient(client,flight)){
                if (isSeatsAvailable(planesCompaniesService.getById(flight.getPlaneId()))){
                    double clientCash = client.getCash();
                    double flightPrice = flight.getPrice();
                    client.setCash(clientCash - flightPrice);
                    clientsService.update(client);
                    int availableSeats = planesCompaniesService.getById(flight.getPlaneId()).getAvailableSeats();
                    planesCompaniesService.updateSeatNum(flight.getPlaneId(),availableSeats-1);
                    ordersDAO.create(order);
                }
                else{
                    logger.info("There is no available seats");
                }
            }
            else{
                logger.info("Funds insufficient");
            }
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while creating order");
        }
    }

    @Override
    public void update(OrdersEntity newOrder) throws SQLException, NoSuchDataException {
        try{
            clientsService.checkIfPresent(newOrder.getClientId());
            flightsService.checkIfPresent(newOrder.getFlightId());
            OrdersEntity oldOrder = ordersDAO.getById(newOrder.getId());
            ClientsEntity oldClient = clientsService.getById(oldOrder.getClientId());
            ClientsEntity newClient = clientsService.getById(newOrder.getClientId());
            FlightsEntity oldFlight = flightsService.getById(oldOrder.getFlightId());
            FlightsEntity newFlight = flightsService.getById(newOrder.getFlightId());

            if (oldClient.getId() != newClient.getId() && oldFlight.getId() != newFlight.getId()){
                updateFlightId(newOrder.getId(),newFlight.getId());
                updateClientId(newOrder.getId(),newClient.getId());
            }
            else if (oldClient.getId() != newClient.getId()){
                updateClientId(newOrder.getId(),newClient.getId());
            }
            else if (oldFlight.getId() != newFlight.getId()){
                updateFlightId(newOrder.getId(),newFlight.getId());
            }
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while updating row");
            throw e;
        }
    }

    private void updateFlightId(Integer updateOrderId, Integer newFlightId) throws SQLException,NoSuchDataException{
        try{
            flightsService.checkIfPresent(newFlightId);
            OrdersEntity order = ordersDAO.getById(updateOrderId);
            ClientsEntity client = clientsService.getById(order.getClientId());
            FlightsEntity oldFlight = flightsService.getById(order.getFlightId());
            FlightsEntity newFlight = flightsService.getById(newFlightId);
            if (isFundsSufficient(client,oldFlight) && isSeatsAvailable(planesCompaniesService.getById(newFlight.getPlaneId()))){
                double clientCash = client.getCash();
                double price = newFlight.getPrice();
                client.setCash(clientCash - price);
                PlanesCompaniesEntity oldPlanesCompaniesEntity = planesCompaniesService.getById(oldFlight.getPlaneId());
                PlanesCompaniesEntity newPlanesCompaniesEntity = planesCompaniesService.getById(newFlight.getPlaneId());
                planesCompaniesService.updateSeatNum(oldPlanesCompaniesEntity.getId(),oldPlanesCompaniesEntity.getAvailableSeats() + 1);
                planesCompaniesService.updateSeatNum(newPlanesCompaniesEntity.getId(),newPlanesCompaniesEntity.getAvailableSeats() - 1);
                clientsService.update(client);
                ordersDAO.update(new OrdersEntity(updateOrderId,client.getId(),newFlightId));
            }
            else{
                logger.info("Funds insufficient");
            }
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while updating row");
            throw e;
        }
    }

    private void updateClientId(Integer updateOrderId,Integer newClientId) throws SQLException,NoSuchDataException{
        try{
            clientsService.checkIfPresent(newClientId);
            OrdersEntity order = ordersDAO.getById(updateOrderId);
            FlightsEntity flight = flightsService.getById(order.getFlightId());
            ClientsEntity newClient = clientsService.getById(newClientId);
            if (isFundsSufficient(newClient,flight)){
                double clientCash = newClient.getCash();
                double price = flight.getPrice();
                newClient.setCash(clientCash - price);
                clientsService.update(newClient);
                ordersDAO.update(new OrdersEntity(updateOrderId,newClientId,flight.getId()));
            }
            else{
                logger.info("Funds insufficient");
            }
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while updating row");
            throw e;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        OrdersEntity order = ordersDAO.getById(id);
        ClientsEntity client = clientsService.getById(order.getClientId());
        FlightsEntity flight = flightsService.getById(order.getFlightId());
        PlanesCompaniesEntity planesCompaniesEntity = planesCompaniesService.getById(flight.getPlaneId());
        planesCompaniesService.updateSeatNum(planesCompaniesEntity.getId(),planesCompaniesEntity.getAvailableSeats() + 1);
        client.setCash(client.getCash() + flight.getPrice());
        clientsService.update(client);
        ordersDAO.delete(id);
    }

    private boolean isSeatsAvailable(PlanesCompaniesEntity entity){
        return entity.getAvailableSeats() > 0;
    }
    private boolean isFundsSufficient(ClientsEntity client, FlightsEntity flight){
        return client.getCash() > flight.getPrice();
    }
}
