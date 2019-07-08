package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.ClientsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.ClientsDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.ClientsEntity;

import java.sql.SQLException;
import java.util.List;

public class ClientsService implements ClientsDAO,Service<ClientsEntity,Integer> {
    private ClientsDAOImpl clientsDAO = new ClientsDAOImpl();

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
        clientsDAO.delete(id);
    }
}
