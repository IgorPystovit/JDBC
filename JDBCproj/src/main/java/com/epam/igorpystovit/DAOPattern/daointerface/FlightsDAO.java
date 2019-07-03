package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.DAOPattern.FlightDateTimeType;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public interface FlightsDAO extends GeneralDAO<FlightsEntity,Integer>{
    void updateDateTime(Integer updateFlightId, String newDate, String newTime, FlightDateTimeType dateTimeType) throws SQLException, NoSuchDataException;
    void updatePrice(Integer updateFlightId,double newPrice) throws SQLException,NoSuchDataException;
}
