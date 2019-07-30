package com.epam.igorpystovit.DAOPattern.daoimplementations;

import com.epam.igorpystovit.DAOPattern.daointerface.PlanesCompaniesDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;
import com.epam.igorpystovit.model.transformer.Transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanesCompaniesDAOImpl implements PlanesCompaniesDAO {
    private final String SELECT = "select * from Planes_Companies";
    private final String SELECT_BY_ID = "select * from Planes_Companies where id = ?";
    private final String INSERT = "insert Planes_Companies(id,company_id,plane_id,available_seats) values(?,?,?,?)";
    private final String UPDATE = "update Planes_Companies set company_id = ?,plane_id = ?,available_seats = ? where id = ?";
    private final String UPDATE_SEAT_NUM = "update Planes_Companies set available_seats = ? where id = ?";
    private final String DELETE = "delete from Planes_Companies where id = ?";
    private Transformer transformer = new Transformer<>(PlanesCompaniesEntity.class);
    private final Connection DBCONNECTION;

    public PlanesCompaniesDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<PlanesCompaniesEntity> getAll() throws SQLException {
        List<PlanesCompaniesEntity> planesCompanies = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT);
        while (selectResult.next()){
            planesCompanies.add((PlanesCompaniesEntity) transformer.transformFromResultSet(selectResult));
        }
        return planesCompanies;
    }

    @Override
    public PlanesCompaniesEntity getById(Integer id) throws SQLException, NoSuchDataException {
        PlanesCompaniesEntity planesCompaniesEntity = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet selectByIdResult = selectByIdStatement.executeQuery();
        while (selectByIdResult.next()){
            planesCompaniesEntity = (PlanesCompaniesEntity) transformer.transformFromResultSet(selectByIdResult);
        }
        if (planesCompaniesEntity == null){
            throw new NoSuchDataException();
        }
        return planesCompaniesEntity;

    }

    @Override
    public void create(PlanesCompaniesEntity entity) throws SQLException {
        try{
            checkIfPresent(entity.getId());
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,entity.getId());
            insertStatement.setInt(2,entity.getCompanyId());
            insertStatement.setInt(3,entity.getPlaneId());
            insertStatement.setInt(4,entity.getAvailableSeats());
            insertStatement.execute();
        }
    }

    @Override
    public void update(PlanesCompaniesEntity entity) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(entity.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setInt(1,entity.getCompanyId());
            updateStatement.setInt(2,entity.getPlaneId());
            updateStatement.setInt(3,entity.getAvailableSeats());
            updateStatement.setInt(4,entity.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("An error occurred while updating row");
            throw e;
        }

    }

    @Override
    public void updateSeatNum(Integer updateRowId, int newAvailableSeatsNum) throws SQLException, NoSuchDataException {
        try{
            checkIfPresent(updateRowId);
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE_SEAT_NUM);
            updateStatement.setInt(1,newAvailableSeatsNum);
            updateStatement.setInt(2,updateRowId);
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("An error occurred while updating row");
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


    @Override
    public Integer readId() {
        return Reader.readInt();
    }
}

