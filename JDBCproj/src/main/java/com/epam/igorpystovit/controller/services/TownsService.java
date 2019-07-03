package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;

import com.epam.igorpystovit.DAOPattern.daointerface.TownsDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.TownsEntity;

import java.sql.SQLException;
import java.util.List;

public class TownsService implements TownsDAO{
    private final TownsDAOImpl townsDAO = new TownsDAOImpl();


    public List<TownsEntity> getAll() throws SQLException {
        return townsDAO.getAll();
    }

    @Override
    public TownsEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return townsDAO.getById(id);
    }

    @Override
    public void create(Integer id, String townName) throws SQLException {
        townsDAO.create(id,townName);
    }

    @Override
    public void update(Integer updateTownId, String newName) throws SQLException, NoSuchDataException {
        townsDAO.update(updateTownId,newName);
    }

    @Override
    public void create(TownsEntity entity) throws SQLException {
        townsDAO.create(entity);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        townsDAO.delete(id);
    }

    @Override
    public void update(TownsEntity entity) throws SQLException, NoSuchDataException {
        townsDAO.update(entity);
    }
}
