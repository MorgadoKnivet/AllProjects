package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.RegistrarMonitoria;
import morgado.mdsoftware.monitoriacefet.R;

public class RedefinirSenha extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        Button enviar = (Button) findViewById(R.id.enviarRedSenha);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                String email = ((EditText) findViewById(R.id.redEmail)).getText().toString();
                progressDialog = new ProgressDialog(RedefinirSenha.this);

                progressDialog.setMessage("Enviando email");
                progressDialog.show();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(RedefinirSenha.this, "E-mail enviado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RedefinirSenha.this, "Email não cadastrado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
