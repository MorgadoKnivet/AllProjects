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

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.ContaMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.FeedBack;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Avaliar.AvaliarMonitorBusca;
import morgado.mdsoftware.monitoriacefet.Universidade.BuscaUniversidade;

public class menuAluno extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    String x;

    private FirebaseAuth mAuth;
    String packageName = "mdsoftware.development.calcumath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aluno);


        mAuth = FirebaseAuth.getInstance();
        if (!(verificaConexao())){

            Toast.makeText(menuAluno.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
            return;

        }
       final Button irParaBusca = (Button)findViewById(R.id.ProcurarMonitoriaMA);
        final Button irParaContaMonitor = (Button)findViewById(R.id.contaMonitor) ;
      final  Button irParaFeed = (Button)findViewById(R.id.feedbackMA) ;
       final Button irParaAvaliar = (Button)findViewById(R.id.AvaliarMonitorMA);

        final ImageView banner = (ImageView) findViewById(R.id.bannerMenuAluno);
        final ImageButton closed = (ImageButton)findViewById(R.id.closedMenuAluno);


        closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setVisibility(View.GONE);
                closed.setVisibility(View.GONE);

                irParaAvaliar.setVisibility(View.VISIBLE);
                irParaFeed.setVisibility(View.VISIBLE);
                irParaContaMonitor.setVisibility(View.VISIBLE);
                irParaBusca.setVisibility(View.VISIBLE);
            }
        });


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



        irParaAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaAvaliar();
            }
        });

        irParaFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaFeedBackAluno();
            }
        });



        irParaContaMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                        startActivity(new Intent(getApplicationContext(), ContaMonitor.class));


            }
        });

        irParaBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        irParaBusca();

            }
        });

    }


    public void irParaBusca(){

        ArrayList arrayList = new ArrayList();
        arrayList.add("CEFET Maracanã médio e técnico");
        arrayList.add("CEFET Maracanã universidade");

        for (int i=0;i<arrayList.size();i++){
            String aux = (String)arrayList.get(i);
            irParaMenu(aux);
        }




      //  startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));
    }

    public void irParaFeedBackAluno(){
        startActivity(new Intent(getApplicationContext(), FeedBack.class));
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

    public void irParaAvaliar(){
        Toast.makeText(menuAluno.this,"Função em desenvolvimento",Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(getApplicationContext(), AvaliarMonitorBusca.class));

    }


    // irParaBusca, ta comnome errado
    public void irParaMenu(String unidadeL){

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));


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

                        startActivity(new Intent(getApplicationContext(), BuscaUniversidade.class));

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

                        startActivity(new Intent(getApplicationContext(), BuscaMonitoria.class));

                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
