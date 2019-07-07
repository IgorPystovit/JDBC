package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.ClientsDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.transformer.Transformer;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.ClientsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsDAOImpl implements ClientsDAO {
    private final String SELECT = "select * from Clients";
    private final String SELECT_BY_ID = "select * from Clients where id = ?";
    private final String INSERT = "insert Clients(id,name,surname,cash) values(?,?,?,?)";
    private final String UPDATE = "update Clients set name = ? , surname = ? , cash = ? where id = ?";
    private final String UPDATE_CASH = "update Clients set cash = ? where id = ?";
    private final String DELETE = "delete from Clients where id = ?";
    private final Connection DBCONNECTION;
    private final Transformer<ClientsEntity> transformer = new Transformer<>(ClientsEntity.class);

    public ClientsDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<ClientsEntity> getAll() throws SQLException {
        List<ClientsEntity> clients = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT);
        while (selectResult.next()){
            clients.add((ClientsEntity)transformer.transformFromResultSet(selectResult));
        }
        return clients;
    }

    @Override
    public ClientsEntity getById(Integer id) throws SQLException,NoSuchDataException {
        ClientsEntity client = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet selectByIdResult = selectByIdStatement.executeQuery();
        while (selectByIdResult.next()){
            client = (ClientsEntity) transformer.transformFromResultSet(selectByIdResult);
        }
        if (client == null){
            throw new NoSuchDataException();
        }
        return client;
    }

    @Override
    public void create(ClientsEntity clientsEntity) throws SQLException{
        try{
            checkIfPresent(clientsEntity.getId());
        } catch (NoSuchDataException e){
            PreparedStatement createStatement = DBCONNECTION.prepareStatement(INSERT);
            createStatement.setInt(1,clientsEntity.getId());
            createStatement.setString(2,clientsEntity.getName());
            createStatement.setString(3,clientsEntity.getSurname());
            createStatement.setDouble(4,clientsEntity.getCash());
            createStatement.execute();
        }
        logger.error("You are trying to insert duplicate key");
    }

    public void create(Integer id,String name,String surname,double cash) throws SQLException{
        try{
            checkIfPresent(id);
        } catch (NoSuchDataException e){
            PreparedStatement createStatement = DBCONNECTION.prepareStatement(INSERT);
            createStatement.setInt(1,id);
            createStatement.setString(2,name);
            createStatement.setString(3,surname);
            createStatement.setDouble(4,cash);
            createStatement.execute();
        }
        logger.error("You are trying to insert duplicate key");
    }

    @Override
    public void update(ClientsEntity client) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(client.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,client.getName());
            updateStatement.setString(2,client.getSurname());
            updateStatement.setDouble(3,client.getCash());
            updateStatement.setInt(4,client.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    public void update(Integer updateClientId,String name,String surname,double cash) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(updateClientId);
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,name);
            updateStatement.setString(2,surname);
            updateStatement.setDouble(3,cash);
            updateStatement.setInt(4,updateClientId);
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(id);
            PreparedStatement deleteStatement = DBCONNECTION.prepareStatement(DELETE);
            deleteStatement.setInt(1,id);
            deleteStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to delete does not exist");
            throw e;
        }
    }
}
