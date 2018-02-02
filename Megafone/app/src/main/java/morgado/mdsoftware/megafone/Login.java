package morgado.mdsoftware.megafone;

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

    private EditText email, senha;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText)findViewById(R.id.emailLogin);
        senha = (EditText)findViewById(R.id.senhaLogin);
        Button but = (Button)findViewById(R.id.butLogin);
        progressDialog = new ProgressDialog(this);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logar();
            }
        });




    }

    private void logar(){

        String email = this.email.getText().toString();
        String senha = this.senha.getText().toString();
        auth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Logando...");
        progressDialog.show();


        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //ir para menu
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MenuIntarface.class));

                }else{
                    Toast.makeText(Login.this,"E-mail ou senha incorretos",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });


    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), Principal.class));
    }
}
