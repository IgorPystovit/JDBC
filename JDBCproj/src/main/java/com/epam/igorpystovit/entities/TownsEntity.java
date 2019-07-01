package com.epam.igorpystovit.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Towns")
public class TownsEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    int townId;
    @Column(name = "name", length = 200)
    String townName;

    public TownsEntity(){}
    public TownsEntity(int townId,String townName){
        this.townId = townId;
        this.townName = townName;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = "+townId+"\t"+" town name = "+townName+"\n");
        return sb.toString();
    }
}
