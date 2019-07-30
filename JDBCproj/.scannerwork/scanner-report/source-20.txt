package com.epam.igorpystovit.view;

import com.epam.igorpystovit.model.TableEditor;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablesPrinterMenu<T> implements Menu {
    private TableEditor<T> tableEditor = new TableEditor<>();

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>(){{
            put(1,"To print Flights table");
            put(2,"To print Company planes table");
            put(3,"To print Orders table");
            put(4,"To print Planes table");
            put(5,"To print Clients table");
            put(6,"To print Companies table");
        }};
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>(){{
           put(1,() -> tableEditor.edit(TableType.FLIGHTS,ModificationType.READ_ALL));
           put(2,() -> tableEditor.edit(TableType.PLANES_COMPANIES,ModificationType.READ_ALL));
           put(3,() -> tableEditor.edit(TableType.ORDERS,ModificationType.READ_ALL));
           put(4,() -> tableEditor.edit(TableType.PLANES,ModificationType.READ_ALL));
           put(5,() -> tableEditor.edit(TableType.CLIENTS,ModificationType.READ_ALL));
           put(6,() -> tableEditor.edit(TableType.COMAPNIES,ModificationType.READ_ALL));
        }};
        return menuActions;
    }
}
