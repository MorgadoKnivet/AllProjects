package com.example.moorg.alertmaps;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.moorg.alertmaps.BD.DataBase;
import com.example.moorg.alertmaps.ListPersonalizada.ListaPersonalizada;

import java.util.ArrayList;

/**
 * Created by MoorG on 27/04/2017.
 */

public class ListaTarefas extends AppCompatActivity {

    EditText butEtProcesso;
    EditText aux;
    Button butDelete;

    ArrayList auxList;
    String markDado;


    private ListView lstNome;

    private SQLiteDatabase conn;
    private RepositórioTabela repositórioTabela;
    private ArrayAdapter<String> adpDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tarefas);
        butDelete = (Button) findViewById(R.id.Delete);
        lstNome = (ListView) findViewById(R.id.listViewTeste);

        try {
            DataBase dataBase = new DataBase(this);

            conn = dataBase.getWritableDatabase();

            repositórioTabela = new RepositórioTabela(conn);
            for (int i = 0; i <= 100; i++) {
           //     repositórioTabela.excluir(i);
            }

              ListaPersonalizada listaPersonalizada = new ListaPersonalizada(repositórioTabela.buscaTabela(this),this);



            //      adpDados = repositórioTabela.buscaTabela(this);

            lstNome.setAdapter(listaPersonalizada);
        } catch (SQLException EX) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro" + EX.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();

        }

        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (long i = 0; i <= 100; i++) {

                       repositórioTabela.excluir(i);
                }
                Intent intentAux1 = new Intent(getApplicationContext(),ListaTarefas.class);

                startActivity(intentAux1);
            }
        });

    }
        /*
  //




    }

*/

    public void irParaMaps(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),MapsActivity.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(intentAux1);
    }
}


