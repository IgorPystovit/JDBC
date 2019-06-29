package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.MetaDataDAO;
import com.epam.igorpystovit.connectionmanager.ConnectionManager;
import com.epam.igorpystovit.metadata.TableMetaData;
import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        try{
            List<TableMetaData> tablesData = new MetaDataDAO().getDatabaseSummary(ConnectionManager.getConnection());

            for (TableMetaData table : tablesData){
                System.out.println(table);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
