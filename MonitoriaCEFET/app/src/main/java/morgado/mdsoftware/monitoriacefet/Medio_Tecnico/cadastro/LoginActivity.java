package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.menuAluno;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;

/**
 * Created by MoorG on 07/07/2017.
 */

//https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/
public class LoginActivity extends AppCompatActivity {

    EditText email, senha;
    Button loguin;
    TextView avisoNET;
    private MaterialDialog mMaterialDialog;
    private String VERSAO = "2.0";
    String x="";
    private InterstitialAd mInterstitialAd;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    ArrayList unidade = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("versao");
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String  x = d.getValue(String.class);

                    if (!(VERSAO.equals(x))) {
                        showUpdateAppDialog();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*
        MobileAds.initialize(LoginActivity.this, "ca-app-pub-7164742797981159~2228765994");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7869198683351919/7714393949");
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);

        AdView adView = new AdView(LoginActivity.this);
        adView = (AdView) findViewById(R.id.adViewLogin);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/

        avisoNET = (TextView)findViewById(R.id.avisoNet);

        loguin = (Button) findViewById(R.id.buttonLoguinL);

        email = (EditText) findViewById(R.id.redEmail);

        senha = (EditText) findViewById(R.id.senhaL);


        progressDialog = new ProgressDialog(this);


        loguin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!(verificaConexao())){
                    avisoNET.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
                    return;

                }else{
                    avisoNET.setVisibility(View.GONE);
                }

                email = (EditText) findViewById(R.id.redEmail);
                // email.setFocusable(false);
                senha = (EditText) findViewById(R.id.senhaL);
                // senha.setFocusable(false);


                // senhaConfirm = (EditText) findViewById(R.id.repitirSenha);

                userLogin();

            }
        });

    }

    public void userLogin() {
        final String auxEmail = email.getText().toString().trim();
        final String auxSenha = senha.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(auxEmail)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxSenha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }

           progressDialog.setMessage("Logando no sistema...");
           progressDialog.show();

        //logging in the user

        mAuth.signInWithEmailAndPassword(auxEmail, auxSenha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the task is successfull
                if (task.isSuccessful()) {


                     unidade.add("CEFET Maracanã médio e técnico");
                     unidade.add("CEFET Maracanã universidade");
                     unidade.add(""+mAuth.getCurrentUser().getUid());

                    for (int i = 0; i < unidade.size(); i++) {
                        problemaUnidade("" + unidade.get(i));
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

        });
    }

    public void problemaUnidade( String unidadeL) {

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                                    if (x.equals("MONITOR")) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                   //     Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
                                    } else {
                                    //    progressDialog.dismiss();
                                        Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                    }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }


        if (unidadeL.equals("CEFET Maracanã universidade")) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");


            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);



                                    if (x.equals("MONITOR")) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                        Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                    }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
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

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                                    if (x.equals("MONITOR")) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                        Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());
                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                    }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }
    }

    public void irParaCadastro(View v) {
        finish();
        Intent intentAux1 = new Intent(getApplicationContext(), RedefinirSenha.class);
        startActivity(intentAux1);
    }

    public void irParaCreditos(View view) {
        Intent intentAux1 = new Intent(getApplicationContext(), creditos.class);
        startActivity(intentAux1);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), Menu.class));
    }

    public void showUpdateAppDialog(){

        mMaterialDialog = new MaterialDialog(this)
                .setTitle( "Atualize o MonitoriaCEFET")
                .setMessage("Nova versão disponível na PlayStore. O aplicativo está chegando a sua perfeição com novas funções, correção de bugs, entre outros detalhes.")
                .setCanceledOnTouchOutside(false)
                .setPositiveButton( "Atualizar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = getPackageName();
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


        mMaterialDialog.show();
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

/*

Não monitor = ""
monitor novato = "MONITOR"
monitor veterano = "MONITOR_VETERANO"



 */