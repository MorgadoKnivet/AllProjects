package com.example.moorg.broadcastreceiveraprendendo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Objetivo executar funções em background sem que o usuario veja, utilizado para executar coisas rápidas
        // android.intent.action.BOOT_COMPLETED vai iniciar a app quando o celular iniciar, utiliza-se a permissão android.permission.RECEIIVE_BOOT_COMPLETED
    }

    public void chamarBR(View view){
        Button bt = (Button)view;

        Intent intent = new Intent(bt.getText().toString());

        sendBroadcast(intent);

    }
}
