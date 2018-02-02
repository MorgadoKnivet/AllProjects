package com.example.moorg.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MoorG on 19/07/2017.
 */

public class BroadcastTeste extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
     intent = new Intent("SERVICO_TEST");
        context.startService(intent);


    }
}
