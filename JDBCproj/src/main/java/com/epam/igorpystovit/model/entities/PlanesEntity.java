package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Planes")
public class PlanesEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "plane_name",length = 255)
    private String name;
    @Column(name = "capacity",length = 200)
    private int capacity;
    @Column(name = "plane_type",length = 200)
    private PlaneType planeType;

    public PlanesEntity(){}
    public PlanesEntity(int id,String name,int capacity,PlaneType planeType){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.planeType = planeType;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = "+id+"\t"+"name = "+name+"\t"+"capacity = "+capacity+"\t"+"plane type = "+planeType.toString().toLowerCase()+"\n");
        return sb.toString();
    }
}
