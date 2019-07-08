package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.ClientsService;
import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.entityfactory.ClientsEntityFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablesMenu<T,ID> implements Menu{
    private static Service service;
    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>(){{
           put(1,"To edit Clients table");
        }};
        return null;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>(){{
           put(1,() -> new EditorsMenu<>(TableType.CLIENTS).launch());


        }};
        return null;
    }
}
