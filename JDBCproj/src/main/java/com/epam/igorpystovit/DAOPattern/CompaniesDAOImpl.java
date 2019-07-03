package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.DAOPattern.daointerface.CompaniesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.transformer.Transformer;
import com.epam.igorpystovit.model.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.model.entities.CompaniesEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDAOImpl implements CompaniesDAO{
    private final String SELECT_ALL = "select * from Companies";
    private final String SELECT_BY_ID = "select * from Companies where id = ?";
    private final String INSERT = "insert Companies(id,name) values(?,?)";
    private final String UPDATE = "update Companies set name = ? where id = ?";
    private final String DELETE = "delete from Companies where id = ?";

    private final Connection DBCONNECTION;
    private Transformer<CompaniesEntity> transformer = new Transformer<>(CompaniesEntity.class);

    public CompaniesDAOImpl(){
        DBCONNECTION = ConnectionManager.getConnection();
    }

    @Override
    public List<CompaniesEntity> getAll() throws SQLException {
        List<CompaniesEntity> companies = new ArrayList<>();
        Statement selectStatement = DBCONNECTION.createStatement();
        ResultSet selectResult = selectStatement.executeQuery(SELECT_ALL);
        while (selectResult.next()){
            companies.add((CompaniesEntity) transformer.transformFromResultSet(selectResult));
        }
        return companies;
    }

    @Override
    public CompaniesEntity getById(Integer id) throws SQLException, NoSuchDataException {
        CompaniesEntity company = null;
        PreparedStatement selectByIdStatement = DBCONNECTION.prepareStatement(SELECT_BY_ID);
        selectByIdStatement.setInt(1,id);
        ResultSet companyByIdResult = selectByIdStatement.executeQuery();
        while (companyByIdResult.next()){
            company = (CompaniesEntity) transformer.transformFromResultSet(companyByIdResult);
        }
        if (company == null){
            throw new NoSuchDataException();
        }
        return company;
    }

    @Override
    public void create(CompaniesEntity company) throws SQLException{
        try{
            checkIfPresent(company.getId());
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,company.getId());
            insertStatement.setString(2,company.getName());
            insertStatement.execute();
        }
    }

    public void create(int id,String name) throws SQLException{
        try{
            checkIfPresent(id);
            logger.error("You are trying to insert duplicate primary key");
        } catch (NoSuchDataException e){
            PreparedStatement insertStatement = DBCONNECTION.prepareStatement(INSERT);
            insertStatement.setInt(1,id);
            insertStatement.setString(2,name);
            insertStatement.execute();
        }
    }

    @Override
    public void update(CompaniesEntity company) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(company.getId());
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,company.getName());
            updateStatement.setInt(2,company.getId());
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("No data with such id present on the table");
            throw e;
        }
    }

    public void update(int updateCompanyId, String name) throws SQLException,NoSuchDataException{
        try{
            checkIfPresent(updateCompanyId);
            PreparedStatement updateStatement = DBCONNECTION.prepareStatement(UPDATE);
            updateStatement.setString(1,name);
            updateStatement.setInt(2, updateCompanyId);
            updateStatement.execute();
        } catch (NoSuchDataException e){
            logger.error("No data with such updateCompanyId present on the table");
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
            logger.error("No data with such id present on the table");
            throw e;
        }
    }

}
