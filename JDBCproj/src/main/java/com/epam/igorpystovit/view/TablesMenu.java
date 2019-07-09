package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.ClientsService;
import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.entityfactory.ClientsEntityFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablesMenu implements Menu{
    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>(){{
           put(1,"To edit Flights table");
           put(2,"To edit Company planes table");
           put(3,"To edit Orders table");
           put(4,"To edit Planes table");
           put(5,"To edit Clients table");
           put(6,"To edit Companies table");
           put(7,"To edit Towns table");
        }};
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>(){{
           put(1,() -> new EditorsMenu<>(TableType.FLIGHTS).launch());
           put(2,() -> new EditorsMenu<>(TableType.PLANES_COMPANIES).launch());
           put(3,() -> new EditorsMenu<>(TableType.ORDERS).launch());
           put(4,() -> new EditorsMenu<>(TableType.PLANES).launch());
           put(5,() -> new EditorsMenu<>(TableType.CLIENTS).launch());
           put(6,() -> new EditorsMenu<>(TableType.COMAPNIES).launch());
           put(7,() -> new EditorsMenu<>(TableType.TOWNS).launch());
        }};
        return menuActions;
    }
}
