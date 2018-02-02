package com.example.moorg.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Calculadora extends AppCompatActivity {
    Button butZero, butUm, butDois, butTres, butQuatro, butCinco, butSeis, butSet, butOito, butNove, butSoma, butSub, butDiv, butX, butRes, butPt, butLimp;
    EditText butEtProcesso, etContatenar;
    double n1, n2, result;
    String operador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);


        butZero = (Button)findViewById(R.id.button);
        butUm = (Button)findViewById(R.id.button1);
        butDois = (Button)findViewById(R.id.button2);
       butTres = (Button)findViewById(R.id.button3);
        butQuatro = (Button)findViewById(R.id.button4);
        butCinco = (Button)findViewById(R.id.button5);
        butSeis = (Button)findViewById(R.id.button6);
        butSet = (Button)findViewById(R.id.button7);
        butOito = (Button)findViewById(R.id.button8);
        butNove = (Button)findViewById(R.id.button9);
        butSoma = (Button)findViewById(R.id.buttonSoma);
        butDiv = (Button)findViewById(R.id.buttonDiv);
        butSub = (Button)findViewById(R.id.buttonSub);
        butX = (Button)findViewById(R.id.buttonMult);
        butRes = (Button)findViewById(R.id.buttonResult);
        butLimp = (Button)findViewById(R.id.buttonLimpa);
        butPt = (Button)findViewById(R.id.button0);
        butEtProcesso = (EditText)findViewById(R.id.etProceso);


        butZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "0"
                );
            }
        });

        butUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "1"
                );
            }
        });

        butDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "2"
                );
            }
        });


        butTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "3"
                );
            }
        });


        butQuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "4"
                );
            }
        });


        butCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "5"
                );
            }
        });


        butSeis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "6"
                );
            }
        });


        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "7"
                );
            }
        });

        butOito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "8"
                );
            }
        });

        butNove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "9"
                );
            }
        });


        butPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContatenar = (EditText)findViewById(R.id.etProceso);
                butEtProcesso.setText(etContatenar.getText().toString() + "."
                );
            }
        });

        butSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operador = "+";
                etContatenar = (EditText)findViewById(R.id.etProceso);
                String aux;
                aux = etContatenar.getText().toString();
                if ((aux == null) || (aux.equals(""))){
                    butEtProcesso.setText("");
                }else {
                    n1 = Double.parseDouble(etContatenar.getText().toString());
                    butEtProcesso.setText("");
                }
            }
        });

        butSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "-";
                String aux;
                aux = etContatenar.getText().toString();
                if ((aux == null) || (aux.equals(""))){
                    butEtProcesso.setText("");
                }else {
                    n1 = Double.parseDouble(etContatenar.getText().toString());
                    butEtProcesso.setText("");
                }
            }
        });

        butX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "*";
                String aux;
                aux = etContatenar.getText().toString();
                if ((aux == null) || (aux.equals(""))){
                    butEtProcesso.setText("");
                }else {
                    n1 = Double.parseDouble(etContatenar.getText().toString());
                    butEtProcesso.setText("");
                }

            }
        });

        butDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "/";
                String aux;
                aux = etContatenar.getText().toString();
                if ((aux == null) || (aux.equals("")   )){
                    butEtProcesso.setText("");
                }else {
                    n1 = Double.parseDouble(etContatenar.getText().toString());
                    butEtProcesso.setText("");
                }

            }
        });

        butLimp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                n1 = 0;
                n2 = 0;
                butEtProcesso.setText("");
            }
        });

        butRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux;
                aux = etContatenar.getText().toString();
                if ((aux == null) || (aux.equals(""))){
                    butEtProcesso.setText("");
                }else {
                    etContatenar = (EditText) findViewById(R.id.etProceso);
                    n2 = Double.parseDouble(etContatenar.getText().toString());
                    if (operador.equals("+")) {
                        butEtProcesso.setText("");
                        result = n1 + n2;
                    }
                    if (operador.equals("-")) {
                        butEtProcesso.setText("");
                        result = n1 - n2;
                    }
                    if (operador.equals("*")) {
                        butEtProcesso.setText("");
                        result = n1 * n2;
                    }
                    if (operador.equals("/")) {
                        butEtProcesso.setText("");

                            result = n1/n2;


                    }
                    butEtProcesso.setText(String.valueOf(result));

                }

            }
        });







    }
}

