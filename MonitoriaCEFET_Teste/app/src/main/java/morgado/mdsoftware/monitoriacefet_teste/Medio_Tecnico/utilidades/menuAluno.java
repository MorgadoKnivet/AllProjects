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
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.ContaMonitor;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.FeedBack;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.avaliar_monitoria.AvaliarMonitorBusca;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;

public class menuAluno extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aluno);
        MobileAds.initialize(menuAluno.this,"ca-app-pub-7164742797981159~2228765994" );


        Button irParaBusca = (Button)findViewById(R.id.ProcurarMonitoriaMA);
        Button irParaContaMonitor = (Button)findViewById(R.id.contaMonitor) ;


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7164742797981159/3853850613");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        irParaContaMonitor.setOnClickListener(new View.OnClickListener() {
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
                        startActivity(new Intent(getApplicationContext(), ContaMonitor.class));
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);

                        // Código a ser executado quando uma solicitação de anúncio falhar.
                        startActivity(new Intent(getApplicationContext(), ContaMonitor.class));
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


                });
            }
        });


/*

        AdView adView = (AdView)findViewById(R.id.adViewMenuAlunoTop);
       // AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
          AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        AdView adView4 = (AdView)findViewById(R.id.adViewMenuAlunoBot);
       // AdRequest adRequest4 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
          AdRequest adRequest4 = new AdRequest.Builder().build();
        adView4.loadAd(adRequest4);
*/

    }

    public void irParaRegistro(View view) {
        startActivity(new Intent(getApplicationContext(), InserirDadosMonitoria.class));
    }

    public void irParaBusca(){
        startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
    }

    public void irParaFeedBackAluno(View view){
        startActivity(new Intent(getApplicationContext(), FeedBack.class));
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

    public void irParaAvaliar(View view){
        startActivity(new Intent(getApplicationContext(), AvaliarMonitorBusca.class));

    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
