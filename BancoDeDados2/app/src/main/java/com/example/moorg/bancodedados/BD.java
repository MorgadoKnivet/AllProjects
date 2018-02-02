package com.example.moorg.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoorG on 02/05/2017.
 */

public class BD {
    private SQLiteDatabase bd;

    public BD(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("Nome", usuario.getNome());
        valores.put("Email", usuario.getEmail());
        valores.put("Senha", usuario.getSenha());

        bd.insert("usuario", null, valores);

    }

    public void atualizar(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("Email", usuario.getEmail());
        valores.put("Senha", usuario.getSenha());

        bd.update("Usuario", valores,"_id = ?", new String[]{""+usuario.getId()});
    }

    public void deletar(Usuario usuario){
        bd.delete("Usuario", "_Id = " +usuario.getId(), null);
    }

    public List<Usuario> buscar(){
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome", "email"};

        Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "Nome ASC");

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setNome(cursor.getString(1));
                u.setNome(cursor.getString(2));
                list.add(u);
            }while(cursor.moveToNext());
        }
        // paramos em 19 minitos do video https://www.youtube.com/watch?v=QQ-Ir8pwxWA
      return (list);
    }

}
