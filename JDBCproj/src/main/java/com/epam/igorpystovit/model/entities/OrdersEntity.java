package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Orders")
public class OrdersEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "client_id",length = 200)
    private int clientId;
    @Column(name = "flight_id",length = 200)
    private int flightId;

    public OrdersEntity(){}
    public OrdersEntity(int id,int clientId,int flightId){
        this.id = id;
        this.clientId = clientId;
        this.flightId = flightId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" id = "+id+"\t"+" Client id ="+clientId+"\t"+" Flight id = "+flightId+"\n");
        return sb.toString();
    }
}
