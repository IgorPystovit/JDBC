package com.epam.igorpystovit.view;

import com.epam.igorpystovit.model.TableEditor;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablesPrinterMenu<T> implements Menu {
    private TableEditor<T> tableEditor = new TableEditor<>();

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>();
        menuItems.put(1,"To print Flights table");
        menuItems.put(2,"To print Company planes table");
        menuItems.put(3,"To print Orders table");
        menuItems.put(4,"To print Planes table");
        menuItems.put(5,"To print Clients table");
        menuItems.put(6,"To print Companies table");
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>();
        menuActions.put(1,() -> tableEditor.edit(TableType.FLIGHTS,ModificationType.READ_ALL));
        menuActions.put(2,() -> tableEditor.edit(TableType.PLANES_COMPANIES,ModificationType.READ_ALL));
        menuActions.put(3,() -> tableEditor.edit(TableType.ORDERS,ModificationType.READ_ALL));
        menuActions.put(4,() -> tableEditor.edit(TableType.PLANES,ModificationType.READ_ALL));
        menuActions.put(5,() -> tableEditor.edit(TableType.CLIENTS,ModificationType.READ_ALL));
        menuActions.put(6,() -> tableEditor.edit(TableType.COMAPNIES,ModificationType.READ_ALL));
        return menuActions;
    }
}
