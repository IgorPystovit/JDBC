package com.epam.igorpystovit.model.entityfactory;

import com.epam.igorpystovit.view.ModificationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class EntityFactory<T,ID> {
    protected Logger logger = LogManager.getLogger(EntityFactory.class);

    protected abstract T create(ID id);

    public T createEntity(ID id){
        return create(id);
    }

}
