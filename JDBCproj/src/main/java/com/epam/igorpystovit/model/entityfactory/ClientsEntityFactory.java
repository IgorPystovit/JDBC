package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.ClientsEntity;

public class ClientsEntityFactory extends EntityFactory<ClientsEntity> {

    @Override
    protected ClientsEntity createEntity() {
        logger.info("Input clients id:");
        int clientId = Reader.readInt();
        logger.info("Input clients name:");
        String name = Reader.readString();
        logger.info("Input clients surname:");
        String surname = Reader.readString();
        logger.info("Input clients cash:");
        double cash = Reader.readDouble();
        return new ClientsEntity(clientId,name,surname,cash);
    }
}
