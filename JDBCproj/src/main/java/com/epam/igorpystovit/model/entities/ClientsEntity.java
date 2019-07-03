package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Clients")
public class ClientsEntity{
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "name",length = 200)
    private String name;
    @Column(name = "surname",length = 200)
    private String surname;
    @Column(name = "cash",length = 255)
    private double cash;

    public ClientsEntity(){}
    public ClientsEntity(int id,String name,String surname,double cash){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cash = cash;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID = "+id+"\t"+" Name = "+name+"\t"+" Surname = "+surname+"\t"+" Cash = "+cash+"\n");
        return sb.toString();
    }
}
