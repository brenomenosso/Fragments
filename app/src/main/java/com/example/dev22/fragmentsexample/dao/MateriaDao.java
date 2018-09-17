package com.example.dev22.fragmentsexample.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.dev22.fragmentsexample.controllers.ProfessorController;
import com.example.dev22.fragmentsexample.databases.Database;
import com.example.dev22.fragmentsexample.model.Materia;


import java.util.ArrayList;
import java.util.List;

public class MateriaDao extends GenericDao<Materia> {

    @Override
    public boolean save(Materia obj) {
        if (obj.getCodigo() == null) {
            return database.insert(Database.TABLE_MATERIA, null, objectToContentValues(obj)) != -1;
        } else {
            return database.update(Database.TABLE_MATERIA, objectToContentValues(obj),
                    Database.FIELD_TABLE_MATERIA_CODIGO +
                            " = ?", new String[]{String.valueOf(obj.getCodigo())}) > 0;
        }
    }

    @Override
    public boolean delete(Materia obj) {
        return database.delete(Database.TABLE_MATERIA, Database.FIELD_TABLE_MATERIA_CODIGO +
                " = ?", new String[]{String.valueOf(obj.getCodigo())}) > 0;
    }

    @Override
    public Materia find(Long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(Database.TABLE_MATERIA, null, Database.FIELD_TABLE_MATERIA_CODIGO + " = ?",
                    new String[]{String.valueOf(id)}, null, null, Database.FIELD_TABLE_MATERIA_NOME, null);
            return cursor.moveToFirst() ? cursorToObject(cursor) : null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public List<Materia> findAll() {
        Cursor cursor = null;
        try {
            List<Materia> materia = new ArrayList<>();
            cursor = database.query(Database.TABLE_MATERIA, null, null,
                    null, null, null, Database.FIELD_TABLE_MATERIA_NOME, null);
            while (cursor.moveToNext()) {
                materia.add(cursorToObject(cursor));
            }
            return materia;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public Materia cursorToObject(Cursor cursor) {
        Materia materia = new Materia();
        materia.setCodigo(cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_MATERIA_CODIGO)));
        materia.setNome(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_MATERIA_NOME)));
        materia.setProfessor(ProfessorController.getInstance().find(
                cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_MATERIA_PROFESSOR))));
        materia.setDescricao(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_MATERIA_DESCRICAO)));
        return materia;
    }

    @Override
    public ContentValues objectToContentValues(Materia obj) {
        ContentValues contentValues = new ContentValues();
        if (obj.getCodigo() == null) {
            contentValues.putNull(Database.FIELD_TABLE_MATERIA_CODIGO);
        } else {
            contentValues.put(Database.FIELD_TABLE_MATERIA_CODIGO, obj.getCodigo());
        }

        if (!TextUtils.isEmpty(obj.getNome())) {
            contentValues.put(Database.FIELD_TABLE_MATERIA_NOME, obj.getNome());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_MATERIA_NOME);
        }

        if (obj.getProfessor() != null && obj.getProfessor().getCodigo() != null) {
            contentValues.put(Database.FIELD_TABLE_MATERIA_PROFESSOR, obj.getProfessor().getCodigo());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_MATERIA_PROFESSOR);
        }

        if (!TextUtils.isEmpty(obj.getDescricao())) {
            contentValues.put(Database.FIELD_TABLE_MATERIA_DESCRICAO, obj.getDescricao());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_MATERIA_DESCRICAO);
        }

        return contentValues;
    }
}
