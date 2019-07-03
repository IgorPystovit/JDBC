package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Companies")
public class CompaniesEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "name",length = 200)
    private String name;

    public CompaniesEntity(){}
    public CompaniesEntity(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID = "+id+"\t"+" Company name = "+name+"\n");
        return sb.toString();
    }
}
