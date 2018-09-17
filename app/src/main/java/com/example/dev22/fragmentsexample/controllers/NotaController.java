package com.example.dev22.fragmentsexample.controllers;


import com.example.dev22.fragmentsexample.dao.NotaDao;
import com.example.dev22.fragmentsexample.model.Nota;

public class NotaController extends GenericController<Nota> {

    private static NotaController instance;


    public static NotaController getInstance(){
        if(instance == null) {
            instance = new NotaController();
        }
        return instance;
    }

    private NotaController() {
        super(new NotaDao());
    }
}
