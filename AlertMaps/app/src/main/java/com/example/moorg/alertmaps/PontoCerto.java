package com.example.moorg.alertmaps;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by MoorG on 21/04/2017.
 */

public class PontoCerto extends AppCompatActivity {
    private MediaPlayer mp;
    private Button btnSom;
            int music = R.raw.musicedumon;
    TextView  lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerta_ponto_correto);
        lv = (TextView) findViewById(R.id.textViewEndereco);
        lv.setText(recebeEndereço());
      // receberMusic();
        recebeEndereço();
        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        btnSom = (Button) findViewById(R.id.butStop);

        try {
            mp.reset();
            AssetFileDescriptor afd = null;
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();

                        afd = getResources().openRawResourceFd(music);



            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepareAsync();
            }
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }

        btnSom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                mp.stop();
            }
        });


    }


    public void irParaMaps(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),MapsActivity.class);
        // aux.setClass(this,Calculadora.class);
        mp.stop();
        startActivity(intentAux1);
    }


    public void receberMusic(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int setMusic = bundle.getInt("music");
           this.music = setMusic;


    }

    public String recebeEndereço(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String setEnd = bundle.getString("endereco");

        return setEnd;

        // lv = (TextView) findViewById(R.id.listViewEndereco);
        //lv.setText("PORRA TA TROLANDO");

    }

}
