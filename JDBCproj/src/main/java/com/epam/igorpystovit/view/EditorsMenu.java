package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.entityfactory.EntityFactory;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditorsMenu<T,ID> implements Menu{
    private Service<T,ID> service;
    private EntityFactory<T> entityFactory;
    private TableType tableType;

    public EditorsMenu(){}
    public EditorsMenu(Service<T,ID> service, EntityFactory<T> entityFactory){
        this.entityFactory = entityFactory;
        this.service = service;
    }
    public EditorsMenu(TableType tableType){
        this.tableType = tableType;
    }

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer,String> menuItems = new LinkedHashMap<>(){{
           put(1,"To add new row");
        }};
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions(){
        Map<Integer,Runnable> menuActions = new LinkedHashMap<>(){{
            put(1,() -> edit(tableType,ModificationType.CREATE));
        }};
        return null;
    }

    @SuppressWarnings("unchecked")
    private void edit(TableType tableType,ModificationType modificationType){
        Service service = tableType.getService();
        EntityFactory entityFactory = tableType.getEntityFactory();

        try{
            switch (modificationType){
                case CREATE:
                    service.create(entityFactory.create());
                    break;
                default:
            }
        } catch (SQLException e){
            logger.error(e);
        }
    }
}
