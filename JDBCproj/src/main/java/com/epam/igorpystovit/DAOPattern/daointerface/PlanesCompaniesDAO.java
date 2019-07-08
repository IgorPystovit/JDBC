package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;

import java.sql.SQLException;

public interface PlanesCompaniesDAO extends GeneralDAO<PlanesCompaniesEntity,Integer>{
    void updateSeatNum(Integer updateRowId, int newAvailableSeatsNum) throws SQLException, NoSuchDataException;
}
