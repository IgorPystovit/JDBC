package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.CompaniesDAOImpl;
import com.epam.igorpystovit.DAOPattern.PlanesDAOImpl;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.entities.CompaniesEntity;
import com.epam.igorpystovit.entities.PlaneType;
import com.epam.igorpystovit.entities.PlanesEntity;
import com.epam.igorpystovit.entities.TownsEntity;

import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args) {
//        try{
//            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
//            Date date = format.parse("23:18:02");
//            System.out.println(date);
//
//            LocalTime localTime = LocalTime.parse("11:00:30");
//            System.out.println(localTime);
//        } catch (Exception e){
//            System.out.println(e);
//        }

        PlanesDAOImpl planesDAO = new PlanesDAOImpl();
        TownsDAOImpl townsDAO = new TownsDAOImpl();
        try{
            planesDAO.delete(2);
            List<PlanesEntity> planes = planesDAO.getAll();
            for (PlanesEntity plane : planes){
                System.out.println(plane);
            }

        } catch (SQLException|NoSuchDataException e){
            e.printStackTrace();
        }
    }
}
