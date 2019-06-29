package com.epam.igorpystovit.metadata;

import java.util.ArrayList;
import java.util.List;

public class TableMetaData {
    private String name;
    private String dbName;
    private List<ForeignKeyMetaData> foreignKeys;
    private List<ColumnMetaData> columns;


    public void setColumns(List<ColumnMetaData> columns) {
        this.columns = columns;
    }

    public List<ColumnMetaData> getColumns() {
        return columns;
    }

    public void setForeignKeys(List<ForeignKeyMetaData> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<ForeignKeyMetaData> getForeignKeys() {
        return foreignKeys;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TableMetaData(){
        foreignKeys = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table name: "+name+"\n");
        sb.append("\t"+"Stored at: "+dbName+"\n");
        for (ForeignKeyMetaData fkmeta : foreignKeys){
            sb.append("\t"+fkmeta+"\n");
        }

        for (ColumnMetaData column : columns){
            sb.append("\t"+column+"\n");
        }
        return sb.toString();
    }
}
