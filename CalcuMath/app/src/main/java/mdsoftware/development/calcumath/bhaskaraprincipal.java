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
    ImageButton resumo, calcular;
    Button passo, calc;
    EditText editCalc, auxEquação;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_bhaskara);

        resumo = (ImageButton)findViewById(R.id.butResumo);
         passo = (Button) findViewById(R.id.ativarPasso);
          calc = (Button)findViewById(R.id.ativarCalc);
        calcular = (ImageButton)findViewById(R.id.buscarEQ);
         textPasso = (ImageView)findViewById(R.id.textPassoBhas);
        auxEquação = (EditText)findViewById(R.id.equaçao);
     //   final ImageView textPasso = (ImageView)findViewById(R.id.textPassoBhas);
         textResumo = (ImageView)findViewById(R.id.textResumoBhas);



        passo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               textResumo.setVisibility(View.GONE);
                textPasso.setVisibility(View.VISIBLE);
                calcular.setVisibility(View.GONE);
                auxEquação.setVisibility(View.GONE);

       //        textPasso.setVisibility(View.VISIBLE);
                Log.i("SCRIPT", "ENTROU NO CLick");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textResumo.setVisibility(View.GONE);
                textPasso.setVisibility(View.GONE);
                calcular.setVisibility(View.VISIBLE);
                auxEquação.setVisibility(View.VISIBLE);


            }
        });

        resumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPasso.setVisibility(View.GONE);
                textResumo.setVisibility(View.VISIBLE);
                calcular.setVisibility(View.GONE);
                auxEquação.setVisibility(View.GONE);

            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxEquação = (EditText)findViewById(R.id.equaçao);
                String eq = auxEquação.getText().toString();
            // "   2x² + 5x + 3 = 0 "


                if ((eq.contains("+")) && !(eq.contains("-")) ){
                    // "   2x² + 5x + 3 = 0 "
                   eq= eq.replace(" ","").replace("²","").replace("x","").replace("=0","").replace("+",".").trim();
                    //2.5.3
                    String aux[] = eq.split(".");

                    int a, b, c = 0;

                    a = Integer.parseInt(aux[0]);
                    b = Integer.parseInt(aux[1]);
                    c = Integer.parseInt(aux[2]);

                    /*Math.pow(x, y);

                    Legenda:
                    x = base
                    y = potência

                    */
                    double result = ( Math.sqrt(Math.pow(b,2) - 4*a*c));
                    Double x1 = (b + result)/2*a;
                    Double x2 = (b - result)/2*a;
                    /*

                    a = aux[0]
                    b = aux[1]
                    c = aux[2]

                     */
                    Log.i("Script_Resultado",""+x1+ " " +x2);

                }


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
