package com.example.moorg.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoorG on 18/07/2017.
 */

public class ServicoTest extends Service  {

    public List<worker> threads = new ArrayList<worker>();




    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Script", "onCreate()");


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("Script", "onStartCommand()");

        worker w = new worker(startId);
        w.start();
        threads.add(w);

        return(super.onStartCommand(intent, flags, startId));
        // START_NOT_STICKY
        // START_STICKY
        // START_REDELIVER_INTENT
    }


    class worker extends Thread{
        public int count = 0;
        public int startId;
        public boolean ativo = true;

        public worker(int startId){
            this.startId = startId;
        }

        public void run(){
            while(ativo && count < 1000){
                try {
                    Thread.sleep(1000); // faz esperar 1 segundo = 1000 miliSegundos para poder continuar
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                count++;
                Log.i("Script", "COUNT: "+count);

             //   MainActivity mainActivity = new MainActivity();
             //   mainActivity.lv.setText(""+count);

            }
            stopSelf(startId);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        for(int i = 0, tam = threads.size(); i < tam; i++){
            threads.get(i).ativo = false;
        }
    }
}
