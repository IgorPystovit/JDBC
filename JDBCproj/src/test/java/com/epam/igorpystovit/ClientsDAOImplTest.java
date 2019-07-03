package com.epam.igorpystovit;

import com.epam.igorpystovit.DAOPattern.ClientsDAOImpl;
import com.epam.igorpystovit.model.entities.ClientsEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ClientsDAOImplTest {
    private void create_some() throws SQLException {
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        clientsDAO.create(new ClientsEntity(2,"Nastya","Hrynyshyn",2500));
    }

    @Test
    public void create_test() throws SQLException,NoSuchDataException{
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        create_some();
        Assertions.assertEquals("Nastya",clientsDAO.getById(2).getName());
    }

    @Test
    public void delete_test() throws SQLException,NoSuchDataException{
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        clientsDAO.delete(2);
        Assertions.assertThrows(NoSuchDataException.class,() -> clientsDAO.getById(2));
    }

    @Test
    public void deleting_non_exist_row_throws_an_exception(){
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> clientsDAO.delete(20));
    }

    @Test
    public void update_test() throws SQLException,NoSuchDataException{
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        clientsDAO.update(new ClientsEntity(1,"Some","Client",1.0));
        Assertions.assertEquals("Some",clientsDAO.getById(1).getName());
    }

    @Test
    public void update_non_existing_row_throws_an_exception(){
        ClientsDAOImpl clientsDAO = new ClientsDAOImpl();
        Assertions.assertThrows(NoSuchDataException.class,() -> clientsDAO.update(new ClientsEntity(5,"Some","Client",1.0)));
    }
}
