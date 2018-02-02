package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import morgado.mdsoftware.monitoriacefet_teste.R;

public class MenuAtualizarMonitoria extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public static final int CONSTANTE_MENU_ATUALIZAR = 3;
    Bundle params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_atualizar_monitoria);

        MobileAds.initialize(MenuAtualizarMonitoria.this,"ca-app-pub-7164742797981159~2228765994" );
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7164742797981159/3853850613");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Button irParaAttObservacao = (Button)findViewById(R.id.attMenuObservacao);
        Button irParaAttDados = (Button)findViewById(R.id.attMenuDados);
        final Button irParaAttExluir = (Button)findViewById(R.id.attMenuExcluir);


        Intent intent = getIntent();
        if (intent != null) {
            params = intent.getExtras();

        }



        irParaAttDados.setOnClickListener(new View.OnClickListener() {
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
                        irParaAttDados();

                    }
                });
            }
        });

        irParaAttObservacao.setOnClickListener(new View.OnClickListener() {
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
                        irParaAttObservacao();

                    }
                });
            }
        });


        irParaAttExluir.setOnClickListener(new View.OnClickListener() {
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
                       irParaAttExcluir();

                    }
                });
            }
        });


    }



    public void irParaAttDados(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this, AtualizarDados.class);
        intent.putExtras(params);
        finish();
        startActivityForResult(intent, CONSTANTE_MENU_ATUALIZAR);

    }

    public void irParaAttObservacao(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this, AtualizarObservarcao.class);
        intent.putExtras(params);
        finish();
        startActivityForResult(intent, CONSTANTE_MENU_ATUALIZAR);
    }

    public void irParaAttExcluir(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this,  ExcluirMonitoria.class);
        intent.putExtras(params);
        finish();
        startActivityForResult(intent, CONSTANTE_MENU_ATUALIZAR);

    }



}
