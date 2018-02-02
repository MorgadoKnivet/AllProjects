package com.example.moorg.broadcastreceiveraprendendo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by MoorG on 18/07/2017.
 */

public class BroadCastReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // tem que responder em 10 segundos
        Log.i("Script","Broadcast1");
    }
}
