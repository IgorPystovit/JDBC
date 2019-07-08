package com.epam.igorpystovit.model.entityfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public abstract class EntityFactory<T> {
    protected Scanner scan = new Scanner(System.in);
    protected Logger logger = LogManager.getLogger(EntityFactory.class);

    protected abstract T createEntity();
//    abstract T updateEntity();

    public T create(){
        return createEntity();
    }

//    public T update(){
//        return updateEntity();
//    }

}
