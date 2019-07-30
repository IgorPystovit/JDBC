package com.epam.igorpystovit.view;

import com.epam.igorpystovit.model.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public interface Menu {
    Logger logger = LogManager.getLogger(Menu.class);
    Map<Integer,String> getMenuItems();
    Map<Integer,Runnable> getMenuActions();

    default void launch(){
        Map<Integer,String> menuItems = getMenuItems();
        Map<Integer,Runnable> menuActions = getMenuActions();

        menuItems.put(0,"To exit");
        menuActions.put(0,() -> logger.info("Bye"));

        int requestId;
        do{
            printMenuItems(menuItems);
            logger.info("Input request id");
            requestId = Reader.readInt();
            menuActions.get(requestId).run();
        } while (requestId != 0);
    }

    default void printMenuItems(Map<Integer,String> menuItems){
        logger.info("Menu Items(id/description)");
        menuItems.keySet().stream()
                .forEach(a -> logger.info(a+" - "+menuItems.get(a).toLowerCase()));
    }
}
