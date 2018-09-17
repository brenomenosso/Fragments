package com.example.dev22.fragmentsexample.controllers;

import com.example.dev22.fragmentsexample.dao.ProfessorDao;
import com.example.dev22.fragmentsexample.model.Professor;

public class ProfessorController extends GenericController<Professor> {

    private static ProfessorController instance;

    private Professor professor;

    public static ProfessorController getInstance() {
        if (instance == null) {
            instance = new ProfessorController();
        }
        return instance;
    }

    private ProfessorController() {
        super(new ProfessorDao());
    }

    public Professor getProfessorLogado() {
        return professor;
    }

    public void logout() {
        professor = null;
    }

    public boolean checkLogin(String usuario, String senha) {
        professor = ((ProfessorDao) dao).checkLogin(usuario, senha);
        return professor != null;
    }
}
