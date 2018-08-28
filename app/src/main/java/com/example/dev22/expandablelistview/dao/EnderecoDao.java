package com.example.dev22.expandablelistview.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dev22.expandablelistview.databases.Database;
import com.example.dev22.expandablelistview.databases.DatabaseHelper;
import com.example.dev22.expandablelistview.model.Endereco;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDao {

    private SQLiteDatabase database;

    public EnderecoDao() {
        database = DatabaseHelper.getInstance().getWritableDatabase();
    }

    public boolean save(Endereco endereco) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.FIELD_TABLE_ENDERECO_LOCALIZACAO, endereco.getEndereco());

        if (endereco.getNumero() != null) {
            contentValues.put(Database.FIELD_TABLE_ENDERECO_NUMERO, endereco.getNumero());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ENDERECO_NUMERO);
        }

        if (endereco.getComplemento() != null) {
            contentValues.put(Database.FIELD_TABLE_ENDERECO_COMPLEMENTO, endereco.getComplemento());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ENDERECO_COMPLEMENTO);
        }


        if (endereco.getUserId() != null) {
            contentValues.put(Database.FIELD_TABLE_ENDERECO_USERID, endereco.getUserId());
        } else {
            contentValues.putNull(Database.FIELD_TABLE_ENDERECO_USERID);
        }

        if (endereco.getId() == null) {
            contentValues.putNull(Database.FIELD_TABLE_ENDERECO_ID);
            return database.insert(Database.TABLE_ENDERECO, null, contentValues) > 0;
        } else {
            contentValues.put(Database.FIELD_TABLE_ENDERECO_ID, endereco.getId());
            return database.update(Database.TABLE_ENDERECO, contentValues,
                    Database.FIELD_TABLE_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())}) > 0;

        }

    }

    public boolean delete(Endereco endereco) {
        if (endereco.getId() != null) {
            return database.delete(Database.TABLE_ENDERECO, Database.FIELD_TABLE_ENDERECO_ID +
                    " = ? ", new String[]{String.valueOf(endereco.getId())}) > 0;
        }
        return false;
    }


    public List<Endereco> buscarEnderecos(Usuario usuario) {
        Cursor c = null;
        try {
            c = database.rawQuery("SELECT * FROM " + Database.TABLE_ENDERECO + " WHERE " +
                    Database.FIELD_TABLE_ENDERECO_USERID + " = ?", new String[]{String.valueOf(usuario.getId())});
            List<Endereco> enderecos = new ArrayList<>();
            while (c.moveToNext()) {
                Endereco endereco = new Endereco();
                endereco.setId(c.getInt(c.getColumnIndex(Database.FIELD_TABLE_ENDERECO_ID)));
                endereco.setUserId(c.getInt(c.getColumnIndex(Database.FIELD_TABLE_ENDERECO_USERID)));
                endereco.setEndereco(c.getString(c.getColumnIndex(Database.FIELD_TABLE_ENDERECO_LOCALIZACAO)));
                endereco.setNumero(c.getInt(c.getColumnIndex(Database.FIELD_TABLE_ENDERECO_NUMERO)));
                endereco.setComplemento(c.getString(c.getColumnIndex(Database.FIELD_TABLE_ENDERECO_COMPLEMENTO)));
                enderecos.add(endereco);
            }
            return enderecos;
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
    }
}
