package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;

public class PK_FlightsPlanes {
    @Column(name = "flight_id",length = 200)
    private int flightId;
    @Column(name = "plane_id",length = 200)
    private int planeId;

    public PK_FlightsPlanes(){}
    public PK_FlightsPlanes(int flightId,int planeId){
        this.flightId = flightId;
        this.planeId = planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }
}
