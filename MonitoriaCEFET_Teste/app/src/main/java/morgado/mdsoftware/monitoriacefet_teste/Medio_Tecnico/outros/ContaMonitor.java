package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import morgado.mdsoftware.monitoriacefet_teste.R;

public class ContaMonitor extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    private FirebaseAuth mAuth;
    EditText nomeMonitor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_monitor);
        mAuth = FirebaseAuth.getInstance();
         nomeMonitor = (EditText)findViewById(R.id.contaNomeMonitor);
        Button enviarMonitor = (Button) findViewById(R.id.contaEnviarMonitor);


        enviarMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviar();

                nomeMonitor = (EditText)findViewById(R.id.contaNomeMonitor);

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(nomeMonitor.getWindowToken(), 0);


            }
        });

    }

    public void enviar(){
        String auxMonitor = nomeMonitor.getText().toString();

        if (TextUtils.isEmpty(auxMonitor)) {
            Toast.makeText(this, "Você esqueceu do seu nome", Toast.LENGTH_LONG).show();
            return;
        }




        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Pedido de conta de monitor").child(""+mAuth.getCurrentUser().getUid());//.child(""+mAuth.getCurrentUser().getEmail());

        regMonitoria.setValue(auxMonitor);

        Toast.makeText(this, "Seu pedido foi enviado", Toast.LENGTH_LONG).show();

        finish();



    }

}
