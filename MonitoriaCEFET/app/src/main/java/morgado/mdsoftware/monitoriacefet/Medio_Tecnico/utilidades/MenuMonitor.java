package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
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
import morgado.mdsoftware.monitoriacefet.Universidade.AtualizarMonitoriaBuscaFaculdade;
import morgado.mdsoftware.monitoriacefet.Universidade.BuscaUniversidade;
import morgado.mdsoftware.monitoriacefet.Universidade.RegistrarMonitoriaFaculdade;


public class MenuMonitor extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    FirebaseAuth mAuth;
    Boolean medio = false, facul =false, vet=false;
    String packageName = "mdsoftware.development.calcumath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_monitor);





        mAuth = FirebaseAuth.getInstance();
        if (!(verificaConexao())){

            Toast.makeText(MenuMonitor.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
            return;

        }else {
            irParaBusca();
        }



        final Button irParaBusca = (Button)findViewById(R.id.ProcurarMonitoria);
        final Button irParaRegistro = (Button)findViewById(R.id.CadastroMonitoria);
        final Button irParaAvaliar = (Button)findViewById(R.id.AvaliarMonitor);
        final Button irParaPerfil = (Button)findViewById(R.id.irParaPerfil);
        final Button irParaFeed = (Button)findViewById(R.id.feedback) ;
        final Button irParaAtualizar = (Button)findViewById(R.id.atualizarMonitoria);


        final ImageView banner = (ImageView) findViewById(R.id.bannerMenuMonitor);

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                    startActivity( intent );
                }
                catch (android.content.ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                    startActivity( intent );
                }
            }
        });


        final ImageButton closed = (ImageButton)findViewById(R.id.closedMenuMonitor);
        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setVisibility(View.GONE);
                closed.setVisibility(View.GONE);
                irParaAtualizar.setVisibility(View.VISIBLE);
                irParaAvaliar.setVisibility(View.VISIBLE);
                irParaFeed.setVisibility(View.VISIBLE);
                irParaPerfil.setVisibility(View.VISIBLE);
                irParaRegistro.setVisibility(View.VISIBLE);
                irParaBusca.setVisibility(View.VISIBLE);
            }
        });



        irParaFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaFeedBack();
              //  irParaBusca.setVisibility(View.VISIBLE);
            }
        });

        irParaAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaAtualizar();
            }
        });

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

                irParaPerfil();

            }
        });

        irParaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        irParaCadastroBoolean();


            }
        });

        irParaBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                            irParaBuscaBoolean();
                    }


        });


    }

    public void irParaPerfil(){
        Intent intentAux1 = new Intent(getApplicationContext(),PerfilMonitor.class);
        startActivity(intentAux1);
    }

    public void irParaAtualizar(){
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

    public void irParaFeedBack(){
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
