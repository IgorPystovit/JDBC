package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.CompaniesEntity;

import java.sql.SQLException;

public interface CompaniesDAO extends GeneralDAO<CompaniesEntity,Integer>{
    void create(Integer id,String name) throws SQLException;
    void update(Integer updateCompanyId, String newName) throws SQLException, NoSuchDataException;
}
