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


public class RegistrarUsuario extends AppCompatActivity {
private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        mAuth = FirebaseAuth.getInstance();

        Button buscar = (Button)findViewById(R.id.butRegistrarRegistrar);
        progressDialog = new ProgressDialog(RegistrarUsuario.this);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ((EditText)findViewById(R.id.emailCadastro)).getText().toString();
                String senha = ((EditText)findViewById(R.id.senhaCadastro)).getText().toString();
                cadastro(email, senha);
            }
        });

    }


    public void cadastro(final String email, final String senha){


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email não digitado", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Senha não digitada", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Criando sua conta...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(RegistrarUsuario.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            loguin(email,senha);

                        } else {
                            Toast.makeText(RegistrarUsuario.this, "Ops! Houve um erro", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
    public void loguin(String email, String senha){

        mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logando...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(RegistrarUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegistrarUsuario.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
