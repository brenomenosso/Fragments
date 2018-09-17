package com.example.dev22.fragmentsexample.controllers;

import com.example.dev22.fragmentsexample.dao.MateriaDao;
import com.example.dev22.fragmentsexample.model.Materia;

public class MateriaController extends GenericController<Materia> {

    private static MateriaController instance;

    public static MateriaController getInstance() {
        if (instance == null) {
            instance = new MateriaController();
        }
        return instance;
    }

    private MateriaController() {
        super(new MateriaDao());
    }

}
