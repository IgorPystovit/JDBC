package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.model.Reader;
import com.epam.igorpystovit.model.entities.ClientsEntity;

public class ClientsEntityFactory extends EntityFactory<ClientsEntity,Integer> {

    @Override
    protected ClientsEntity create(Integer id) {
//        int clientId = Reader.readInt();
        logger.info("Input new clients name:");
        String name = Reader.readString();
        logger.info("Input new clients surname:");
        String surname = Reader.readString();
        logger.info("Input new clients cash:");
        double cash = Reader.readDouble();
        return new ClientsEntity(id,name,surname,cash);
    }

}
