package com.epam.igorpystovit.model;

import com.epam.igorpystovit.controller.services.Service;

public class TableEditor<T,ID> {
    private Service<T,ID> service;

    public TableEditor(){}
    public TableEditor(Service<T, ID> service) {
        this.service = service;
    }


    public Service<T, ID> getService() {
        return service;
    }

    public void setService(Service<T, ID> service) {
        this.service = service;
    }
}
