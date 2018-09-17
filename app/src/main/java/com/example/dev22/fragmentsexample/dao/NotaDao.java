package com.example.dev22.fragmentsexample.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.dev22.fragmentsexample.controllers.AlunoController;
import com.example.dev22.fragmentsexample.controllers.MateriaController;
import com.example.dev22.fragmentsexample.databases.Database;

import com.example.dev22.fragmentsexample.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class NotaDao extends GenericDao<Nota> {
    @Override
    public boolean save(Nota obj) {
        if(obj.getCodigo() == null) {
          return database.insert(Database.TABLE_NOTA,null, objectToContentValues(obj)) != -1;
        } else {
          return database.update(Database.TABLE_NOTA,objectToContentValues(obj), Database.FIELD_TABLE_NOTA_CODIGO
                  + " = ?", new String[]{String.valueOf(obj.getCodigo())}) > 0;
        }
    }

    @Override
    public boolean delete(Nota obj) {
        return database.delete(Database.TABLE_NOTA,Database.FIELD_TABLE_NOTA_CODIGO + " = ?",
               new String[]{String.valueOf(obj.getCodigo())}) > 0;
    }

    @Override
    public Nota find(Long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(Database.TABLE_NOTA,null,
                    Database.FIELD_TABLE_NOTA_CODIGO + " = ?", new String[]{String.valueOf(id)},
                    null,null,Database.FIELD_TABLE_NOTA_ALUNO,null);
            return cursor.moveToFirst() ? cursorToObject(cursor) : null;
        } finally {
            if(cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public List<Nota> findAll() {
        Cursor cursor = null;
        try {
            List<Nota> nota = new ArrayList<>();
            cursor = database.query(Database.TABLE_NOTA,null, null,
                    null,null,null, Database.FIELD_TABLE_NOTA_ALUNO,null);
            while (cursor.moveToNext()) {
                nota.add(cursorToObject(cursor));
            }
            return nota;
        } finally {
            if(cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    @Override
    public Nota cursorToObject(Cursor cursor) {
        Nota nota = new Nota();
        nota.setCodigo(cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_CODIGO)));
        nota.setAluno(AlunoController.getInstance().find
                (cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_ALUNO))));
        nota.setMateria(MateriaController.getInstance().find
                (cursor.getLong(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_MATERIA))));
        nota.setNota1(cursor.getDouble(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_NOTA1)));
        nota.setNota2(cursor.getDouble(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_NOTA2)));
        nota.setNota3(cursor.getDouble(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_NOTA3)));
        nota.setNota4(cursor.getDouble(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_NOTA4)));
        nota.setFaltas(cursor.getInt(cursor.getColumnIndex(Database.FIELD_TABLE_NOTA_FALTAS)));
        return nota;
    }

    @Override
    public ContentValues objectToContentValues(Nota obj) {
        ContentValues contentValues = new ContentValues();

        if (obj.getCodigo() == null) {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_CODIGO);
        } else {
            contentValues.put(Database.FIELD_TABLE_NOTA_CODIGO, obj.getCodigo());
        }

        if (obj.getAluno() != null && obj.getAluno().getCodigo() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_ALUNO, obj.getAluno().getCodigo());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_ALUNO);
        }

        if (obj.getMateria() != null && obj.getMateria().getCodigo() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_MATERIA, obj.getMateria().getCodigo());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_MATERIA);
        }

        if (obj.getNota1() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_NOTA1, obj.getNota1());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_NOTA1);
        }

        if (obj.getNota2() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_NOTA2, obj.getNota2());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_NOTA2);
        }

        if (obj.getNota3() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_NOTA3, obj.getNota3());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_NOTA3);
        }

        if (obj.getNota4() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_NOTA4, obj.getNota4());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_NOTA4);
        }

        if (obj.getFaltas() != null) {
            contentValues.put(Database.FIELD_TABLE_NOTA_FALTAS, obj.getFaltas());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_NOTA_FALTAS);

        }
        return contentValues;
    }
}
