package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.FlightsPlanesEntity;
import com.epam.igorpystovit.model.entities.PK_FlightsPlanes;

import java.sql.SQLException;

public interface FlightsPlanesDAO extends GeneralDAO<FlightsPlanesEntity, PK_FlightsPlanes>{
    void updateSeatNum(PK_FlightsPlanes updateRowId,int newSeatNum) throws SQLException, NoSuchDataException;
    void create(PK_FlightsPlanes primaryKey) throws SQLException;
}
