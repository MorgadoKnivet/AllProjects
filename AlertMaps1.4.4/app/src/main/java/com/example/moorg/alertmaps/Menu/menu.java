package com.example.moorg.alertmaps.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.moorg.alertmaps.ListaTarefas;
import com.example.moorg.alertmaps.MapsActivity;
import com.example.moorg.alertmaps.R;

/**
 * Created by MoorG on 23/05/2017.
 */

public class menu extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

    }


    public void irParaMapa(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),MapsActivity.class);

        startActivity(intentAux1);
    }

    public void irParaTarefas(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),ListaTarefas.class);
        startActivity(intentAux1);
    }

    public void irParaCréditos(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),Creditos.class);
        startActivity(intentAux1);
    }

    public void irParaTutorial(View v){
        Intent intentAux2 = new Intent(getApplicationContext(),Tutorial.class);
        startActivity(intentAux2);
    }



    public void irParaMusica(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),EscolherMusica.class);
        startActivity(intentAux1);
    }


}
