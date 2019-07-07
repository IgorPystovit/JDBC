package com.epam.igorpystovit.controller.services;

import com.epam.igorpystovit.DAOPattern.PlanesCompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.daointerface.PlanesCompaniesDAO;
import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.PlanesCompaniesEntity;

import java.sql.SQLException;
import java.util.List;

public class PlanesCompaniesService implements PlanesCompaniesDAO {
    private PlanesService planesService = new PlanesService();
    private CompaniesService companiesService = new CompaniesService();
    private PlanesCompaniesDAOImpl planesCompaniesDAO = new PlanesCompaniesDAOImpl();

    @Override
    public List<PlanesCompaniesEntity> getAll() throws SQLException {
        return planesCompaniesDAO.getAll();
    }

    @Override
    public PlanesCompaniesEntity getById(Integer id) throws SQLException, NoSuchDataException {
        return planesCompaniesDAO.getById(id);
    }

    @Override
    public void create(PlanesCompaniesEntity entity) throws SQLException {
        try{
            planesService.checkIfPresent(entity.getPlaneId());
            companiesService.checkIfPresent(entity.getCompanyId());
            validateSeatNum(entity);
            planesCompaniesDAO.create(entity);
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while creating row");
        }
    }

    @Override
    public void update(PlanesCompaniesEntity entity) throws SQLException, NoSuchDataException {
        try{
            planesService.checkIfPresent(entity.getPlaneId());
            companiesService.checkIfPresent(entity.getCompanyId());
            validateSeatNum(entity);
            planesCompaniesDAO.update(entity);
        } catch (NoSuchDataException e){
            logger.error("Something went wrong while updating row");
            throw e;
        }
    }

    @Override
    public void updateSeatNum(Integer updateRowId, int newAvailableSeatsNum) throws SQLException, NoSuchDataException {
        PlanesCompaniesEntity entity = planesCompaniesDAO.getById(updateRowId);
        int maxSeatNum = planesService.getById(entity.getPlaneId()).getCapacity();
        newAvailableSeatsNum = validateSeatNum(newAvailableSeatsNum,maxSeatNum);
        planesCompaniesDAO.updateSeatNum(updateRowId,newAvailableSeatsNum);
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchDataException {
        planesCompaniesDAO.delete(id);
    }

    private void validateSeatNum(PlanesCompaniesEntity entity) throws SQLException,NoSuchDataException{
        int availableSeatNum = entity.getAvailableSeats();
        int maxSeatNum = planesService.getById(entity.getPlaneId()).getCapacity();
        if (maxSeatNum < availableSeatNum) {
            availableSeatNum = maxSeatNum;
        }
        else if (availableSeatNum < 0){
            availableSeatNum = 0;
        }
        entity.setAvailableSeats(availableSeatNum);
    }

    private int validateSeatNum(int seatNum, int maxSeatNum){
        if (maxSeatNum < seatNum) {
            seatNum = maxSeatNum;
        }
        else if (seatNum < 0){
            seatNum = 0;
        }
        return seatNum;
    }
}
