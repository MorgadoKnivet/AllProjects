package morgado.mdsoftware.monitoriacefet_teste.Inicial_cadastro_inicial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.menuAluno;

/**
 * Created by MoorG on 07/07/2017.
 */

//https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/
public class LoginActivity extends AppCompatActivity {

    EditText email, senha;
    Button loguin;

    String x;
    private InterstitialAd mInterstitialAd;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    ArrayList unidade = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MobileAds.initialize(LoginActivity.this, "ca-app-pub-7164742797981159~2228765994");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7869198683351919/7714393949");
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);

        AdView adView = new AdView(LoginActivity.this);


        adView = (AdView) findViewById(R.id.adViewLogin);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        //   AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        /*      ;
    //    adView.setAdSize(AdSize.BANNER);
// adView.setAdSize(AdSize.FULL_BANNER);
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        AdView adView2 = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView2.loadAd(adRequest2);
*/
        //getting firebase auth object



         /* Verificando se usuário ja está logado

        if (mAuth.getCurrentUser()!= null) {
            //close this activity
            //opening profile activity
            database = FirebaseDatabase.getInstance();
            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());
            auxRegistro = new ArrayList();

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        String x = d.getValue(String.class);
                        // Map z = d.getValue(Map.class);
                        // z = z + x;

                        if(x.equals("MONITOR")){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                        }else {
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                        }
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Login Error: "+databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });

        }
*/



        loguin = (Button) findViewById(R.id.buttonLoguinL);

        email = (EditText) findViewById(R.id.emailL);

        senha = (EditText) findViewById(R.id.senhaL);


        progressDialog = new ProgressDialog(this);


        loguin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                email = (EditText) findViewById(R.id.emailL);
                // email.setFocusable(false);
                senha = (EditText) findViewById(R.id.senhaL);
                // senha.setFocusable(false);


                // senhaConfirm = (EditText) findViewById(R.id.repitirSenha);

                userLogin();


            }
        });

    }


    //method for user login
    public void userLogin() {
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();


        //checking if email and passwords are empty
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
                 //   unidade.add(""+mAuth.getCurrentUser().getUid());

                    for (int i = 0; i < unidade.size(); i++) {
                        problemaUnidade("" + unidade.get(i));
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                //  progressDialog.dismiss();
            }

        });

        // Código a ser executado quando o anúncio intersticial for fechado.
        //   mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());


        //start the profile activity}
    }


    public void irParaCadastro(View v) {
        finish();
        Intent intentAux1 = new Intent(getApplicationContext(), NewUserActivity.class);
        startActivity(intentAux1);
    }

    public void irParaCreditos(View view) {
        Intent intentAux1 = new Intent(getApplicationContext(), creditos.class);
        startActivity(intentAux1);
    }

    public void problemaUnidade( String unidadeL) {

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(unidadeL).child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


/*
                if (mInterstitialAd.isLoaded()) {
                    progressDialog.dismiss();
                    mInterstitialAd.show();
uni
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
*/

                            if (x.equals("MONITOR")) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                Log.i("ENTROU NO MARACANA", "" + mAuth.getCurrentUser().getUid());
                            } else {
                                progressDialog.dismiss();
                                Log.i("ENTROU NO MARACANA", "" + mAuth.getCurrentUser().getUid());
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

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(unidadeL).child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);



                            if (x.equals("MONITOR")) {
                                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
                            } else {

                                Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
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
                                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                            } else {

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












    }
}

/*

  //logging in the user
        mAuth.signInWithEmailAndPassword(auxEmail, auxSenha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if the task is successfull
                if (task.isSuccessful()) {
                    DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

                    regMonitoria.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {

                                x = d.getValue(String.class);

                            }

                            if (mInterstitialAd.isLoaded()) {
                                progressDialog.dismiss();
                                mInterstitialAd.show();

                                mInterstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {
                                        super.onAdClosed();

                                        // database = FirebaseDatabase.getInstance();
                                        if (x.equals("MONITOR")) {
                                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                        }
                                    }


                                });

                            }else {
                                if (x.equals("MONITOR")) {


                                    startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                } else {


                                    startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                }
                            }


                        /*
                            if (x.equals("MONITOR")) {


                                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                            } else {


                                startActivity(new Intent(getApplicationContext(), menuAluno.class));
                            }

                        }
/*
@Override
public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

        }
        });


        } else {
        Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        }
        //  progressDialog.dismiss();
        }

        });

        // Código a ser executado quando o anúncio intersticial for fechado.
        //   mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());


        //start the profile activity}
        }
 */

