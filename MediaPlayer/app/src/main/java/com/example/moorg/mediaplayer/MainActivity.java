// https://github.com/LeonardoTeixeira/tutorial-android-mediaplayer/blob/master/app/src/main/java/br/com/leonardoteixeira/tutorialmediaplayer/MainActivity.java
package com.example.moorg.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mp;
    private Button btnSom;
  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        btnSom = (Button) findViewById(R.id.butSom);
        btnSom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();
            AssetFileDescriptor afd = null;
            switch (v.getId()) {
                case R.id.butSom:
                    afd = getResources().openRawResourceFd(R.raw.click);
                    break;
            }
            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepareAsync();
            }
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
    }*/
}
// https://blog.leonardoteixeira.com.br/android/2015/09/18/tutorial-android-mediaplayer.html
// site consulta quando precisar algo mais profundo
/*
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private MediaPlayer mp;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                mp = new MediaPlayer();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                AssetFileDescriptor afd = null;
                switch (V.getId()){
                    case R.id.button:
                        afd = getResources().openRawResourceFd(R.raw.click);
                        break;
                }

            }
        });





    }





} */
