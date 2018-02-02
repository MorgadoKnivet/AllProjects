package com.example.moorg.alertmaps.BuscaEndereço;

import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by MoorG on 15/06/2017.
 */

public class appBackground extends android.app.Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
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

       // Worker w = new Worker(startId);
       // w.start();
       // threads.add(w);

        return(super.onStartCommand(intent, flags, startId));
        // START_NOT_STICKY
        // START_STICKY
        // START_REDELIVER_INTENT
    }
}
