package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.R;

/**
 * Created by MoorG on 19/09/2017.
 */

public class MyService extends Service implements Runnable {
    private String id;
    private String userUnidade;
    private String x = "";
    private FirebaseDatabase database;
    private DatabaseReference regMonitoria;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
        setId("" + mAuth.getCurrentUser().getUid());

        Thread thread = new Thread(this);
        thread.start();
        System.out.println("Serviço criado");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
      //  Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        System.out.println("Entrou no MY__SERVICE");

        return (super.onStartCommand(intent, flags, startId));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Serviço destruido");
        //  Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void run() {
        ArrayList unidade = new ArrayList();
        unidade.add("CEFET Maracanã médio e técnico");
        unidade.add("CEFET Maracanã universidade");
        unidade.add("" + mAuth.getCurrentUser().getUid());
        System.out.println("Entrou no RUN");

        for (int i = 0; i < unidade.size(); i++) {
            problemaUnidade("" + unidade.get(i));
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void problemaUnidade(String unidadeL) {

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);

                        if (x.equals("MONITOR")) {


                            NotificationCompat.Builder mBuilder =
                                    (NotificationCompat.Builder) new NotificationCompat.Builder(MyService.this)
                                            .setVisibility(Notification.VISIBILITY_PUBLIC)
                                            .setSmallIcon(R.drawable.ic_stat_name)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.monitoria_icon))
                                            .setContentTitle("MonitoriaCEFET")
                                            .setAutoCancel(true)
                                            .setVibrate(new long[]{150, 300, 150, 600})
                                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                                    .setContentText("Sua conta de monitor já está disponível");

                            Intent resultIntent = new Intent(MyService.this, MenuMonitor.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                            stackBuilder.addParentStack(LoginActivity.class);
                            stackBuilder.addNextIntent(resultIntent);

                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            mNotificationManager.notify(R.mipmap.monitoria_icon22, mBuilder.build());



                            stopSelf();

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }

        if (unidadeL.equals("CEFET Maracanã universidade")) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");


            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                        if (x.equals("MONITOR")) {


                            NotificationCompat.Builder mBuilder =
                                    (NotificationCompat.Builder) new NotificationCompat.Builder(MyService.this)
                                            .setVisibility(Notification.VISIBILITY_PUBLIC)
                                            .setSmallIcon(R.drawable.ic_stat_name)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.monitoria_icon))
                                            .setContentTitle("MonitoriaCEFET")
                                            .setAutoCancel(true)
                                            .setVibrate(new long[]{150, 300, 150, 600})
                                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                            .setContentText("Sua conta de monitor já está disponível");

                            Intent resultIntent = new Intent(MyService.this, MenuMonitor.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                            stackBuilder.addParentStack(LoginActivity.class);
                            stackBuilder.addNextIntent(resultIntent);

                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            mNotificationManager.notify(R.mipmap.monitoria_icon22, mBuilder.build());

                            stopSelf();

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                //    Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }


        if (!(unidadeL.equals("CEFET Maracanã universidade") && unidadeL.equals("CEFET Maracanã médio e técnico"))) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                            if (x.equals("MONITOR")) {

                                NotificationCompat.Builder mBuilder =
                                        (NotificationCompat.Builder) new NotificationCompat.Builder(MyService.this)
                                                .setVisibility(Notification.VISIBILITY_PUBLIC)
                                                .setSmallIcon(R.drawable.ic_stat_name)
                                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.monitoria_icon))
                                                .setContentTitle("MonitoriaCEFET")
                                                .setAutoCancel(true)
                                                .setVibrate(new long[]{150, 300, 150, 600})
                                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                                .setContentText("Sua conta de monitor já está disponível");

                                Intent resultIntent = new Intent(MyService.this, MenuMonitor.class);
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                                stackBuilder.addParentStack(LoginActivity.class);
                                stackBuilder.addNextIntent(resultIntent);

                                PendingIntent resultPendingIntent =
                                        stackBuilder.getPendingIntent(
                                                0,
                                                PendingIntent.FLAG_UPDATE_CURRENT
                                        );

                                mBuilder.setContentIntent(resultPendingIntent);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                mNotificationManager.notify(R.mipmap.monitoria_icon22, mBuilder.build());


                       //         Notification n = mBuilder.build();
                         //       n.vibrate = new long[]{150, 300, 150, 600};
                          //      n.flags = Notification.FLAG_AUTO_CANCEL;


                            /*
                                try{
                                    Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    Ringtone toque = RingtoneManager.getRingtone(MyService.this, som);
                                    toque.play();
                                }
                                catch(Exception e){}


                            Log.i("SCRIPT", "Notificação exibidaAAAAAAAAAAAAA");
                            System.out.println("Notificação exibidaAAAAAAAAAAAAA");
                            */

                                stopSelf();


                            }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                //    Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }

    }


}
