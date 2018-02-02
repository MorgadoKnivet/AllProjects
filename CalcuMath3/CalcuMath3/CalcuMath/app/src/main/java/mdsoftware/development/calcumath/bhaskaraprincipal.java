package mdsoftware.development.calcumath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class bhaskaraprincipal extends AppCompatActivity {

    ImageView textResumo, textPasso;
    ImageButton resumo;
    Button passo, calc;
    EditText editCalc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_bhaskara);

        resumo = (ImageButton)findViewById(R.id.butResumo);
         passo = (Button) findViewById(R.id.ativarPasso);
          calc = (Button)findViewById(R.id.ativarCalc);
         textPasso = (ImageView)findViewById(R.id.textPassoBhas);
     //   final ImageView textPasso = (ImageView)findViewById(R.id.textPassoBhas);
         textResumo = (ImageView)findViewById(R.id.textResumoBhas);

        passo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               textResumo.setVisibility(View.GONE);
                textPasso.setVisibility(View.VISIBLE);
       //        textPasso.setVisibility(View.VISIBLE);
                Log.i("SCRIPT", "ENTROU NO CLick");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textResumo.setVisibility(View.GONE);
                textPasso.setVisibility(View.GONE);
                editCalc.setVisibility(View.VISIBLE);

           //     editCalc.setHint("Insira o número mí");

                // Minimo, maximo, numero de divisores.

            }
        });

        resumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPasso.setVisibility(View.GONE);
                textResumo.setVisibility(View.VISIBLE);
            }
        });


    }


/*
    public void mudarTexto(View view){
        textResumo.setVisibility(View.GONE);
    }

    */

    public void voltar(View view) {
        Intent intent = new Intent(this, matematica.class);
        startActivity(intent);
        finish();
    }

}
