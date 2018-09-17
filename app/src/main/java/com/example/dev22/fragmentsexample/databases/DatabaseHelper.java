package com.example.dev22.fragmentsexample.databases;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dev22.fragmentsexample.app.MainApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }

    private DatabaseHelper() {
        super(MainApplication.getInstance(), Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database.QUERY_CREATE_TABLE_ALUNO);
        db.execSQL(Database.QUERY_CREATE_TABLE_PROFESSOR);
        db.execSQL(Database.QUERY_CREATE_TABLE_MATERIA);
        db.execSQL(Database.QUERY_CREATE_TABLE_NOTA);
        db.execSQL("INSERT INTO " + Database.TABLE_PROFESSOR + "(" +
                Database.FIELD_TABLE_PROFESSOR_NOME + ", " +
                Database.FIELD_TABLE_PROFESSOR_USUARIO + ", " +
                Database.FIELD_TABLE_PROFESSOR_SENHA + ", " +
                Database.FIELD_TABLE_PROFESSOR_EMAIL +
                ") VALUES ('Administrador','admin', 'admin', 'admin@admin.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Database.QUERY_DROP_TABLE_ALUNO);
        db.execSQL(Database.QUERY_DROP_TABLE_PROFESSOR);
        db.execSQL(Database.QUERY_DROP_TABLE_MATERIA);
        db.execSQL(Database.QUERY_DROP_TABLE_NOTA);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL(Database.QUERY_ENABLE_FOREIGN_KEYS);
        }
    }
}
