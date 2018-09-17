package com.example.dev22.fragmentsexample.controllers;

import com.example.dev22.fragmentsexample.dao.GenericDao;

import java.util.List;

public abstract class GenericController<T extends Object> {

    protected GenericDao<T> dao;

    protected GenericController(GenericDao<T> dao) {
        this.dao = dao;
    }

    public boolean save(T obj) {
        return dao.save(obj);
    }

    public boolean delete(T obj) {
        return dao.delete(obj);
    }

    public T find(Long id) {
        return dao.find(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

}