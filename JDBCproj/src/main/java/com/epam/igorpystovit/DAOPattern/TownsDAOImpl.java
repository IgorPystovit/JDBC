package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.TownsDAO;
import com.epam.igorpystovit.Transformer;
import com.epam.igorpystovit.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.entities.TownsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TownsDAOImpl  {
    private final String FIND_ALL = "select * from Towns";
    private final String GET_BY_ID = "select * from Towns where id = ?";
    private final String INSERT = "insert Towns values(?,?)";
    private final String UPDATE = "update Towns set name = ? where id = ?";
    private final Connection dbConnection;
    private Transformer transformer = new Transformer(TownsEntity.class);
    public TownsDAOImpl(){
        dbConnection = ConnectionManager.getConnection();
    }

    public TownsEntity getTownById(int id) throws SQLException{
        TownsEntity townsEntity = null;
        PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_BY_ID);
        preparedStatement.setInt(1,id);
        ResultSet townResultSet = preparedStatement.executeQuery();
        while (townResultSet.next()){
            townsEntity = (TownsEntity) transformer.transformFromResultSet(townResultSet);
        }
        return townsEntity;
    }

    public boolean createTown(int id,String townName) throws SQLException{
        boolean modified = false;
        TownsEntity townsEntity = getTownById(id);
        if (townsEntity == null){
            PreparedStatement preparedStatement = dbConnection.prepareStatement(INSERT);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,townName);
            modified = preparedStatement.execute();
        }
        else{
            System.out.println("You are trying to insert duplicate primary key");
        }
        return modified;
    }

    public List<TownsEntity> getAllTowns() throws SQLException {
        List<TownsEntity> townsEntities = new ArrayList<>();
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_ALL);
        while (resultSet.next()){
            townsEntities.add((TownsEntity) transformer.transformFromResultSet(resultSet));
        }
        return townsEntities;
    }
}
