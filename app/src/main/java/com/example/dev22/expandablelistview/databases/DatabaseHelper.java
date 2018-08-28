package com.example.dev22.expandablelistview.databases;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dev22.expandablelistview.App;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance() {
        if (instance == null){
            instance = new DatabaseHelper();
        }
        return instance;
    }

    private DatabaseHelper() {
        super(App.getInstance(), Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database.QUERY_CREATE_TABLE_USERS);
        db.execSQL(Database.QUERY_CREATE_TABLE_ENDERECO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Database.QUERY_DROP_TABLE_USERS);
        db.execSQL(Database.QUERY_DROP_TABLE_ENDERECO);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }
}
