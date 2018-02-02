package com.example.moorg.alertmaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.moorg.alertmaps.ListPersonalizada.Curso;
import com.example.moorg.alertmaps.ListPersonalizada.ListaPersonalizada;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoorG on 07/05/2017.
 */

public class RepositórioTabela {

    private SQLiteDatabase conn;


  //  ListaTarefas listaTarefas = new ListaTarefas();
  //  classEspecial classEspecial = new classEspecial();

    String lat, lng;
    ArrayList auxLat = new ArrayList();
    ArrayList auxLng = new ArrayList();
    public RepositórioTabela(SQLiteDatabase conn){
        this.conn = conn;

    }
    public void excluir(long id){
        conn.delete(Contato.TABELA, " id = ? ", new String[]{ String.valueOf(id) });

;
    }
    public void excluir(double lat){

        conn.delete(Contato.TABELA,"lat = ? ",new String[]{ String.valueOf(lat) });



    }


    public void testeInserirContatos(String markDado, String auxLat, String auxLng){

        ContentValues values = new ContentValues();
             // permine enviar dois valores,tipo um mapa, no primeiro: Nome do campo na tabela no segundo: o valor inserido na tabela
        values.put("Nome", markDado);
        values.put("Lat", auxLat);
         values.put("Lng", auxLng);

        conn.insertOrThrow("Dados", null, values);
    }


    public List buscaTabela(Context context){
        /*vamos testar aqui abaixo a listView personalizada */

        List lista = new ArrayList();



        // ____________________________________________________
       // ArrayAdapter lista = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
      //  ListaPersonalizada listaPersonalizada = new ListaPersonalizada(lista,this);
        Cursor cursor = conn.query("Dados", null,null,null,null,null,null,null);
        if (cursor.getCount() > 0){

            cursor.moveToFirst();
           do{
                String nome = cursor.getString(1);
                lista.add(nome);
                //lista.add(cursor.getString(2));
                //lista.add(cursor.getString(3));


            }while (cursor.moveToNext());
        }

         // listaTarefas.setArray(lista);

        return lista ; //assistido até 32.00
    }



    public  ArrayList[] buscaTabelaLatLng(Context context) {
        ArrayList [] vet = new ArrayList[4];
        ArrayList listLng = new ArrayList();
        ArrayList listLat = new ArrayList();
        Cursor cursor = conn.query("Dados", null, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {
                String auxLat = cursor.getString(2);

                double auxLat2 = Double.parseDouble(auxLat);

                listLat.add(auxLat2);

                String auxLng = cursor.getString(3);

                double auxLng2 = Double.parseDouble(auxLng);

                listLng.add(auxLng2);

               

            } while (cursor.moveToNext());
        }
        vet[1] = listLat;
        vet[2] = listLng;
        return vet ;
    }




}
