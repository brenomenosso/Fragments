package com.example.dev22.fragmentsexample.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dev22.fragmentsexample.databases.DatabaseHelper;
import com.example.dev22.fragmentsexample.model.Professor;

import java.util.List;

public abstract class GenericDao<T extends Object> {

    protected SQLiteDatabase database;

    protected GenericDao() {
        this.database = DatabaseHelper.getInstance().getWritableDatabase();
    }

    public abstract boolean save(T obj);

    public abstract boolean delete(T obj);

    public abstract T find(Long id);

    public abstract List<T> findAll();

    public abstract T cursorToObject(Cursor cursor);

    public abstract ContentValues objectToContentValues(T obj);

}
