package com.example.moorg.bricandocomlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaTeste = (ListView) findViewById(R.id.listViewTeste);

        ArrayList list = new ArrayList();



        Curso curso = new Curso();
        list.add("AAAAAAAAAAAAAAAAAAAA");
        list.add("EU VO DE COMBO");


        arrayAdapter adapter =
                new arrayAdapter(list, this);

        listaTeste.setAdapter(adapter);




    }



}

