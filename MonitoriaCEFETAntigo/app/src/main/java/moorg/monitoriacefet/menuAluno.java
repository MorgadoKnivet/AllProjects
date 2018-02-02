package moorg.monitoriacefet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class menuAluno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aluno);

        AdView adView = (AdView)findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        AdView adView4 = (AdView)findViewById(R.id.adView4);
        AdRequest adRequest4 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView4.loadAd(adRequest4);


    }

    public void irParaRegistro(View view) {
        startActivity(new Intent(getApplicationContext(), InserirDadosMonitoria.class));
    }

    public void irParaBusca(View view){
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

}
