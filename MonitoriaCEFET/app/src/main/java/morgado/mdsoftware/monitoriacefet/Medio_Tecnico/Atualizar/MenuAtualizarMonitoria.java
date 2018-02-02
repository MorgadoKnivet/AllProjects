package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;

public class MenuAtualizarMonitoria extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public static final int CONSTANTE_MENU_ATUALIZAR = 3;
    Bundle params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_atualizar_monitoria);



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


                        irParaAttDados();


            }
        });

        irParaAttObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        irParaAttObservacao();


            }
        });


        irParaAttExluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       irParaAttExcluir();


            }
        });



    }



    public void irParaAttDados(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this, AtualizarDados.class);
        intent.putExtras(params);

        startActivityForResult(intent, CONSTANTE_MENU_ATUALIZAR);
        finish();
    }

    public void irParaAttObservacao(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this, PerfilMonitor.class);

        startActivity(intent);
        finish();
    }

    public void irParaAttExcluir(){
        Intent intent = new Intent(MenuAtualizarMonitoria.this,  ExcluirMonitoria.class);
        intent.putExtras(params);

        startActivityForResult(intent, CONSTANTE_MENU_ATUALIZAR);
        finish();
    }


    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

    }
}
