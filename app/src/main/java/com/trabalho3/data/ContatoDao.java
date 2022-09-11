package com.trabalho3.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDao {

    private static ContatoDao instance;

    private SQLiteDatabase db;

    private ContatoDao(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    //singleton
    public static ContatoDao getInstance(Context context) {
        if (instance == null) {
            instance = new ContatoDao(context.getApplicationContext());
        }
        return instance;
    }

    public List<Contato> list() {

        String[] columns = {
                ContatosContract.Columns._ID,
                ContatosContract.Columns.NOME,
                ContatosContract.Columns.TELEFONE
        };

        List<Contato> contatos = new ArrayList<>();

        try (Cursor c = db.query( ContatosContract.TABLE_NAME, columns, null, null, null, null,  ContatosContract.Columns.NOME)) {
            if (c.moveToFirst()) {
                do {
                    Contato contato =  ContatoDao.fromCursor(c);
                    contatos.add(contato);
                } while (c.moveToNext());
            }

            return contatos;
        }

    }

    private static Contato fromCursor(Cursor c) {
        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex( ContatosContract.Columns._ID));
        @SuppressLint("Range") String nome = c.getString(c.getColumnIndex(ContatosContract.Columns.NOME));
        @SuppressLint("Range") String telefone = c.getString(c.getColumnIndex(ContatosContract.Columns.TELEFONE));
        return new Contato(id, nome, telefone);
    }

    public void save(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ContatosContract.Columns.NOME, contato.getNome());
        values.put(ContatosContract.Columns.TELEFONE, contato.getTelefone());
        long id = db.insert(ContatosContract.TABLE_NAME, null, values);
        contato.setId((int) id);
    }

    public void update(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ContatosContract.Columns.NOME, contato.getNome());
        values.put(ContatosContract.Columns.TELEFONE, contato.getTelefone());
        db.update(ContatosContract.TABLE_NAME, values, ContatosContract.Columns._ID + " = ?", new String[]{ String.valueOf(contato.getId()) });
    }

    public void delete(Contato contato) {
        db.delete(ContatosContract.TABLE_NAME, ContatosContract.Columns._ID + " = ?", new String[]{ String.valueOf(contato.getId()) });
    }
}
