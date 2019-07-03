package com.epam.igorpystovit.model.entities;

import com.epam.igorpystovit.DateTimeParser;
import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.PrimaryKey;
import com.epam.igorpystovit.model.Annotations.Table;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Table(name = "Flights")
public class FlightsEntity {
    @PrimaryKey
    @Column(name = "id",length = 200)
    private int id;
    @Column(name = "company_id",length = 200)
    private int companyId;
    @Column(name = "departure_town_id",length = 200)
    private int departureTownId;
    @Column(name = "arrival_town_id",length = 200)
    private int arrivalTownId;
    @Column(name = "departure_date",length = 220)
    private Date departureDate;
    @Column(name = "departure_time",length = 220)
    private Time departureTime;
    @Column(name = "arrival_date",length = 220)
    private Date arrivalDate;
    @Column(name = "arrival_time",length = 220)
    private Time arrivalTime;
    @Column(name = "price",length = 200)
    private double price;

    private DateTimeParser dateTimeParser = new DateTimeParser();

    public FlightsEntity(){}
    public FlightsEntity(int id,int companyId,int departureTownId,int arrivalTownId,
                         String departureDate,String departureTime,String arrivalDate,String arrivalTime,double price){
        this.id = id;
        this.companyId = companyId;
        this.departureTownId = departureTownId;
        this.arrivalTownId = arrivalTownId;
        this.departureDate = dateTimeParser.dateParser(departureDate);
        this.departureTime = dateTimeParser.timeParser(departureTime);
        this.arrivalDate = dateTimeParser.dateParser(arrivalDate);
        this.arrivalTime = dateTimeParser.timeParser(arrivalTime);
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setDepartureTownId(int departureTownId) {
        this.departureTownId = departureTownId;
    }

    public int getDepartureTownId() {
        return departureTownId;
    }

    public void setArrivalTownId(int arrivalTownId) {
        this.arrivalTownId = arrivalTownId;
    }

    public int getArrivalTownId() {
        return arrivalTownId;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = dateTimeParser.dateParser(departureDate);
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = dateTimeParser.timeParser(departureTime);
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = dateTimeParser.dateParser(arrivalDate);
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = dateTimeParser.timeParser(arrivalTime);
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("id = "+id+"\t"+" Company id = "+companyId+"\t"+" Departure town id = "+departureTownId+"\t"+
                " Arrival town id = "+arrivalTownId+"\t"+" Departure date = "+departureDate+"\t"+" Departure time = "+departureTime+"\t"+
                " Arrival date = "+arrivalDate+"\t"+" Arrival time = "+arrivalTime);
        return sb.toString();
    }
}
