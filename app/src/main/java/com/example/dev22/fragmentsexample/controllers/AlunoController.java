package com.example.dev22.fragmentsexample.controllers;

import com.example.dev22.fragmentsexample.dao.AlunoDao;
import com.example.dev22.fragmentsexample.model.Aluno;

public class AlunoController extends GenericController<Aluno> {

    private static AlunoController instance;

    public static AlunoController getInstance() {
        if(instance == null) {
            instance = new AlunoController();
        }
        return instance;
    }

    private AlunoController() {
        super(new AlunoDao());
    }
}
