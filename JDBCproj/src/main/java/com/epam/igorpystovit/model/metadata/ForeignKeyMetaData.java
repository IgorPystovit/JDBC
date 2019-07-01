package com.epam.igorpystovit.model.metadata;

public class ForeignKeyMetaData {
    private String tableName;
    private String fkColumnName;
    private String columnName;


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fkColumnName: "+ fkColumnName +"\n");
        sb.append("\t"+"Table name: "+tableName+"\n");
        sb.append("\t"+"Column name: "+columnName+"\n");
        return sb.toString();
    }
}
