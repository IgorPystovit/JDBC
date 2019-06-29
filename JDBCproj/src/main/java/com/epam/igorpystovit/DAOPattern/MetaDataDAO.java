package com.epam.igorpystovit.DAOPattern;

import com.epam.igorpystovit.metadata.ColumnMetaData;
import com.epam.igorpystovit.metadata.ForeignKeyMetaData;
import com.epam.igorpystovit.metadata.TableMetaData;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataDAO {
    public List<TableMetaData> getDatabaseSummary(Connection connection) throws SQLException {
        List<TableMetaData> tablesData = new ArrayList<>();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, "%", types);
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tablesData.add(getTableMetaData(connection,tableName));
            }
        return tablesData;
    }

    public TableMetaData getTableMetaData(Connection connection,String customTableName) throws SQLException{
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, "%", types);
        TableMetaData tableMetaData = new TableMetaData();
        while (resultSet.next()) {
            String tempTableName = resultSet.getString("TABLE_NAME");
            if (!tempTableName.equalsIgnoreCase(customTableName)){
                continue;
            }

            tableMetaData.setName(tempTableName);
            tableMetaData.setDbName(connection.getCatalog());

            List<ForeignKeyMetaData> foreignKeysData = new ArrayList<>();
            ResultSet foreignKeys = databaseMetaData.getImportedKeys(connection.getCatalog(), null, tempTableName);
            while (foreignKeys.next()) {
                ForeignKeyMetaData foreignKeyMetaData = new ForeignKeyMetaData();
                foreignKeyMetaData.setColumnName(foreignKeys.getString("PKCOLUMN_NAME"));
                foreignKeyMetaData.setFkColumnName(foreignKeys.getString("FKCOLUMN_NAME"));
                foreignKeyMetaData.setTableName(foreignKeys.getString("PKTABLE_NAME"));
                foreignKeysData.add(foreignKeyMetaData);
            }

            tableMetaData.setForeignKeys(foreignKeysData);

            ResultSet columns = databaseMetaData.getColumns(connection.getCatalog(), null, tempTableName, "%");
            List<ColumnMetaData> columnsData = new ArrayList<>();
            while (columns.next()) {
                boolean present = false;
                for (ForeignKeyMetaData foreignKey : foreignKeysData) {
                    if (foreignKey.getFkColumnName().equals(columns.getString("COLUMN_NAME"))) {
                        present = true;
                        break;
                    }
                }
                if (present) {
                    continue;
                }
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setColumnName(columns.getString("COLUMN_NAME"));
                ResultSet resultSet1 = databaseMetaData.getPrimaryKeys(connection.getCatalog(), null, tempTableName);
                resultSet1.next();
                columnMetaData.setPrimaryKey(resultSet1.getString("COLUMN_NAME").equals(columnMetaData.getColumnName()));
                columnMetaData.setColumnSize(columns.getString("COLUMN_SIZE"));
                columnMetaData.setDataType(columns.getString("TYPE_NAME"));
                columnMetaData.setDecimalDigits(columns.getString("DECIMAL_DIGITS"));
                columnMetaData.setAutoIncrement(columns.getString("IS_NULLABLE").equals("YES"));
                columnMetaData.setNullable(columns.getString("IS_AUTOINCREMENT").equals("IS_AUTOINCREMENT"));
                columnsData.add(columnMetaData);
            }

            tableMetaData.setColumns(columnsData);
        }
        return tableMetaData;
    }
}
