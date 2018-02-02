package com.example.moorg.alertmaps.FuncionamentoBackground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.moorg.alertmaps.R;

public class MainActivity extends Activity {
    public TextView lv;
    //

    @Override
    // AQUI ESTÁ DANDO O ERRO, POIS ELE STARTA O SERVICE AQUI, PRECISAR ACHAR UMA OUTRA MANEIRA DE STARTAR O SERVICE
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        lv = (TextView) findViewById(R.id.textView);
    }

    public void startService(View view){
        Intent it = new Intent(this,ServicoTest.class);
       // it.putExtra("aas", "sdvsd");
        startService(it);
    }

    public void stopService(View view){
        Intent it = new Intent(this,ServicoTest.class);
        stopService(it);
    }
}
