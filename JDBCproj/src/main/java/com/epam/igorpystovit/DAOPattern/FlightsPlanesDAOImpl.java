package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.FlightsPlanesDAO;
import com.epam.igorpystovit.DAOPattern.daointerface.PlanesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.controller.services.PlanesService;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.FlightsPlanesEntity;
import com.epam.igorpystovit.model.entities.PK_FlightsPlanes;
import com.epam.igorpystovit.model.entities.PlanesEntity;
import com.epam.igorpystovit.model.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightsPlanesDAOImpl implements FlightsPlanesDAO {
    private final String SELECT = "select * from Flights_Planes";
    private final String SELECT_BY_ID = "select * from Flights_Planes where flight_id = ? and plane_id = ?";
    private final String INSERT = "insert Flights_Planes(flight_id,plane_id,seat_num) values(?,?,?)";
    private final String UPDATE = "update Flights_Planes set seat_num = ? where flight_id = ? and plane_id = ?";
    private final String DELETE = "delete from Flights_Planes where flight_id = ? and plane_id = ?";
    private final Connection DBCONNECTION;
    private final Transformer transformer = new Transformer<>(FlightsPlanesEntity.class);
    public FlightsPlanesDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<FlightsPlanesEntity> getAll() throws SQLException {
        List<FlightsPlanesEntity> flightsPlanes = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT);
        while (selectResult.next()){
            flightsPlanes.add((FlightsPlanesEntity)transformer.transformFromResultSet(selectResult));
        }
        return flightsPlanes;
    }

    @Override
    public FlightsPlanesEntity getById(PK_FlightsPlanes primaryKey) throws SQLException, NoSuchDataException {
        FlightsPlanesEntity flightsPlanesEntity = null;
        PreparedStatement selectByIdSatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdSatement.setInt(1,primaryKey.getFlightId());
        selectByIdSatement.setInt(2,primaryKey.getPlaneId());
        ResultSet selectByIdResult = selectByIdSatement.executeQuery();
        while (selectByIdResult.next()){
            flightsPlanesEntity = (FlightsPlanesEntity) transformer.transformFromResultSet(selectByIdResult);
        }
        if (flightsPlanesEntity == null){
            throw new NoSuchDataException();
        }
        return flightsPlanesEntity;
    }

    @Override
    public void create(FlightsPlanesEntity entity) throws SQLException {
        try{
            checkIfPresent(entity.getPrimaryKey());
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,entity.getPrimaryKey().getFlightId());
            insertStatement.setInt(2,entity.getPrimaryKey().getPlaneId());
            insertStatement.setInt(3,entity.getSeatNum());
            insertStatement.execute();
        }
    }

    @Override
    public void create(PK_FlightsPlanes primaryKey) throws SQLException {
        PlanesService planesService = new PlanesService();
        try{
            checkIfPresent(primaryKey);
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            try{
                PlanesEntity planesEntity = planesService.getById(primaryKey.getPlaneId());
                PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
                insertStatement.setInt(1,primaryKey.getFlightId());
                insertStatement.setInt(2,primaryKey.getPlaneId());
                insertStatement.setInt(3,planesEntity.getCapacity());
                insertStatement.execute();
            } catch (NoSuchDataException ex){
                logger.error("An error occurred while creating Flights_Planes data row");
            }
        }
    }

    @Override
    public void update(FlightsPlanesEntity entity) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(entity.getPrimaryKey());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setInt(1,entity.getSeatNum());
            updateStatement.setInt(2,entity.getPrimaryKey().getFlightId());
            updateStatement.setInt(3,entity.getPrimaryKey().getPlaneId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("An error occurred while updating row");
            throw e;
        }
    }

    @Override
    public void updateSeatNum(PK_FlightsPlanes updateRowId, int newSeatNum) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(updateRowId);
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setInt(1,newSeatNum);
            updateStatement.setInt(2,updateRowId.getFlightId());
            updateStatement.setInt(3,updateRowId.getPlaneId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("An error occurred while updating row");
            throw e;
        }
    }

    @Override
    public void delete(PK_FlightsPlanes pkFlightsPlanes) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(pkFlightsPlanes);
            PreparedStatement deleteStatement = DBCONNECTION.prepareStatement(DELETE);
            deleteStatement.setInt(1,pkFlightsPlanes.getFlightId());
            deleteStatement.setInt(2,pkFlightsPlanes.getPlaneId());
            deleteStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to delete does not exist");
            throw e;
        }
    }

}
