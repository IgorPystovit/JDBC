package com.epam.igorpystovit.DAOPattern.daointerface;

import com.epam.igorpystovit.NoSuchDataException;
import com.epam.igorpystovit.model.entities.OrdersEntity;

import java.sql.SQLException;

public interface OrdersDAO extends GeneralDAO<OrdersEntity,Integer>{}
