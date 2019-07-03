package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DateTimeParser;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightsDAOImpl implements FlightsDAO {
    private final String SELECT = "select * from Flights";
    private final String SELECT_BY_ID = "select * from Flights where id = ?";
    private final String INSERT = "insert Flights(id,company_id,departure_town_id,arrival_town_id,departure_date,departure_time,arrival_date,arrival_time,price) " +
            "values(?,?,?,?,?,?,?,?,?)";
    private final String UPDATE = "update Flights set company_id = ? , departure_town_id = ? , arrival_town_id = ? , departure_date = ? ," +
            "departure_time = ? , arrival_date = ? , arrival_time = ? , price = ? where id = ?";
    private final String UPDATE_ARRIVAL_DATE_TIME = "update Flights set arrival_date = ? , arrival_time = ? where id = ?";
    private final String UPDATE_DEPARTURE_DATE_TIME = "update Flights set departure_date = ? , departure_time = ? where id = ?";
    private final String UPDATE_PRICE = "update Flights set price = ? where id = ?";
    private final String DELETE = "delete from Flights where id = ?";
    private final Connection DBCONNECTION;

    private Transformer<FlightsEntity> transformer = new Transformer<>(FlightsEntity.class);
    private DateTimeParser dateTimeParser = new DateTimeParser();

    public FlightsDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<FlightsEntity> getAll() throws SQLException {
        List<FlightsEntity> flights = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT);
        while (selectResult.next()){
            flights.add((FlightsEntity)transformer.transformFromResultSet(selectResult));
        }
        return flights;
    }

    @Override
    public FlightsEntity getById(Integer id) throws SQLException, NoSuchDataException {
        FlightsEntity flight = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet selectByIdResult = selectByIdStatement.executeQuery();
        while (selectByIdResult.next()){
            flight = (FlightsEntity) transformer.transformFromResultSet(selectByIdResult);
        }
        if (flight == null){
            throw new NoSuchDataException();
        }
        return flight;
    }

    @Override
    public void create(FlightsEntity flight) throws SQLException {
        try{
            checkIfPresent(flight.getId());
            logger.error("You are trying to insert duplicate key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,flight.getId());
            insertStatement.setInt(2,flight.getCompanyId());
            insertStatement.setInt(3,flight.getDepartureTownId());
            insertStatement.setInt(4,flight.getArrivalTownId());
            insertStatement.setDate(5,flight.getDepartureDate());
            insertStatement.setTime(6,flight.getDepartureTime());
            insertStatement.setDate(7,flight.getArrivalDate());
            insertStatement.setTime(8,flight.getArrivalTime());
            insertStatement.setDouble(9,flight.getPrice());
            insertStatement.execute();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(id);
            PreparedStatement deleteStatement = DBCONNECTION.prepareStatement(DELETE);
            deleteStatement.setInt(1,id);
            deleteStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to delete does not exist");
            throw e;
        }
    }

    @Override
    public void update(FlightsEntity flight) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(flight.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setInt(1,flight.getCompanyId());
            updateStatement.setInt(2,flight.getDepartureTownId());
            updateStatement.setInt(3,flight.getArrivalTownId());
            updateStatement.setDate(4,flight.getDepartureDate());
            updateStatement.setTime(5,flight.getDepartureTime());
            updateStatement.setDate(6,flight.getArrivalDate());
            updateStatement.setTime(7,flight.getArrivalTime());
            updateStatement.setDouble(8,flight.getPrice());
            updateStatement.setInt(9,flight.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    @Override
    public void updateDateTime(Integer updateFlightId, String newDate, String newTime, FlightDateTimeType dateTimeType) throws SQLException, NoSuchDataException {
        PreparedStatement dateTimeUpdateStatement = null;
        switch (dateTimeType){
            case ARRIVAL:
                dateTimeUpdateStatement = DBCONNECTION.prepareStatement(UPDATE_ARRIVAL_DATE_TIME);
                break;
            case DEPARTURE:
                dateTimeUpdateStatement = DBCONNECTION.prepareStatement(UPDATE_DEPARTURE_DATE_TIME);
                break;
            default:
                logger.fatal("No such date time type");
        }
        try{
            checkIfPresent(updateFlightId);
            dateTimeUpdateStatement.setString(1, newDate);
            dateTimeUpdateStatement.setTime(2, dateTimeParser.timeParser(newTime));
            dateTimeUpdateStatement.setInt(3,updateFlightId);
            dateTimeUpdateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    @Override
    public void updatePrice(Integer updateFlightId, double newPrice) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(updateFlightId);
            PreparedStatement priceUpdateStatement = DBCONNECTION.prepareStatement(UPDATE_PRICE);
            priceUpdateStatement.setDouble(1,newPrice);
            priceUpdateStatement.setInt(2,updateFlightId);
            priceUpdateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }
}