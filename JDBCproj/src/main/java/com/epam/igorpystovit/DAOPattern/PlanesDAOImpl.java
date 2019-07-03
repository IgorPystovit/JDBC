package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.PlanesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.transformer.Transformer;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.entities.PlanesEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanesDAOImpl implements PlanesDAO {
    private final String SELECT = "select * from Planes";
    private final String SELECT_BY_ID = "select * from Planes where id = ?";
    private final String INSERT = "insert Planes(id,plane_name,capacity,plane_type) values(?,?,?,?)";
    private final String UPDATE = "update Planes set plane_name = ? , capacity = ? , plane_type = ? where id = ?";
    private final String DELETE = "delete from Planes where id = ?";
    private final Connection DBCONNECTION;
    private final Transformer<PlanesEntity> transformer = new Transformer<>(PlanesEntity.class);

    public PlanesDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<PlanesEntity> getAll() throws SQLException {
        List<PlanesEntity> planes = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet planesSelectResult = selectStatement.executeQuery(SELECT);
        while (planesSelectResult.next()){
            planes.add((PlanesEntity)transformer.transformFromResultSet(planesSelectResult));
        }
        return planes;
    }

    @Override
    public PlanesEntity getById(Integer id) throws NoSuchDataException,SQLException{
        PlanesEntity plane = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet selectResult = selectByIdStatement.executeQuery();
        while (selectResult.next()){
            plane = (PlanesEntity) transformer.transformFromResultSet(selectResult);
        }
        if (plane == null){
            throw new NoSuchDataException();
        }
        return plane;
    }

    public void create(Integer id, String planeName, Integer capacity, PlaneType planeType) throws SQLException{
        try{
            checkIfPresent(id);
            logger.error("You are trying to insert duplicate key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,id);
            insertStatement.setString(2,planeName);
            insertStatement.setInt(3,capacity);
            insertStatement.setString(4,planeType.toString());
            insertStatement.execute();
        }
    }

    @Override
    public void create(PlanesEntity plane) throws SQLException{
        try{
            checkIfPresent(plane.getId());
            logger.error("You are trying to insert duplicate key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,plane.getId());
            insertStatement.setString(2,plane.getName());
            insertStatement.setInt(3,plane.getCapacity());
            insertStatement.setString(4,plane.getPlaneType().toString());
            insertStatement.execute();
        }
    }

    public void update(Integer updatePlaneId,String newPlaneName,Integer newCapacity,PlaneType newPlaneType) throws NoSuchDataException,SQLException{
        try{
            checkIfPresent(updatePlaneId);
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,newPlaneName);
            updateStatement.setInt(2,newCapacity);
            updateStatement.setString(3,newPlaneType.toString());
            updateStatement.setInt(4,updatePlaneId);
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

    @Override
    public void update(PlanesEntity plane) throws NoSuchDataException,SQLException{
        try{
            checkIfPresent(plane.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,plane.getName());
            updateStatement.setInt(2,plane.getCapacity());
            updateStatement.setString(3,plane.getPlaneType().toString().toLowerCase());
            updateStatement.setInt(4,plane.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("A row you are trying to update does not exist");
            throw e;
        }
    }

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
