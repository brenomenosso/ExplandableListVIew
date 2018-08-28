package com.example.dev22.expandablelistview.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.dev22.expandablelistview.databases.Database;
import com.example.dev22.expandablelistview.databases.DatabaseHelper;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private SQLiteDatabase database;

    public UsuarioDao() {
        database = DatabaseHelper.getInstance().getWritableDatabase();
    }

    public boolean save(Usuario usuario) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.FIELD_TABLE_USERS_NOME, usuario.getNome());

        if (usuario.getSobrenome() != null) {
            contentValues.put(Database.FIELD_TABLE_USERS_SOBRENOME, usuario.getSobrenome());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_USERS_SOBRENOME);
        }

        if (usuario.getIdade() != null) {
            contentValues.put(Database.FIELD_TABLE_USERS_IDADE, usuario.getIdade());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_USERS_IDADE);
        }

        if (usuario.getId() == null) {
            contentValues.putNull(Database.FIELD_TABLE_USERS_ID);
            int id = (int) database.insert(Database.TABLE_USERS, null, contentValues);
            if (id > 0){
                usuario.setId(id);
                return true;
            }
        } else {
            contentValues.put(Database.FIELD_TABLE_USERS_ID, usuario.getId());
            return database.update(Database.TABLE_USERS, contentValues,
                    Database.FIELD_TABLE_USERS_ID + " = ?", new String[]{String.valueOf(usuario.getId())}) > 0;

        }
        return false;
    }

    public boolean delete(Usuario usuario) {
        if (usuario.getId() != null) {
            return database.delete(Database.TABLE_USERS, Database.FIELD_TABLE_USERS_ID +
                    " = ?", new String[]{String.valueOf(usuario.getId())}) > 0;
        }
        return false;
    }

    public List<Usuario> buscarUsuarios() {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + Database.TABLE_USERS, null);
            List<Usuario> usuarios = new ArrayList<>();
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndex(Database.FIELD_TABLE_USERS_ID)));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_USERS_NOME)));
                usuario.setSobrenome(cursor.getString(cursor.getColumnIndex(Database.FIELD_TABLE_USERS_SOBRENOME)));
                usuario.setIdade(cursor.getInt(cursor.getColumnIndex(Database.FIELD_TABLE_USERS_IDADE)));
                usuarios.add(usuario);
            }
            return usuarios;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
