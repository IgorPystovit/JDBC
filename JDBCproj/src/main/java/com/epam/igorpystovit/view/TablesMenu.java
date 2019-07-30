package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.ClientsService;
import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.entityfactory.ClientsEntityFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablesMenu implements Menu{
    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>();
        menuItems.put(1,"To edit Flights table");
        menuItems.put(2,"To edit Planes_Companies table");
        menuItems.put(3,"To edit Orders table");
        menuItems.put(4,"To edit Planes table");
        menuItems.put(5,"To edit Clients table");
        menuItems.put(6,"To edit Companies table");
        menuItems.put(7,"To edit Towns table");
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>();
        menuActions.put(1,() -> new EditorsMenu<>(TableType.FLIGHTS).launch());
        menuActions.put(2,() -> new EditorsMenu<>(TableType.PLANES_COMPANIES).launch());
        menuActions.put(3,() -> new EditorsMenu<>(TableType.ORDERS).launch());
        menuActions.put(4,() -> new EditorsMenu<>(TableType.PLANES).launch());
        menuActions.put(5,() -> new EditorsMenu<>(TableType.CLIENTS).launch());
        menuActions.put(6,() -> new EditorsMenu<>(TableType.COMAPNIES).launch());
        menuActions.put(7,() -> new EditorsMenu<>(TableType.TOWNS).launch());
        return menuActions;
    }
}
