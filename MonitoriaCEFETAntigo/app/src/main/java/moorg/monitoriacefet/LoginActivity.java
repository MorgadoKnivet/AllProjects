package moorg.monitoriacefet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by MoorG on 07/07/2017.
 */

//https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/
public class LoginActivity extends AppCompatActivity {
    ArrayList auxRegistro;
    EditText email, senha;
    Button loguin;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       MobileAds.initialize(LoginActivity.this,"ca-app-pub-7869198683351919~8806915786" );
    /*    AdView adView = new AdView(LoginActivity.this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7869198683351919/6637525110");

        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
*/


        ;
    //    adView.setAdSize(AdSize.BANNER);
// adView.setAdSize(AdSize.FULL_BANNER);
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
/*
        AdView adView2 = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView2.loadAd(adRequest2);
*/
        //getting firebase auth object
        mAuth = FirebaseAuth.getInstance();
        loguin = (Button) findViewById(R.id.buttonLoguinL);

        email = (EditText) findViewById(R.id.emailL);

        senha = (EditText) findViewById(R.id.senhaL);


        progressDialog = new ProgressDialog(this);
        /* Verificando se usuário ja está logado


        if (mAuth.getCurrentUser()!= null) {
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
        }
*/


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
    public  void userLogin(){
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();


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
        mAuth.signInWithEmailAndPassword(auxEmail,auxSenha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //if the task is successfull
                        if(task.isSuccessful()){

                            //start the profile activity
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





                        }else{
                            Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                      //  progressDialog.dismiss();
                    }
                });

    }



    public void irParaCadastro(View v){
        Intent intentAux1 = new Intent(getApplicationContext(),NewUserActivity.class);
        startActivity(intentAux1);
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }



}
