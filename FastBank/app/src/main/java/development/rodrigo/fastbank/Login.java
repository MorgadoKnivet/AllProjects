package development.rodrigo.fastbank;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Set set = new HashSet();
    Map<String, Object> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

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



        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    conferirConta();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Email ou senha incorretos",Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    public void conferirConta(){

        final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum");
        regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){

                }

                int cont = 0;
                for (Object i : set) {
                    String palavra = (String) i;
                    Log.i("Makers",palavra);
                    if (palavra.equals(mAuth.getCurrentUser().getUid())){

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Tipo","comum");
                        intent.putExtras(bundle);
                        progressDialog.dismiss();
                        startActivity(intent);

                    }else{
                        cont = cont+1;
                        if (cont==set.size()){

                            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum");

                            regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        map = (Map<String, Object>) dataSnapshot.getValue();
                                    }
                                    try {
                                        set = map.keySet();
                                    }catch (NullPointerException n){

                                    }

                                    int cont = 0;
                                    for (Object i : set) {
                                        String palavra = (String) i;
                                        Log.i("Makers", palavra);
                                        if (palavra.equals(mAuth.getCurrentUser().getUid())) {

                                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Tipo", "Admim");
                                            intent.putExtras(bundle);
                                            progressDialog.dismiss();

                                            startActivity(intent);

                                        }
                                        else{
                                            cont = cont+1;
                                            if (cont==set.size()){
                                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("Tipo", "Admim");
                                                intent.putExtras(bundle);
                                                progressDialog.dismiss();

                                                startActivity(intent);
                                                Toast.makeText(Login.this, "Gerente", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }



                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }
}
