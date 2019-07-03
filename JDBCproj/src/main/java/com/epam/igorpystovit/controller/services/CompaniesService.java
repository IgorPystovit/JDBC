package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.CompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.CompaniesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.CompaniesEntity;

import java.sql.SQLException;
import java.util.List;

public class CompaniesService implements CompaniesDAO {
    private CompaniesDAOImpl companiesDAO = new CompaniesDAOImpl();

    @Override
    public List<CompaniesEntity> getAll() throws SQLException {
        return companiesDAO.getAll();
    }

    @Override
    public CompaniesEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return companiesDAO.getById(id);
    }

    @Override
    public void create(CompaniesEntity company) throws SQLException {
        companiesDAO.create(company);
    }

    @Override
    public void create(Integer id, String name) throws SQLException {
        companiesDAO.create(id,name);
    }

    @Override
    public void update(CompaniesEntity company) throws SQLException, NoSuchDataException {
        companiesDAO.update(company);
    }

    @Override
    public void update(Integer updateCompanyId, String newName) throws SQLException, NoSuchDataException {
        companiesDAO.update(updateCompanyId,newName);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        companiesDAO.delete(id);
    }
}
