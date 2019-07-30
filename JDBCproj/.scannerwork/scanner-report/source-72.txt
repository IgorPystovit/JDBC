package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.TownsEntity;

import java.sql.SQLException;

public interface TownsDAO extends GeneralDAO<TownsEntity, Integer>{
    void create(Integer id,String townName) throws SQLException;
    void update(Integer updateTownId,String newName) throws SQLException, NoSuchDataException;
}
