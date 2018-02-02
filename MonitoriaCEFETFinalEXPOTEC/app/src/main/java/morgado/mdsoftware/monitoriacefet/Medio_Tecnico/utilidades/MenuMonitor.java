package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar.AtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.FeedBack;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Avaliar.AvaliarMonitorBusca;
import morgado.mdsoftware.monitoriacefet.Universidade.AtualizarMonitoriaBuscaFaculdade;
import morgado.mdsoftware.monitoriacefet.Universidade.BuscaUniversidade;
import morgado.mdsoftware.monitoriacefet.Universidade.RegistrarMonitoriaFaculdade;


public class MenuMonitor extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    FirebaseAuth mAuth;
    Boolean medio = false, facul =false, vet=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_monitor);
        MobileAds.initialize(MenuMonitor.this,"ca-app-pub-7164742797981159~2228765994" );
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7164742797981159/3853850613");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAuth = FirebaseAuth.getInstance();
        if (!(verificaConexao())){

            Toast.makeText(MenuMonitor.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
            return;

        }else {
            irParaBusca();
        }
        Button irParaBusca = (Button)findViewById(R.id.ProcurarMonitoria);
        final Button irParaRegistro = (Button)findViewById(R.id.CadastroMonitoria);
        Button irParaAvaliar = (Button)findViewById(R.id.AvaliarMonitor);
        Button irParaPerfil = (Button)findViewById(R.id.irParaPerfil);

      //  DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child("CEFET Maracanã médio e técnico").child(mAuth.getCurrentUser().getUid());

        irParaAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaAvaliar();
            }
        });

        irParaPerfil.setOnClickListener(new View.OnClickListener() {
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
                        irParaPerfil();

                    }
                });
            }

        });

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
                       // irParaRegistro();
                        irParaCadastroBoolean();

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
                     //   irParaBusca();
                            irParaBuscaBoolean();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);

                        // Código a ser executado quando uma solicitação de anúncio falhar.
                     //   irParaBusca();
                        irParaBuscaBoolean();
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

    public void irParaPerfil(){
        Intent intentAux1 = new Intent(getApplicationContext(),PerfilMonitor.class);
        startActivity(intentAux1);
    }

    public void irParaAtualizar(View view){
        irParaAtualizarBoolean();
    }


    public void irParaBusca(){

        ArrayList arrayList = new ArrayList();
        arrayList.add("CEFET Maracanã médio e técnico");
        arrayList.add("CEFET Maracanã universidade");
        arrayList.add(""+mAuth.getCurrentUser().getUid());

        for (int i=0;i<arrayList.size();i++){
            String aux = (String)arrayList.get(i);
            irParaBusca(aux);
        }



    }

    public void irParaFeedBack(View view){
        startActivity(new Intent(getApplicationContext(), FeedBack.class));
    }

    public void irParaAvaliar(){
        Toast.makeText(MenuMonitor.this,"Função em desenvolvimento",Toast.LENGTH_SHORT).show();
       // startActivity(new Intent(getApplicationContext(), AvaliarMonitorBusca.class));

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

    public void irParaBusca(String unidadeL){

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        medio = true;
                       // startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
                        if (!(verificaConexao())){

                            Toast.makeText(MenuMonitor.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
                            return;

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

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid());


            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                      //  startActivity(new Intent(getApplicationContext(), BuscaUniversidade.class));
                        facul = true;

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }


        if (!(unidadeL.equals("CEFET Maracanã universidade") && unidadeL.equals("CEFET Maracanã médio e técnico")) ){

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                      //  startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
                        vet = true;

                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    public void irParaAtualizarBoolean(){
        if (facul){
            startActivity(new Intent(getApplicationContext(), AtualizarMonitoriaBuscaFaculdade.class));
            return;

        }
        if ((medio)||(vet)){
            startActivity(new Intent(getApplicationContext(), AtualizarMonitoria.class));
            return;
        }
    }

    public void irParaBuscaBoolean(){
        if (facul){
            startActivity(new Intent(getApplicationContext(), BuscaUniversidade.class));
            return;
        }
        if ((medio)||(vet)){
            startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
            return;
        }
    }

    public void irParaCadastroBoolean(){
        if (facul){
            startActivity(new Intent(getApplicationContext(), RegistrarMonitoriaFaculdade.class));
            return;
        }
        if ((medio)||(vet)){
            startActivity(new Intent(getApplicationContext(), RegistrarMonitoria.class));
            return;
        }
    }


    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }




}
