package com.epam.igorpystovit.controller;

import com.epam.igorpystovit.DAOPattern.daointerface.GeneralDAO;
import com.epam.igorpystovit.NoSuchDataException;

import java.sql.SQLException;

public abstract class Controller<T,ID> implements GeneralDAO<T,ID>{
    private GeneralDAO<T,ID> generalDAO;

    public T getById(ID id) throws SQLException,NoSuchDataException{
        return generalDAO.getById(id);
    }
    public void update(T obj) throws SQLException, NoSuchDataException {
        generalDAO.update(obj);
    }
}
