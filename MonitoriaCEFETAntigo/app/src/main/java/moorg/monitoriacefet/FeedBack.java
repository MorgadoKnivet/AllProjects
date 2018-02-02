package moorg.monitoriacefet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedBack extends AppCompatActivity {

    EditText textFeedBack, tituloFeedBack;
    Button enviar;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regFeedBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        enviar = (Button)findViewById(R.id.enviarFeedBack);


        textFeedBack = (EditText)findViewById(R.id.textoFeedBack);
        tituloFeedBack = (EditText)findViewById(R.id.tituloFeedBack);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textFeedBack = (EditText)findViewById(R.id.textoFeedBack);
                tituloFeedBack = (EditText)findViewById(R.id.tituloFeedBack);
                enviar();
            }


        });





    }

    private void enviar() {
        String textFeedBackL = textFeedBack.getText().toString();
        String tituloFeedBackL = tituloFeedBack.getText().toString();
        if (TextUtils.isEmpty(textFeedBackL)) {
            Toast.makeText(this, "Ei, Me envie sua opinião. ", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(tituloFeedBackL)) {
            Toast.makeText(this, "Ei, Você esqueceu do título. ", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Enviando FeedBack...");
        progressDialog.show();

        database = FirebaseDatabase.getInstance();
        regFeedBack = database.getReference().child("FeedBack").child(mAuth.getCurrentUser().getUid()).child(tituloFeedBackL);
        regFeedBack.setValue(textFeedBackL);



            database = FirebaseDatabase.getInstance();

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());
        regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {


                        String x = d.getValue(String.class);
                        // Map z = d.getValue(Map.class);
                        // z = z + x;

                        if(x.equals("MONITOR")){
                            progressDialog.dismiss();
                            Toast.makeText(FeedBack.this,"Obrigado por enviar seu FeedBack. ",Toast.LENGTH_LONG).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(FeedBack.this,"Obrigado por enviar seu FeedBack. ",Toast.LENGTH_LONG).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                        }




                    }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });












    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }


}
