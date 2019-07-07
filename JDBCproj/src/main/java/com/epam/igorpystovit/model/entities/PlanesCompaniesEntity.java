package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

@Table(name = "Planes_Companies")
public class PlanesCompaniesEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "company_id",length = 200)
    private int companyId;
    @Column(name = "plane_id",length = 200)
    private int planeId;
    @Column(name = "available_seats",length = 200)
    private int availableSeats;

    public PlanesCompaniesEntity(){}
    public PlanesCompaniesEntity(int id, int companyId, int planeId, int availableSeats) {
        this.id = id;
        this.companyId = companyId;
        this.planeId = planeId;
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" id = "+id+"\t"+" Company id = "+companyId+"\t"+" Plane id = "+
                planeId +"\t"+" Available seats = "+availableSeats+"\n");
        return sb.toString();
    }
}
