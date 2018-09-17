package com.example.dev22.fragmentsexample.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.dev22.fragmentsexample.databases.Database;
import com.example.dev22.fragmentsexample.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDao extends GenericDao<Professor> {

    @Override
    public boolean save(Professor obj) {
        if (obj.getCodigo() == null) {
            return database.insert(Database.TABLE_PROFESSOR, null, objectToContentValues(obj)) != -1;
        } else {
            return database.update(Database.TABLE_PROFESSOR, objectToContentValues(obj),
                    Database.FIELD_TABLE_PROFESSOR_CODIGO
                            + " = ?", new String[]{String.valueOf(obj.getCodigo())}) > 0;
        }
    }

    @Override
    public boolean delete(Professor obj) {
        return database.delete(Database.TABLE_PROFESSOR, Database.FIELD_TABLE_PROFESSOR_CODIGO
                + " = ?", new String[]{String.valueOf(obj.getCodigo())}) > 0;
    }

    @Override
    public Professor find(Long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(Database.TABLE_PROFESSOR, null, Database.FIELD_TABLE_PROFESSOR_CODIGO + " = ?",
                    new String[]{String.valueOf(id)}, null, null, Database.FIELD_TABLE_PROFESSOR_NOME, null);

            return cursor.moveToFirst() ? cursorToObject(cursor) : null;
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public List<Professor> findAll() {
        Cursor cursor = null;

        try {
            List<Professor> professor = new ArrayList<>();
            cursor = database.query(Database.TABLE_PROFESSOR, null, null,
                    null, null, null, Database.FIELD_TABLE_PROFESSOR_NOME, null);
            while (cursor.moveToNext()) {
                professor.add(cursorToObject(cursor));
            }
            return professor;
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    @Override
    public Professor cursorToObject(Cursor cursor) {
        Professor professor = new Professor();
        professor.setCodigo(cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_CODIGO)));
        professor.setNome(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_NOME)));
        professor.setUsuario(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_USUARIO)));
        professor.setSenha(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_SENHA)));
        professor.setEndereco(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_ENDERECO)));
        professor.setEmail(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_EMAIL)));
        professor.setNumero(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_NUMERO)));
        professor.setCep(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_CEP)));
        professor.setCidade(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_CIDADE)));
        professor.setEstado(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_ESTADO)));
        professor.setComplemento(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_PROFESSOR_COMPLEMENTO)));
        return professor;
    }

    @Override
    public ContentValues objectToContentValues(Professor obj) {
        ContentValues contentValues = new ContentValues();

        if (obj.getCodigo() == null) {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_CODIGO);
        } else {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_CODIGO, obj.getCodigo());
        }

        if (!TextUtils.isEmpty(obj.getNome())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_NOME, obj.getNome());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_NOME);
        }

        if (!TextUtils.isEmpty(obj.getUsuario())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_USUARIO, obj.getUsuario());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_USUARIO);

        }

        if (!TextUtils.isEmpty(obj.getSenha())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_SENHA, obj.getSenha());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_SENHA);
        }

        if (!TextUtils.isEmpty(obj.getEndereco())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_ENDERECO, obj.getEndereco());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_ENDERECO);
        }

        if (!TextUtils.isEmpty(obj.getEmail())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_EMAIL, obj.getEmail());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_EMAIL);
        }


        if (!TextUtils.isEmpty(obj.getNumero())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_NUMERO, obj.getNumero());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_NUMERO);
        }

        if (!TextUtils.isEmpty(obj.getCep())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_CEP, obj.getCep());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_CEP);
        }

        if (!TextUtils.isEmpty(obj.getCidade())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_CIDADE, obj.getCidade());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_CIDADE);
        }

        if (!TextUtils.isEmpty(obj.getEstado())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_ESTADO, obj.getEstado());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_ESTADO);

        }

        if (!TextUtils.isEmpty(obj.getComplemento())) {
            contentValues.put(Database.FIELD_TABLE_PROFESSOR_COMPLEMENTO, obj.getComplemento());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_PROFESSOR_COMPLEMENTO);
        }

        return contentValues;
    }

    public Professor checkLogin(String usuario, String senha) {
        Cursor cursor = null;
        try {
            cursor = database.query(Database.TABLE_PROFESSOR, null, "LOWER(" + Database.FIELD_TABLE_PROFESSOR_USUARIO + ") = ? AND " +
                            Database.FIELD_TABLE_PROFESSOR_SENHA + " = ?",
                    new String[]{usuario != null ? usuario.toLowerCase() : "", senha}, null, null, null, null);

            return cursor.moveToFirst() ? cursorToObject(cursor) : null;
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }

    }
}
