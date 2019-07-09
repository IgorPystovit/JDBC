package com.epam.igorpystovit.model;

import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.entityfactory.EntityFactory;
import com.epam.igorpystovit.view.ModificationType;
import com.epam.igorpystovit.view.TableType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TableEditor<T> {
    private Logger logger = LogManager.getLogger(TableEditor.class);

    @SuppressWarnings("unchecked")
    public void edit(TableType tableType, ModificationType modificationType){
        Service service = tableType.getService();
        EntityFactory entityFactory = tableType.getEntityFactory();

        try{
            switch (modificationType){
                case CREATE:
                    logger.info("Input new "+tableType.toString().toLowerCase()+" id");
                    service.create(entityFactory.createEntity(service.readId()));
                    break;
                case DELETE:
                    logger.info("Input row id to delete");
                    service.delete(service.readId());
                    break;
                case READ:
                    logger.info("Input row id");
                    System.out.println(service.getById(service.readId()));
                    break;
                case READ_ALL:
                    List<T> dataList = service.getAll();
                    if (dataList.size() == 0){
                        logger.info("Nothing is on the table");
                    }
                    for (T tempData : dataList){
                        System.out.println(tempData);
                    }
                    break;
                case UPDATE:
                    logger.info("Input "+tableType.toString().toLowerCase()+" id to update");
                    service.update(entityFactory.createEntity(service.readId()));
                    break;
                default:
                    logger.warn("No such modification type");
            }
        } catch (SQLException | NoSuchDataException e){
            logger.error(e);
        }
    }
}

