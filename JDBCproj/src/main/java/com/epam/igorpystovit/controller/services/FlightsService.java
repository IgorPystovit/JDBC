package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.CompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.FlightDateTimeType;
import com.epam.igorpystovit.DAOPattern.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.DateTimeComparator;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightsService implements FlightsDAO {
    private FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
    private CompaniesDAOImpl companiesDAO = new CompaniesDAOImpl();
    private TownsDAOImpl townsDAO = new TownsDAOImpl();

    private class FlightKey{
        private int companyId;
        private int arrivalTownId;
        private int departureTownId;

        public FlightKey(){}
        public FlightKey(int companyId,int departureTownId,int arrivalTownId){
            this.companyId = companyId;
            this.departureTownId = departureTownId;
            this.arrivalTownId = arrivalTownId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setArrivalTownId(int arrivalTownId) {
            this.arrivalTownId = arrivalTownId;
        }

        public int getArrivalTownId() {
            return arrivalTownId;
        }

        public void setDepartureTownId(int departureTownId) {
            this.departureTownId = departureTownId;
        }

        public int getDepartureTownId() {
            return departureTownId;
        }
    }

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
            if (checkIfFlightDateTimePresent(flight)){
                logger.error("You are trying to insert duplicate row");
                return;
            }
        } catch (NoSuchDataException e){
            logger.error("An error occurs while inserting data! Please check insert parameters");
            return;
        }
        flightsDAO.create(flight);
    }

    @Override
    public void updateDateTime(Integer updateFlightId, String newDate, String newTime, FlightDateTimeType dateTimeType) throws SQLException, NoSuchDataException {
        FlightsEntity newFlightEntity = flightsDAO.getById(updateFlightId);
        switch (dateTimeType){
            case DEPARTURE:
                newFlightEntity.setDepartureDate(newDate);
                newFlightEntity.setDepartureTime(newTime);
                break;
            case ARRIVAL:
                newFlightEntity.setArrivalDate(newDate);
                newFlightEntity.setArrivalTime(newTime);
                break;
            default:
                logger.error("No such dateTimeType");
                return;
        }

        if (checkIfFlightDateTimePresent(newFlightEntity)){
            logger.error("You are trying to insert duplicate row");
            return;
        }
        flightsDAO.updateDateTime(updateFlightId,newDate,newTime,dateTimeType);
    }

    @Override
    public void update(FlightsEntity flight) throws SQLException, NoSuchDataException {
        try{
            companiesDAO.checkIfPresent(flight.getCompanyId());
            townsDAO.checkIfPresent(flight.getArrivalTownId());
            townsDAO.checkIfPresent(flight.getDepartureTownId());
            if (checkIfFlightDateTimePresent(flight)){
                logger.error("You are trying to insert duplicate row");
                return;
            }
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

    private boolean checkIfFlightDateTimePresent(FlightsEntity newFlight) throws SQLException{
        boolean present = false;
        FlightKey flightKey = new FlightKey(newFlight.getCompanyId(),newFlight.getDepartureTownId(),newFlight.getArrivalTownId());
        List<FlightsEntity> flights = getByFlightKey(flightKey);
        if (flights.size() > 0){
            DateTimeComparator newFlightArrival = new DateTimeComparator(newFlight.getArrivalDate(),newFlight.getArrivalTime());
            DateTimeComparator newFlightDeparture = new DateTimeComparator(newFlight.getDepartureDate(),newFlight.getDepartureTime());
            for (FlightsEntity tempFlight : flights){
                if (newFlightDeparture.compare(tempFlight.getDepartureDate(),tempFlight.getDepartureTime()) &&
                        newFlightArrival.compare(tempFlight.getArrivalDate(),tempFlight.getArrivalTime())){
                    present = true;
                    break;
                }
            }
        }
        return present;
    }

    private List<FlightsEntity> getByFlightKey(FlightKey flightKey) throws SQLException{
        List<FlightsEntity> flights = flightsDAO.getAll();
        for (FlightsEntity tempFlight : new ArrayList<>(flights)){
            if ((tempFlight.getCompanyId() != flightKey.companyId) ||
                    (tempFlight.getDepartureTownId() != flightKey.departureTownId) ||
                    (tempFlight.getArrivalTownId() != flightKey.arrivalTownId)){
                flights.remove(tempFlight);
            }
        }
        return flights;
    }

}
