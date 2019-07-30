package com.epam.igorpystovit.view;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainMenu implements Menu{

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>();
        menuItems.put(1,"To edit tables");
        menuItems.put(2,"To print table's content");
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>();
        menuActions.put(1,() -> new TablesMenu().launch());
        menuActions.put(2,() -> new TablesPrinterMenu<>().launch());
        return menuActions;
    }
}
