package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.model.NoSuchDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public interface GeneralDAO<T, ID> {
    Logger logger = LogManager.getLogger(GeneralDAO.class.getName());

    List<T> getAll() throws SQLException;

    T getById(ID id) throws SQLException,NoSuchDataException;

    void create(T entity) throws SQLException;

    void update(T entity) throws SQLException, NoSuchDataException;

    void delete(ID id) throws SQLException, NoSuchDataException;

    ID readId();

    default void checkIfPresent(ID id) throws SQLException,NoSuchDataException{
        getById(id);
    }
}