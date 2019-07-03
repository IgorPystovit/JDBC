package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.CompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.FlightDateTimeType;
import com.epam.igorpystovit.DAOPattern.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;

import java.sql.SQLException;
import java.util.List;

public class FlightsService implements FlightsDAO {
    private FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
    private CompaniesDAOImpl companiesDAO = new CompaniesDAOImpl();
    private TownsDAOImpl townsDAO = new TownsDAOImpl();

    @Override
    public List<FlightsEntity> getAll() throws SQLException {
        return flightsDAO.getAll();
    }

    @Override
    public FlightsEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return flightsDAO.getById(id);
    }

    @Override
    public void create(FlightsEntity flight) throws SQLException {
        try{
            companiesDAO.checkIfPresent(flight.getCompanyId());
            townsDAO.checkIfPresent(flight.getArrivalTownId());
            townsDAO.checkIfPresent(flight.getDepartureTownId());
        } catch (NoSuchDataException e){
            logger.error("An error occurs while inserting data! Please check insert parameters");
            return;
        }
        flightsDAO.create(flight);
    }

    @Override
    public void updateDateTime(Integer updateFlightId, String newDate, String newTime, FlightDateTimeType dateTimeType) throws SQLException, NoSuchDataException {
        flightsDAO.updateDateTime(updateFlightId,newDate,newTime,dateTimeType);
    }

    @Override
    public void update(FlightsEntity flight) throws SQLException, NoSuchDataException {
        try{
            companiesDAO.checkIfPresent(flight.getCompanyId());
            townsDAO.checkIfPresent(flight.getArrivalTownId());
            townsDAO.checkIfPresent(flight.getDepartureTownId());
        } catch (NoSuchDataException e){
            logger.error("An error occurs while updating data! Please check insert parameters");
            return;
        }
        flightsDAO.update(flight);
    }

    @Override
    public void updatePrice(Integer updateFlightId, double newPrice) throws SQLException, NoSuchDataException {
        flightsDAO.updatePrice(updateFlightId,newPrice);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        flightsDAO.delete(id);
    }


}
