package com.epam.igorpystovit.model;

public class NoSuchDataException extends Exception{
    public NoSuchDataException(){}
    public NoSuchDataException(String msg){
        super(msg);
    }
}
