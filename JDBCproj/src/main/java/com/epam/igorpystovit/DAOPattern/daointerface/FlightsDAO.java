package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.DAOPattern.daoimplementations.FlightDateTimeType;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;

import java.sql.SQLException;

public interface FlightsDAO extends GeneralDAO<FlightsEntity,Integer>{
    void updateDateTime(Integer updateFlightId, String newDate, String newTime, FlightDateTimeType dateTimeType) throws SQLException, NoSuchDataException;
    void updatePrice(Integer updateFlightId,double newPrice) throws SQLException,NoSuchDataException;
}
