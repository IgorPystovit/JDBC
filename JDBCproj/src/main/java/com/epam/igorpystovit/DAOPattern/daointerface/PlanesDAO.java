package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesEntity;

import java.sql.SQLException;

public interface PlanesDAO extends GeneralDAO<PlanesEntity,Integer>{
    void create(Integer id, String planeName, Integer capacity, PlaneType planeType) throws SQLException;
    void update(Integer updatePlaneId,String newPlaneName,Integer newCapacity,PlaneType newPlaneType) throws NoSuchDataException,SQLException;
}
