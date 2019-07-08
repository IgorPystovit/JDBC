package com.epam.igorpystovit.view;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainMenu implements Menu{

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>(){{
           put(1,"To edit tables");
           put(2,"To print table's content");
           put(3,"To book the flight");
           put(4,"To cancel reservation");
        }};
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {

        return null;
    }
}
