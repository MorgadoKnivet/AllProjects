package morgado.mdsoftware.alertmaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser().getUid() != null){
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

        Button buscar = (Button)findViewById(R.id.butLoginLogin);
        progressDialog = new ProgressDialog(Login.this);



        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.emailLogin)).getText().toString();
                String senha = ((EditText)findViewById(R.id.senhaLogin)).getText().toString();
                loguin(email, senha);
            }
        });


    }

    public void loguin(String email, String senha){



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logando...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    finish();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }


}
