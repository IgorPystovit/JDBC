package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.daoimplementations.CompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.CompaniesDAO;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entities.CompaniesEntity;
import com.epam.igorpystovit.model.entities.FlightsEntity;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesService implements CompaniesDAO,Service<CompaniesEntity,Integer> {
    private static final CompaniesDAOImpl companiesDAO = new CompaniesDAOImpl();
    private static final PlanesCompaniesService planesCompaniesService = new PlanesCompaniesService();

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
        List<PlanesCompaniesEntity> planesCompaniesEntitiesOnTheTable = getPlanesCompaniesEntitiesByCompanyId(id);

        for (PlanesCompaniesEntity tempEntity : planesCompaniesEntitiesOnTheTable){
            planesCompaniesService.delete(tempEntity.getId());
        }
        companiesDAO.delete(id);
    }

    @Override
    public Integer readId() {
        return companiesDAO.readId();
    }

    //Constraints
    private List<PlanesCompaniesEntity> getPlanesCompaniesEntitiesByCompanyId(Integer companyId) throws SQLException{
        List<PlanesCompaniesEntity> planesCompaniesEntities = planesCompaniesService.getAll();
        for (PlanesCompaniesEntity tempEntity : new ArrayList<>(planesCompaniesEntities)){
            if (tempEntity.getCompanyId() != companyId){
                planesCompaniesEntities.remove(tempEntity);
            }
        }
        return planesCompaniesEntities;
    }

}
