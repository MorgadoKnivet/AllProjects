package moorg.monitoriacefet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by MoorG on 04/07/2017.
 */
// https://www.simplifiedcoding.net/android-firebase-tutorial-1/ código baseado do site
public class NewUserActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    FirebaseUser user;


    EditText email, senha, senhaConfirm;
    Button loguin;


    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        AdView adView = (AdView)findViewById(R.id.adViewCad);
        //    adView.setAdSize(AdSize.BANNER);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        // adView.setAdSize(AdSize.FULL_BANNER);
        adView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();
        loguin = (Button) findViewById(R.id.buttonLoguin);

        progressDialog = new ProgressDialog(this);

        loguin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                email = (EditText) findViewById(R.id.loguin);
                senha = (EditText) findViewById(R.id.senha);
                senhaConfirm = (EditText) findViewById(R.id.repitirSenha);
                registerUser();
            }
        });



        }

    public void registerUser() {


        String auxSenhaConfirm = senhaConfirm.getText().toString().trim();
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();
        //    String auxSenhaConfirm = senhaConfirm.getText().toString().trim();


            if (!auxSenhaConfirm.equals(auxSenha)) {
                 Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_LONG).show();
                 return;
            }
            //checking if email and passwords are empty
            if (TextUtils.isEmpty(auxEmail)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(auxSenha)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(auxEmail, auxSenha)
                    .addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(NewUserActivity.this, "Registrado com sucesso", Toast.LENGTH_LONG).show();

                                    database = FirebaseDatabase.getInstance();
                                    user =  mAuth.getCurrentUser();
                                    regMonitoria = database.getReference().child("Usuarios").child(user.getUid()).child("Tipo");
                                    regMonitoria.setValue("");


                                progressDialog.dismiss();

                                    finish();




                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));





                            } else {

                                progressDialog.dismiss();

                                Toast.makeText(NewUserActivity.this, "Registration Error", Toast.LENGTH_LONG).show();

                            }

                        }
                    });






    }



    public void irParaLogin(View v){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

}


