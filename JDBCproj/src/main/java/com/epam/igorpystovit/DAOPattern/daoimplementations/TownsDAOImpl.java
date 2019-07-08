package com.epam.igorpystovit.DAOPattern.daoimplementations;

import com.epam.igorpystovit.DAOPattern.daointerface.TownsDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.transformer.Transformer;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.TownsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TownsDAOImpl implements TownsDAO{
    private final String FIND_ALL = "select * from Towns";
    private final String GET_BY_ID = "select * from Towns where id = ?";
    private final String INSERT = "insert Towns(id,name) values(?,?)";
    private final String UPDATE = "update Towns set name = ? where id = ?";
    private final String DELETE = "delete from Towns where id = ?";
    private final Connection DBCONNECTION;
    private Transformer<TownsEntity> transformer = new Transformer<>(TownsEntity.class);

    public TownsDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public TownsEntity getById(Integer id) throws SQLException,NoSuchDataException{
        TownsEntity townsEntity = null;
        PreparedStatement preparedStatement = DBCONNECTION.prepareStatement(GET_BY_ID);
        preparedStatement.setInt(1,id);
        ResultSet townResultSet = preparedStatement.executeQuery();
        while (townResultSet.next()){
            townsEntity = (TownsEntity) transformer.transformFromResultSet(townResultSet);
        }
        if (townsEntity == null){
            throw new NoSuchDataException();
        }
        return townsEntity;
    }


    @Override
    public void create(TownsEntity townsEntity) throws SQLException{
        try{
            checkIfPresent(townsEntity.getTownId());
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement preparedStatement = DBCONNECTION.prepareStatement(INSERT);
            preparedStatement.setInt(1,townsEntity.getTownId());
            preparedStatement.setString(2,townsEntity.getTownName());
            preparedStatement.execute();
        }
    }

    public void create(Integer id, String townName) throws SQLException{
        try{
            checkIfPresent(id);
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement preparedStatement = DBCONNECTION.prepareStatement(INSERT);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,townName);
            preparedStatement.execute();
            return;
        }
    }

    @Override
    public void update(TownsEntity townsEntity) throws SQLException,NoSuchDataException{
        try {
            checkIfPresent(townsEntity.getTownId());
            PreparedStatement preparedStatement = DBCONNECTION.prepareStatement(UPDATE);
            preparedStatement.setString(1,townsEntity.getTownName());
            preparedStatement.setInt(2,townsEntity.getTownId());
            preparedStatement.execute();
        } catch (NoSuchDataException e){
            System.out.println("No data with such id stored on the table");
            throw e;
        }
    }

    public void update(Integer updateTownId, String townName) throws SQLException,NoSuchDataException{
        try {
            checkIfPresent(updateTownId);
            PreparedStatement preparedStatement = DBCONNECTION.prepareStatement(UPDATE);
            preparedStatement.setString(1,townName);
            preparedStatement.setInt(2, updateTownId);
        } catch (NoSuchDataException e){
            System.out.println("No data with such updateTownId stored on the table");
            throw e;
        }
    }

    @Override
    public void delete(Integer id) throws NoSuchDataException,SQLException{
        try{
            checkIfPresent(id);
            PreparedStatement deleteStatement = DBCONNECTION.prepareStatement(DELETE);
            deleteStatement.setInt(1,id);
            deleteStatement.execute();
        } catch (NoSuchDataException e){
            System.out.println("No data with such id stored on the table");
            throw e;
        }
    }

    @Override
    public List<TownsEntity> getAll() throws SQLException {
        List<TownsEntity> townsEntities = new ArrayList<>();
        Statement statement = DBCONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_ALL);
        while (resultSet.next()){
            townsEntities.add((TownsEntity) transformer.transformFromResultSet(resultSet));
        }
        return townsEntities;
    }
}
