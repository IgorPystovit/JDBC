package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.OrdersDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.OrdersEntity;
import com.epam.igorpystovit.model.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    private final String SELECT = "select * from Orders";
    private final String SELECT_BY_ID = "select * from Orders where id = ?";
    private final String INSERT = "insert Orders(id,client_id,flight_id) values(?,?,?)";
    private final String UPDATE = "update Orders set client_id = ?,flight_id = ? where id = ?";
    private final String UPDATE_FLIGHT = "update Orders set flight_id = ? where id = ?";
    private final String UPDATE_CLIENT = "update Orders set client_id = ? where id = ?";
    private final String DELETE = "delete from Orders where id = ?";

    private Transformer transformer = new Transformer<>(OrdersEntity.class);
    private final Connection DBCONNECTION;

    public OrdersDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<OrdersEntity> getAll() throws SQLException {
        List<OrdersEntity> orders = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT);
        while (selectResult.next()){
            orders.add((OrdersEntity) transformer.transformFromResultSet(selectResult));
        }
        return orders;
    }

    @Override
    public OrdersEntity getById(Integer id) throws SQLException, NoSuchDataException {
        OrdersEntity order = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet selectByIdResult = selectByIdStatement.executeQuery();
        while (selectByIdResult.next()){
            order = (OrdersEntity) transformer.transformFromResultSet(selectByIdResult);
        }

        if (order == null){
            throw new NoSuchDataException();
        }

        return order;
    }

    @Override
    public void create(OrdersEntity order) throws SQLException {
        try{
            checkIfPresent(order.getId());
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(2,order.getClientId());
            insertStatement.setInt(3,order.getFlightId());
            insertStatement.setInt(1,order.getId());
            insertStatement.execute();
        }
    }


    @Override
    public void update(OrdersEntity order) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(order.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setInt(1,order.getClientId());
            updateStatement.setInt(2,order.getFlightId());
            updateStatement.setInt(3,order.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
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
