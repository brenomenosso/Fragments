package com.example.dev22.fragmentsexample.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.dev22.fragmentsexample.databases.Database;
import com.example.dev22.fragmentsexample.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao extends GenericDao<Aluno> {
    @Override
    public boolean save(Aluno obj) {
        if (obj.getCodigo() == null) {
            return database.insert(Database.TABLE_ALUNO, null, objectToContentValues(obj)) != -1;
        } else {
            return database.update(Database.TABLE_ALUNO, objectToContentValues(obj),
                    Database.FIELD_TABLE_ALUNO_CODIGO + " = ?",
                    new String[]{String.valueOf(obj.getCodigo())}) > 0;
        }
    }

    @Override
    public boolean delete(Aluno obj) {
        return database.delete(Database.TABLE_ALUNO, Database.FIELD_TABLE_ALUNO_CODIGO + " = ?",
                new String[]{String.valueOf(obj.getCodigo())}) > 0;
    }

    @Override
    public Aluno find(Long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(Database.TABLE_ALUNO, null,
                    Database.FIELD_TABLE_ALUNO_CODIGO + " = ?", new String[]{String.valueOf(id)},
                    null, null, Database.FIELD_TABLE_ALUNO_NOME, null);
            return cursor.moveToFirst() ? cursorToObject(cursor) : null;
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public List<Aluno> findAll() {
        Cursor cursor = null;
        try {
            List<Aluno> aluno = new ArrayList<>();
            cursor = database.query(Database.TABLE_ALUNO, null, null,
                    null, null, null, Database.FIELD_TABLE_ALUNO_NOME, null);
            while (cursor.moveToNext()) {
                aluno.add(cursorToObject(cursor));
            }
            return aluno;
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public Aluno cursorToObject(Cursor cursor) {
        Aluno aluno = new Aluno();
        aluno.setCodigo(cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_CODIGO)));
        aluno.setNome(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_NOME)));
        aluno.setEndereco(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_ENDERECO)));
        aluno.setNumero(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_NUMERO)));
        aluno.setCep(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_CEP)));
        aluno.setCidade(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_CIDADE)));
        aluno.setEstado(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_ESTADO)));
        aluno.setComplemento(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_ALUNO_COMPLEMENTO)));
        return aluno;
    }

    @Override
    public ContentValues objectToContentValues(Aluno obj) {
        ContentValues contentValues = new ContentValues();

        if (obj.getCodigo() == null) {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_CODIGO);
        } else {
            contentValues.put(Database.FIELD_TABLE_ALUNO_CODIGO, obj.getCodigo());
        }

        if (!TextUtils.isEmpty(obj.getNome())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_NOME, obj.getNome());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_NOME);
        }

        if (!TextUtils.isEmpty(obj.getEndereco())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_ENDERECO, obj.getEndereco());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_ENDERECO);
        }

        if (!TextUtils.isEmpty(obj.getNumero())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_NUMERO, obj.getNumero());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_NUMERO);
        }

        if (!TextUtils.isEmpty(obj.getCep())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_CEP, obj.getCep());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_CEP);
        }

        if (!TextUtils.isEmpty(obj.getCidade())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_CIDADE, obj.getCidade());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_CIDADE);
        }

        if (!TextUtils.isEmpty(obj.getEstado())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_ESTADO, obj.getEstado());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_ESTADO);
        }

        if (!TextUtils.isEmpty(obj.getComplemento())) {
            contentValues.put(Database.FIELD_TABLE_ALUNO_COMPLEMENTO, obj.getComplemento());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ALUNO_COMPLEMENTO);
        }

        return contentValues;
    }
}
