package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.TownsDAOImpl;

import com.epam.igorpystovit.DAOPattern.daointerface.TownsDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.TownsEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TownsService implements TownsDAO,Service<TownsEntity,Integer>{
    private static final TownsDAOImpl townsDAO = new TownsDAOImpl();
    private static final FlightsService flightsService = new FlightsService();

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
        List<FlightsEntity> flightsEntitiesOnTheTable = getFlightsEntitiesByTownId(id);
        for (FlightsEntity flightsEntity : flightsEntitiesOnTheTable){
            flightsService.delete(flightsEntity.getId());
        }
        townsDAO.delete(id);
    }

    @Override
    public void update(TownsEntity entity) throws SQLException, NoSuchDataException {
        townsDAO.update(entity);
    }

    @Override
    public Integer readId() {
        return townsDAO.readId();
    }

    //Constraints
    private List<FlightsEntity> getFlightsEntitiesByTownId(Integer townId) throws SQLException{
        List<FlightsEntity> flightsEntities = flightsService.getAll();
        for (FlightsEntity flightsEntity : new ArrayList<>(flightsEntities)){
            if ((flightsEntity.getArrivalTownId() != townId) && (flightsEntity.getDepartureTownId() != townId)){
                flightsEntities.remove(flightsEntity);
            }
        }
        return flightsEntities;
    }
}
