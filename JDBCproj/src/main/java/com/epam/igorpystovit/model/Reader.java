package com.epam.igorpystovit.model;

import com.epam.igorpystovit.model.datetime.DateTimeParser;
import com.epam.igorpystovit.model.entities.PlaneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Reader {
    private static Scanner scan = new Scanner(System.in);
    private static Logger logger = LogManager.getLogger(Reader.class.getName());

    public static String readString(){
        return scan.nextLine();
    }

    public static Integer readInt(){
        String userNum = null;
        int intValue = 0;
        do{
            try{
                userNum = readString();
                intValue = Integer.parseInt(userNum);
            } catch (NumberFormatException e){
                logger.error("Bad integer input");
                continue;
            }
            break;
        }while (true);
        return intValue;
    }

    public static Double readDouble(){
        String userNum = null;
        double doubleValue = 0.0;
        do{
            try {
                userNum = readString();
                doubleValue = Double.parseDouble(userNum);
            } catch (NumberFormatException e){
                logger.error("Bad double input");
                continue;
            }
            break;
        }while (true);
        return doubleValue;
    }

    public static String readDateString(){
        String stringDate = "";
        do{
            stringDate = Reader.readString();
            if (!DateTimeParser.isStringDateParsable(stringDate)){
                logger.error("Bad date input");
                continue;
            }
            break;
        } while (true);
        return stringDate;
    }

    public static String readTimeString(){
        String stringTime = "";
        do{
            stringTime = Reader.readString();
            if (!DateTimeParser.isStringTimeParsable(stringTime)){
                logger.error("Bad time input");
                continue;
            }
            break;
        }while (true);
        return stringTime;
    }

    public static PlaneType readPlaneType(){
        PlaneType planeType;
        do{
            try{
                planeType = PlaneType.valueOf(readString().toUpperCase());
            } catch (IllegalArgumentException e){
                logger.error("Bad plane type input");
                continue;
            }
            break;
        }while (true);
        return planeType;
    }
}
