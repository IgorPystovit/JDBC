package com.epam.igorpystovit.controller.services;

public class FlightsPlanesService {

    private int validateSeatNum(int seatNum,int maxSeatNum){
        int compareResult = 0;
        if (maxSeatNum < seatNum) {
            seatNum = maxSeatNum;
        }
        else if (seatNum < 0){
            seatNum = 0;
        }
        return seatNum;
    }
}
