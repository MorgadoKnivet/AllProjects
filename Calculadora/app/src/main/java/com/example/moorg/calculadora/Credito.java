package com.example.moorg.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.lang.Object;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MoorG on 11/03/2017.
 */

public class Credito extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);
    }
/*
    Button butFB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);

        butFB = (Button) findViewById(R.id.butFB);

        butFB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                URL url = new URL("http://www.android.com/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                }finally

                {
                    urlConnection.disconnect();
                }

            }
        }); */


 //   }
    public void irParaPrimeiraTela(View v) { // para usarmos a função o click para ir para próxima tela temos que declarar o onlick no XML e colocar o mesmo nome no arquivo java
        Intent aux = new Intent(getApplicationContext(), PrimeiraTela.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(aux);
    }


}
