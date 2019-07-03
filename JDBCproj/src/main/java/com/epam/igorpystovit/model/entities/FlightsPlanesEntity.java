package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.PrimaryKeyComposite;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Flights_Planes")
public class FlightsPlanesEntity {
    @PrimaryKeyComposite
    private PK_FlightsPlanes primaryKey;
    @Column(name = "seat_num",length = 200)
    private int seatNum;

    public FlightsPlanesEntity(){}
    public FlightsPlanesEntity(PK_FlightsPlanes primaryKey,int seatNum){
        this.primaryKey = primaryKey;
        this.seatNum = seatNum;
    }

    public void setPrimaryKey(PK_FlightsPlanes primaryKey) {
        this.primaryKey = primaryKey;
    }

    public PK_FlightsPlanes getPrimaryKey() {
        return primaryKey;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plane id = "+primaryKey.getPlaneId()+"\t"+" Flight id = "+primaryKey.getFlightId()+"\t"+" Seat num = "+seatNum+"\n");
        return sb.toString();
    }
}
