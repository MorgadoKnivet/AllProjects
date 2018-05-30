package yourdev.morgado.gravaraudio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Noise extends Activity {

    TextView mStatusView;
    //TextView calaBoca;
    MediaRecorder mRecorder;
    Thread runner;
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;
    MediaPlayer mp = new MediaPlayer();
    android.media.MediaPlayer mPlayer;
    ArrayList list = new ArrayList();

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    public static final int RECORD_AUDIO = 0;
    ProgressBar progressBar;
    SeekBar medioProgressBar;
    public double TEMPO_MEDIO = 60.00;
    public int porcentagem;
    //

    final Runnable updater = new Runnable(){

        public void run(){

            updateTv();

        };
    };

    final Handler mHandler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recorder_and_play);
        mStatusView = (TextView) findViewById(R.id.textViewCaminho);
        //calaBoca = (TextView)findViewById(R.id.calaBoca);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        medioProgressBar = (SeekBar)findViewById(R.id.seekBarUsuario);
        medioProgressBar.setProgress((int)TEMPO_MEDIO/2);

        medioProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(),"Progress: "+progress,Toast.LENGTH_SHORT).show();
                TEMPO_MEDIO = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mPlayer = android.media.MediaPlayer.create(getApplicationContext(), R.raw.toque_hotline_bling);
        if (runner == null)
        {
            runner = new Thread(){
                public void run()
                {
                    while (runner != null)
                    {
                        try
                        {
                            Thread.sleep(1000);
                            Log.i("Noise", "Tock");
                        } catch (InterruptedException e) { };
                        mHandler.post(updater);

                        // You should use Handler.post() whenever you want to do operations in the UI thread.
                        //
                        //So let's say in the callback (which is running in separate thread) you want to change a TextView's text, you should use Handler.post().
                        //
                        //In Android, as in many other UI frameworks, UI elements (widgets) can be only modified from main thread.

                       /* Você deve usar Handler.post () sempre que quiser fazer operações no thread da interface do usuário.

                         Então, digamos que no retorno de chamada (que está sendo executado no thread separado) você deseja alterar o texto de um TextView, você deve usar Handler.post ().

                         No Android, como em muitos outros frameworks de interface do usuário, os elementos da interface do usuário (widgets) podem ser modificados apenas a partir do thread principal. */
                    }
                }
            };

            runner.start();
            Log.d("Noise", "start runner()");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    public void onResume()
    {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    Noise.RECORD_AUDIO);

        } else {
            startRecorder();
        }
             //   startRecorder();

    }


    // pausa a activity se ficar em segundo plano
    public void onPause()
    {
        super.onPause();
     //   stopRecorder();
    }

    public void startRecorder(){
        if (mRecorder == null)
        {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try
            {
                mRecorder.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try
            {
                mRecorder.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }

            //mEMA = 0.0;
        }

    }
    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void updateTv(){

        double getAmplitude = getAmplitudeEMA();
       // getAplitude = 10 * Math.log10( getAplitude );
        mStatusView.setText(String.format("%.2f",getAmplitude));

        // se amplitude
        /*
        double auxResult = 0;
        if (getAmplitude>200){
            auxResult= 100;
        }else{
             // 100 - 200
            //   x  - getAmplitude;
            double aux = 100 * getAmplitude;
            auxResult = aux/200;

        }
        */



        int progressAmplitude = (int)getAmplitude;
       // mStatusView.setText(String.format("%.2f",auxResult) + " dB");
        progressBar.setProgress(progressAmplitude);

        list.add(getAmplitude);

        if (list.size() < 4){

            //calaBoca.setVisibility(View.GONE);
       //     mPlayer = mp.pauseMusic(mPlayer);
            return;
        }else {

            double result = 0;
            for (int i=0;i<list.size();i++){

                double aux = (double)list.get(i);
                if (aux < TEMPO_MEDIO ) {
                 //   calaBoca.setVisibility(View.GONE);
                    mPlayer = mp.pauseMusic(mPlayer);
                    break;
                }
                result = result + aux;

            }


            if (result / 4 >= TEMPO_MEDIO ) {
              //  calaBoca.setVisibility(View.VISIBLE);
                mPlayer = mp.playMusic(getApplicationContext(), mPlayer);
                list = new ArrayList();

            } else {
                mPlayer = mp.pauseMusic(mPlayer);
             //   calaBoca.setVisibility(View.GONE);
                list = new ArrayList();

            }

        }


    }
    public double soundDb(double ampl){
        return  20 * Math.log10(getAmplitudeEMA() / ampl);
    }

    // 10Log10(I/10-12)


    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude());
        else
            return 0;

        // Returns the maximum absolute amplitude that was sampled since the last call to this method. Call this only after the setAudioSource().
    }

    public double getAmplitudeEMA() {

        int amp =(int) getAmplitude();
     //   Log.i("Noise", "Amplitude: "+(amp/10)*2);

       // mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;

        double result = 10 * Math.log10( amp);
        return result;
       // return (amp/10)*2 ;

    }

}
