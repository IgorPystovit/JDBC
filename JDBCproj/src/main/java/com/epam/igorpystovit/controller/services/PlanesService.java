package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.PlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.PlanesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesEntity;

import java.sql.SQLException;
import java.util.List;

public class PlanesService implements PlanesDAO {
    private PlanesDAOImpl planesDAO = new PlanesDAOImpl();

    @Override
    public List<PlanesEntity> getAll() throws SQLException {
        return planesDAO.getAll();
    }

    @Override
    public PlanesEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return planesDAO.getById(id);
    }

    @Override
    public void create(PlanesEntity plane) throws SQLException {
        planesDAO.create(plane);
    }

    @Override
    public void create(Integer id, String planeName, Integer capacity, PlaneType planeType) throws SQLException {
        planesDAO.create(id,planeName,capacity,planeType);
    }

    @Override
    public void update(PlanesEntity plane) throws SQLException, NoSuchDataException {
        planesDAO.update(plane);
    }

    @Override
    public void update(Integer updatePlaneId, String newPlaneName, Integer newCapacity, PlaneType newPlaneType) throws NoSuchDataException, SQLException {
        planesDAO.update(updatePlaneId,newPlaneName,newCapacity,newPlaneType);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        planesDAO.delete(id);
    }
}
