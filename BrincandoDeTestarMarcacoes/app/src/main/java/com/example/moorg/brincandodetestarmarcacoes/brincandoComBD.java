package com.example.moorg.brincandodetestarmarcacoes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MoorG on 04/05/2017.
 */

public class brincandoComBD extends AppCompatActivity {
   /* SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tarefas);
        db = openOrCreateDatabase("TesteDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Contatos (Nome VARCHAR);");
        // Cria uma tabela, se não existes. Nome Contato e com as colunas nome, contato e tipo

        // Inserindo dados

        db.execSQL("INSERT INTO contatos VALUES('"+valor1 +"','"+valor2+"','"+valor3"');");

        // Selecionando dados

        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+valor1 = "'",null);

        //deletar dados (lembrando que para deletar tem que selecionar antes

        if(c.moveToFirst()){
            db.execSQL("DELETE FROM contatos WHERE Nome ='"+valor1+"'");
        }

        //Mensagem de aviso na tela
        showMessage("Erro", "Invalido");

        // Editar dados
        // Selecionando dados

        Cursor c = db.rawQuery("SELECT * FROM contatos WHERE Nome='"+valor1 = "'",null);

        if(c.moveToFirst()){
            db.execSQL("UPDATE nome_tabela SET  Nome ='"+valor1+"', Contato='"+ valor2+ "'WHERE Nome'"+valor+"'");
        }
        */

}