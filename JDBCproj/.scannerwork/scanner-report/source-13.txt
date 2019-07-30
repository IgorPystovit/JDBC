package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.ClientsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.ClientsDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.ClientsEntity;
import com.epam.igorpystovit.model.entities.OrdersEntity;

import java.sql.SQLException;
import java.util.List;

public class ClientsService implements ClientsDAO,Service<ClientsEntity,Integer> {
    private static final ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
    private static final OrdersService ordersService = new OrdersService();

    @Override
    public ClientsEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return clientsDAO.getById(id);
    }

    @Override
    public List<ClientsEntity> getAll() throws SQLException {
        return clientsDAO.getAll();
    }

    @Override
    public void create(ClientsEntity client) throws SQLException {
        clientsDAO.create(client);
    }

    @Override
    public void create(Integer id, String name, String surname, double cash) throws SQLException {
        clientsDAO.create(id,name,surname,cash);
    }

    @Override
    public void update(Integer updateClientId, String name, String surname, double cash) throws SQLException, NoSuchDataException {
        clientsDAO.update(updateClientId,name,surname,cash);
    }

    @Override
    public void update(ClientsEntity client) throws SQLException, NoSuchDataException {
        clientsDAO.update(client);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        List<OrdersEntity> ordersEntitiesOnTheTable = getOrdersEntitiesByClientsId(id);
        for (OrdersEntity ordersEntity : ordersEntitiesOnTheTable){
            ordersService.delete(ordersEntity.getId());
        }
        clientsDAO.delete(id);
    }

    @Override
    public Integer readId() {
        return clientsDAO.readId();
    }

    //Constraints
    private List<OrdersEntity> getOrdersEntitiesByClientsId(Integer clientId) throws SQLException{
        List<OrdersEntity> ordersEntities = ordersService.getAll();
        for (OrdersEntity ordersEntity : ordersEntities){
            if (ordersEntity.getClientId() != clientId){
                ordersEntities.remove(ordersEntity);
            }
        }
        return ordersEntities;
    }
}
