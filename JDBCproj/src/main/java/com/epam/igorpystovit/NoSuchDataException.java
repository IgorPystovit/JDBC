package com.epam.igorpystovit;

public class NoSuchDataException extends Exception{
    public NoSuchDataException(){}
    public NoSuchDataException(String msg){
        super(msg);
    }
}
