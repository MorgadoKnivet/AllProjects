package com.example.moorg.alertmaps.Menu;

import android.app.Activity;
import android.content.Intent;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moorg.alertmaps.ListPersonalizada.Curso;
import com.example.moorg.alertmaps.PontoCerto;
import com.example.moorg.alertmaps.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by MoorG on 19/06/2017.
 */

public class EscolherMusica extends FragmentActivity{
     public static final int IMAGEM_INTERNA = 5;
     TextView textView;
     Button bn;
    MediaPlayer player;
    private MediaPlayer mp;
    private Button btnSom;
    int musicA = R.raw.musicedumon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escolher_musica);
        textView = (TextView) findViewById(R.id.escreverMusica);

    }

    public void pegarMusica(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, IMAGEM_INTERNA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == IMAGEM_INTERNA){
            if (resultCode == RESULT_OK){
                Uri nomeMusic = intent.getData();

                // Seleciona o titulo da musica e marca qual foi a escolida
              String[] colunas = {MediaStore.Audio.Media.RECORD_SOUND_ACTION};
                  Cursor cursor = getContentResolver().query(nomeMusic,colunas,null,null,null);
              cursor.moveToFirst();
                int indexColuna = cursor.getColumnIndex(colunas[0]);
             /*   String aux = cursor.getString(indexColuna);
                textView.setText(aux);


                Uri music = intent.getData();
                String[] colunaAudio = {MediaStore.Audio.Media.IS_MUSIC};
                Cursor cursor1 = getContentResolver().query(music,colunaAudio,null,null,null);
                cursor1.moveToFirst();
                int indexColunaAudio = cursor1.getColumnIndex(colunaAudio[0]);
                musicA = cursor.getInt(indexColunaAudio);

*/
                if (player == null){
                    File sdCard = Environment.getExternalStorageDirectory();
                    File file = new File(nomeMusic.toString());
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(file.getAbsolutePath().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.prepareAsync();
                }else{
                    player.start();
                }


                // Preciso aprender uma função que transforme o dado/ o caminho, não sei ao certo, em um arquivo de música para pode-lo executar

                 /*// RAW


                 							player = MediaPlayer.create(MainActivity.this, R.raw.music_01);
                 							player.start();*/

                /*// SDCARD
               						  	    File sdCard = Environment.getExternalStorageDirectory();
               						     	File file = new File(sdCard, "Music/Music_01.mp3");
                 							player = new MediaPlayer();
                 							player.setDataSource(file.getAbsolutePath().toString());
                 							player.prepareAsync();*/






                /*
                MediaStore.Audio audio = MediaStore.Audio.Media;
                // ---------------------

                   String[] colunaAudio = {MediaStore.Audio.Media.IS_MUSIC};
                   Cursor cursor1 = getContentResolver().query(music,colunaAudio,null,null,null);
                 cursor1.moveToFirst();
                   int indexColunaAudio = cursor1.getColumnIndex(colunaAudio[0]);
                   musicA = cursor.getInt(indexColunaAudio);

*/






                // ----------------------------

             /*   mp = new MediaPlayer();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                btnSom = (Button) findViewById(R.id.habMusica);

                try {
                    mp.reset();
                    AssetFileDescriptor afd = null;
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.reset();

                    afd = getResources().openRawResourceFd(musicA);



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

*/

                // ------------------------------





               // cursor.close();
              //  cursor1.close();



            }
        }
    }

    public void passaValor(int music){
        Intent intent = new Intent(EscolherMusica.this, PontoCerto.class);

        Bundle bundle = new Bundle();
        bundle.putInt("music", music);
        intent.putExtras(bundle);
        startActivity(intent);

    }
/*
                final MediaPlayer mediaPlayer = new MediaPlayer();
                String mediaPath = aux;
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Uri uri = Uri.parse(mediaPath);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(),uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(),"PlayBack Started",Toast.LENGTH_LONG).show();
                    bn.setEnabled(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        bn.setEnabled(true);
                        mediaPlayer.release();

                        Toast.makeText(getApplicationContext(),"PlayBack ACABOU",Toast.LENGTH_LONG).show();

                    }
                });
*/
}
