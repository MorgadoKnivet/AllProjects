package com.example.moorg.brincandodetestarmarcacoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by MoorG on 07/05/2017.
 */

public class RepositórioTabela {
    private SQLiteDatabase conn;

    ListaTarefas listaTarefas = new ListaTarefas();


    Double auxLat;
    Double auxLng;





  //  ArrayList auxLat = new ArrayList();
  //  ArrayList auxLng = new ArrayList();
    public RepositórioTabela(SQLiteDatabase conn){
        this.conn = conn;

    }
    public void excluir(long id){
        conn.delete(Contato.TABELA, " _id = ? ", new String[]{ String.valueOf( id ) });

    }

    public void testeInserirContatos(String markDado){


            ContentValues values = new ContentValues();
             // permine enviar dois valores,tipo um mapa, no primeiro: Nome do campo na tabela no segundo: o valor inserido na tabela

     //     values.put("Lng", lng);
      //      values.put("Lat", lat);
        values.put("Nome", markDado);

            conn.insertOrThrow("Dados", null, values);

    }


    public ArrayAdapter<String>buscaTabela(Context context){
        ArrayAdapter<String> lista = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("Dados", null,null,null,null,null,null,null);
        if (cursor.getCount() > 0){

            cursor.moveToFirst();
           do{
                String nome = cursor.getString(1);
                lista.add(nome);
                //    auxLat.add(cursor.getInt(2));
                //   auxLng.add(cursor.getInt(3));


            }while (cursor.moveToNext());
        }

         // listaTarefas.setArray(lista);

        return lista ; //assistido até 32.00
    }


    public void InserirLat(Double aux){
        ContentValues values = new ContentValues();
        values.put("Lat_d",-22);

        conn.insertOrThrow("Dados", null, values);

    }

    public void InserirLng(Double aux){
        ContentValues values = new ContentValues();

        values.put("Lng_d",-43);
        conn.insertOrThrow("Dados", null, values);

    }









    public  ArrayList buscaTabelaLat(Context context) {
        ArrayList listLat = new ArrayList();
        Cursor cursor = conn.query("Dados", null, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {
                Double auxLat2 = cursor.getDouble(2);
                    listLat.add(auxLat2);
            } while (cursor.moveToNext());
        }
        return listLat;
    }

    public ArrayList buscaTabelaLng(Context context) {
        ArrayList listLng = new ArrayList();
        Cursor cursor = conn.query("Dados", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {
                Double auxLng2 = cursor.getDouble(3);
                listLng.add(auxLng2);
            } while (cursor.moveToNext());
        }
        return listLng;
    }


        public LatLng getLatLng(){
            LatLng latLng = new LatLng(auxLat,auxLng);
            return latLng;
        }


}
