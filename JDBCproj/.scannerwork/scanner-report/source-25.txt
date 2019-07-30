package com.epam.igorpystovit.view;

import com.epam.igorpystovit.controller.services.Service;
import com.epam.igorpystovit.model.NoSuchDataException;
import com.epam.igorpystovit.model.TableEditor;
import com.epam.igorpystovit.model.entityfactory.EntityFactory;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditorsMenu<T> implements Menu {
    private TableType tableType;
    private TableEditor<T> tableEditor = new TableEditor<>();

    public EditorsMenu() {}
    public EditorsMenu(TableType tableType) {
        this.tableType = tableType;
    }

    @Override
    public Map<Integer, String> getMenuItems() {
        Map<Integer, String> menuItems = new LinkedHashMap<>() {{
            put(1, "To add new row");
            put(2, "To delete the row");
            put(3, "To read row by id");
            put(4, "To read all rows");
            put(5, "To update row");
        }};
        return menuItems;
    }

    @Override
    public Map<Integer, Runnable> getMenuActions() {
        Map<Integer, Runnable> menuActions = new LinkedHashMap<>() {{
            put(1, () -> tableEditor.edit(tableType, ModificationType.CREATE));
            put(2, () -> tableEditor.edit(tableType, ModificationType.DELETE));
            put(3, () -> tableEditor.edit(tableType, ModificationType.READ));
            put(4, () -> tableEditor.edit(tableType, ModificationType.READ_ALL));
            put(5, () -> tableEditor.edit(tableType, ModificationType.UPDATE));
        }};
        return menuActions;
    }

}
