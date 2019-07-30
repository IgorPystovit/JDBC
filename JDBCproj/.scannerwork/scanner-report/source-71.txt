package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.ClientsEntity;

import java.sql.SQLException;

public interface ClientsDAO extends GeneralDAO<ClientsEntity,Integer>{
    void create(Integer id,String name,String surname,double cash) throws SQLException;
    void update(Integer updateClientId,String name,String surname,double cash) throws SQLException, NoSuchDataException;
}
