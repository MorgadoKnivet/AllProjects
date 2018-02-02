package com.example.moorg.service;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    public TextView lv;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
