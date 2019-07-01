package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.MetaDataDAO;
import com.epam.igorpystovit.DAOPattern.TownsDAOImpl;
import com.epam.igorpystovit.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.entities.TownsEntity;
import com.epam.igorpystovit.model.metadata.TableMetaData;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
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
        TownsDAOImpl townsDAO = new TownsDAOImpl();
        try{
            System.out.println(townsDAO.createTown(4,"Some town"));
            System.out.println(townsDAO.getTownById(4));
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
