package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightDateTimeType;
import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightsDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.FlightsDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.OrdersEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightsService implements FlightsDAO,Service<FlightsEntity,Integer> {
    private static final FlightsDAOImpl flightsDAO = new FlightsDAOImpl();
    private static final CompaniesService companiesDAO = new CompaniesService();
    private static final TownsService townsDAO = new TownsService();
    private static final PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();
    private static final OrdersService ordersService = new OrdersService();

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
            planesCompaniesService.checkIfPresent(flight.getPlaneCompanyId());
            townsDAO.checkIfPresent(flight.getArrivalTownId());
            townsDAO.checkIfPresent(flight.getDepartureTownId());

            if (flight.getArrivalTownId() == flight.getDepartureTownId()){
                logger.error("Arrival and departure town are the same");
                return;
            }

            if (isPlaneCompanyPresent(flight.getPlaneCompanyId())){
                logger.error("This plain is already be taken");
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
        flightsDAO.updateDateTime(updateFlightId,newDate,newTime,dateTimeType);
    }

    @Override
    public void update(FlightsEntity flight) throws SQLException, NoSuchDataException {
        try{
            townsDAO.checkIfPresent(flight.getArrivalTownId());
            townsDAO.checkIfPresent(flight.getDepartureTownId());
            planesCompaniesService.checkIfPresent(flight.getPlaneCompanyId());
            flightsDAO.checkIfPresent(flight.getId());
            FlightsEntity oldFlight  = getById(flight.getId());
            if (oldFlight.getPlaneCompanyId() != flight.getPlaneCompanyId()){
                if (isPlaneCompanyPresent(flight.getPlaneCompanyId())){
                    logger.error("This plane is already be taken");
                    return;
                }
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
        List<OrdersEntity> ordersEntitiesOnTheTable = getOrdersEntitiesByFlightId(id);
        for (OrdersEntity ordersEntity : ordersEntitiesOnTheTable){
            ordersService.delete(ordersEntity.getId());
        }
        flightsDAO.delete(id);
    }

    @Override
    public Integer readId() {
        return flightsDAO.readId();
    }

    private boolean isPlaneCompanyPresent(int planeCompanyId) throws SQLException{
        List<FlightsEntity> flights = flightsDAO.getAll();
        for (FlightsEntity tempFlight : new ArrayList<>(flights)){
            if (tempFlight.getPlaneCompanyId() != planeCompanyId){
                flights.remove(tempFlight);
            }
        }
        return !flights.isEmpty();
    }

    //Constraints
    private List<OrdersEntity> getOrdersEntitiesByFlightId(Integer flightId) throws SQLException{
        List<OrdersEntity> ordersEntities = ordersService.getAll();
        for (OrdersEntity ordersEntity : ordersEntities){
            if (ordersEntity.getFlightId() != flightId){
                ordersEntities.remove(ordersEntity);
            }
        }
        return ordersEntities;
    }
}
