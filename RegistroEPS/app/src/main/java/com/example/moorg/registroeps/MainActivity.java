package com.example.moorg.registroeps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase conn;

    SQLiteDatabase db;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






    }



    public void vitoria(View view){

        nome = (EditText)findViewById(R.id.nomeAd);
        String nomeL = nome.getText().toString();

        if (TextUtils.isEmpty(nomeL)) {
            Toast.makeText(this, "Erro, nome Vazio", Toast.LENGTH_LONG).show();
            return;
        }

        DataBase dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nome",nomeL);
        values.put("cont","Vitoria");

        conn.insertOrThrow("Dados",null, values);
        nome.setText("");


    }

    public void derrota(View view){
        nome = (EditText)findViewById(R.id.nomeAd);
        String nomeL = nome.getText().toString();

        if (TextUtils.isEmpty(nomeL)) {
            Toast.makeText(this, "Erro, nome Vazio", Toast.LENGTH_LONG).show();
            return;
        }

        DataBase dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nome",nomeL);
        values.put("cont","Derrota");

        conn.insertOrThrow("Dados",null, values);
        nome.setText("");

    }

    public void empate(View view){

        nome = (EditText)findViewById(R.id.nomeAd);
        String nomeL = nome.getText().toString();

        if (TextUtils.isEmpty(nomeL)) {
            Toast.makeText(this, "Erro, nome Vazio", Toast.LENGTH_LONG).show();
            return;
        }

        DataBase dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nome",nomeL);
        values.put("cont","Empate");

        conn.insertOrThrow("Dados",null, values);
        nome.setText("");
    }

    public ArrayAdapter resultados(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_main,R.id.lv);
        DataBase dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        Cursor cursor = conn.query("Dados", null, null, null, null, null, null, null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String aux = cursor.getString(2);
             //   String cont = cursor.getString(3);

                arrayAdapter.add(aux+": ");



            }while (cursor.moveToNext());
        }

        return arrayAdapter;


    }

    public void resultado(View view){

        ListView listView = (ListView)findViewById(R.id.lv);

        listView.setAdapter(resultados());

    }
}
