package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.FlightsPlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.PlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsPlanesDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.PlanesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsPlanesEntity;
import com.epam.igorpystovit.model.entities.PK_FlightsPlanes;

import java.sql.SQLException;
import java.util.List;

public class FlightsPlanesService implements FlightsPlanesDAO {
    private FlightsPlanesDAOImpl flightsPlanesDAO = new FlightsPlanesDAOImpl();
    private PlanesDAOImpl planesDAO = new PlanesDAOImpl();
    private FlightsDAOImpl flightsDAO = new FlightsDAOImpl();

    @Override
    public FlightsPlanesEntity getById(PK_FlightsPlanes pkFlightsPlanes) throws SQLException, NoSuchDataException {
        return flightsPlanesDAO.getById(pkFlightsPlanes);
    }

    @Override
    public List<FlightsPlanesEntity> getAll() throws SQLException {
        return flightsPlanesDAO.getAll();
    }

    @Override
    public void create(FlightsPlanesEntity entity) throws SQLException {
        try{
            planesDAO.checkIfPresent(entity.getPrimaryKey().getPlaneId());
            flightsDAO.checkIfPresent(entity.getPrimaryKey().getFlightId());
            int validSeatNum = validateSeatNum(entity.getSeatNum(),planesDAO.getById(entity.getPrimaryKey().getPlaneId()).getCapacity());
            entity.setSeatNum(validSeatNum);
            flightsPlanesDAO.create(entity);
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while creating a Flights_Planes row");
        }
    }

    public void create(PK_FlightsPlanes primaryKey) throws SQLException{
        try{
            planesDAO.checkIfPresent(primaryKey.getPlaneId());
            flightsDAO.checkIfPresent(primaryKey.getFlightId());
            flightsPlanesDAO.create(primaryKey);
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while creating a Flights_Planes row");
        }

    }
    @Override
    public void update(FlightsPlanesEntity entity) throws SQLException, NoSuchDataException {
        int validSeatNum = validateSeatNum(entity.getSeatNum(),planesDAO.getById(entity.getPrimaryKey().getPlaneId()).getCapacity());
        entity.setSeatNum(validSeatNum);
        flightsPlanesDAO.update(entity);
    }

    @Override
    public void updateSeatNum(PK_FlightsPlanes updateRowId, int newSeatNum) throws SQLException, NoSuchDataException {
        newSeatNum = validateSeatNum(newSeatNum,planesDAO.getById(updateRowId.getPlaneId()).getCapacity());
        flightsPlanesDAO.updateSeatNum(updateRowId,newSeatNum);
    }

    @Override
    public void delete(PK_FlightsPlanes pkFlightsPlanes) throws SQLException, NoSuchDataException {
        flightsPlanesDAO.delete(pkFlightsPlanes);
    }

    private int validateSeatNum(int seatNum, int maxSeatNum){
        if (maxSeatNum < seatNum) {
            seatNum = maxSeatNum;
        }
        else if (seatNum < 0){
            seatNum = 0;
        }
        return seatNum;
    }
}
