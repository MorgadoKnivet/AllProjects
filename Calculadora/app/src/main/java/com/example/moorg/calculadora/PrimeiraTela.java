package com.example.moorg.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by MoorG on 11/03/2017.
 */

public class PrimeiraTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primeira_tela);

    }

    public void irParaCalc(View v){ // para usarmos a função o click para ir para próxima tela temos que declarar o onlick no XML e colocar o mesmo nome no arquivo java
        Intent aux = new Intent(getApplicationContext(),Calculadora.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(aux);
    }
    public void irParaCred(View v){ // para usarmos a função o click para ir para próxima tela temos que declarar o onlick no XML e colocar o mesmo nome no arquivo java
        Intent aux2 = new Intent(getApplicationContext(),Credito.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(aux2);
    }

}
