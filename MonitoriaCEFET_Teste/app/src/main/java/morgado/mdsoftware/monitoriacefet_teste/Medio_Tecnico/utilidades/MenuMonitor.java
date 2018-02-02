package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import morgado.mdsoftware.monitoriacefet_teste.Inicial_cadastro_inicial.LoginActivity;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.FeedBack;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar.AtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.avaliar_monitoria.AvaliarMonitorBusca;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;


public class MenuMonitor extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_monitor);
        MobileAds.initialize(MenuMonitor.this,"ca-app-pub-7164742797981159~2228765994" );
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7164742797981159/3853850613");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());




/*
        AdView adView = (AdView)findViewById(R.id.adViewMenuMonitor);
      //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
          AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/

        Button irParaBusca = (Button)findViewById(R.id.ProcurarMonitoria);
        final Button irParaRegistro = (Button)findViewById(R.id.CadastroMonitoria);

        /*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7869198683351919/7714393949");
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(request);
        */

        irParaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        irParaRegistro();

                    }
                });
            }
        });
        irParaBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();

                        // Código a ser executado quando o anúncio intersticial for fechado.
                        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                        irParaBusca();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);

                        // Código a ser executado quando uma solicitação de anúncio falhar.
                        irParaBusca();
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();

                        // Código a ser executado quando o usuário deixou o aplicativo.
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();

                        // Código a ser executado quando o anúncio é exibido.
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();


                        // Code to be executed when an ad finishes loading.
                    }
                });
            }
        });


    }

    public void irParaAtualizar(View view){
        startActivity(new Intent(getApplicationContext(), AtualizarMonitoria.class));
    }

    public void irParaRegistro() {
        startActivity(new Intent(getApplicationContext(), RegistrarMonitoria.class));
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
