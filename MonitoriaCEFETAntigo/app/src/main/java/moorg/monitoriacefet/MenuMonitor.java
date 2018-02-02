package moorg.monitoriacefet;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MenuMonitor extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_monitor);
        AdView adView = (AdView)findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        Button button = (Button)findViewById(R.id.ProcurarMonitoria);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7869198683351919/7714393949");
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(request);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                irParaBusca();

            }
        });


    }

    public void irParaRegistro(View view) {
        startActivity(new Intent(getApplicationContext(), InserirDadosMonitoria.class));
    }

    public void irParaBusca(){

        startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
    }
    public void irParaFeedBack(View view){
        startActivity(new Intent(getApplicationContext(), FeedBack.class));
    }

    public void irParaAvaliar(View view){
        startActivity(new Intent(getApplicationContext(), AvaliarMonitorBusca.class));

    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

}
