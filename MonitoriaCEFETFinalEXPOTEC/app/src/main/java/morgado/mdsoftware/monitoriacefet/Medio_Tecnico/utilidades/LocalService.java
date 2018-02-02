package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import morgado.mdsoftware.monitoriacefet.R;

/**
 * Created by MoorG on 19/09/2017.
 */

public class LocalService extends Service {

    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.hello_world;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        /*
        O sistema chama esse método quando o serviço é criado pela primeira vez para realizar procedimentos únicos de configuração (antes de chamar onStartCommand() ou onBind()).
         Se o serviço já estiver em execução, esse método não é chamado.
         */

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;

        /*
        O sistema chama esse método quando outro componente, como uma atividade, solicita que o serviço seja iniciado, chamando startService().
        Quando esse método é executado, o serviço é iniciado e pode ser executado em segundo plano indefinidamente. Se implementar isso, é de sua responsabilidade interromper o serviço quando
         o trabalho for concluído, chamando stopSelf() ou stopService().
        caso queira somente fornecer a vinculação, não é necessário implementar esse método).


         */

    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        /*
        O sistema chama esse método quando o serviço não é mais usado e está sendo destruído.
        O serviço deve implementar isso para limpar quaisquer recursos, como encadeamentos, escutas registradas, receptores etc. Essa é a última chamada que o serviço recebe.
         */

        // Tell the user we stopped.
        Toast.makeText(this,"Service parou de funcionar, foi destruido", Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

        /*
        O sistema chama esse método quando outro componente quer se vincular ao serviço (como para realizações de RPC) chamando bindService().
         Na implementação desse método, você deve fornecer uma interface que os clientes usem para se comunicar com o serviço, retornando um IBinder.
        Você sempre deve implementar esse método, mas, se não quiser permitir a vinculação, retorne nulo.
         */

    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "Serviço foi startado";

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MenuMonitor.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.cefet)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("Titulo")  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }

    /* Manifest
    <manifest ... >
  ...
  <application ... >
      <service android:name=".ExampleService" />
      ...
  </application>
</manifest>     */

}
