package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.OrdersEntity;

public class OrdersEntityFactory extends EntityFactory<OrdersEntity>{
    @Override
    protected OrdersEntity createEntity() {
        logger.info("Input order id:");
        int orderId = Reader.readInt();
        logger.info("Input client id:");
        int clientId = Reader.readInt();
        logger.info("Input flight id:");
        int flightId = Reader.readInt();
        return new OrdersEntity(orderId,clientId,flightId);
    }
}
