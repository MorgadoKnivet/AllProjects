package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;

public class ExcluirMonitoria extends AppCompatActivity {
    String nome, materia;
    TextView nomeT, materiaT;
    ProgressDialog progressDialog;

    Set set = new HashSet();
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_monitoria);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                nome =  params.getString("Nome").trim();
                materia =params.getString("Materia").trim();

            }
        }


        nomeT = (TextView)findViewById(R.id.nomeED);
        materiaT = (TextView)findViewById(R.id.materiaED);

        nomeT.setText(nome);
        materiaT.setText(materia);

        progressDialog = new ProgressDialog(this);

        Button excluir = (Button)findViewById(R.id.excluirED);

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acharAno();
            }
        });
    }

    public void acharAno(){

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia);

        //    Log.i("SCRIPT_TURNO",turnoL);

        progressDialog.setMessage("Excluindo...");
        progressDialog.show();

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator
                    map = (Map<String, Object>) dataSnapshot.getValue();


                }
                try {
                    set = map.keySet();

                }catch (NullPointerException n){

                }


                for (Object i : set) {

                    String palavra = (String) i;
                        Log.i("SCRIPT", palavra);
                        excluir(palavra);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExcluirMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                Log.i("SCRIPT", "Entrou no ERRO");
            }


        });


    }

    public void excluir(String ano){



        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(nome).child("Dados");
        Monitoria monitoria = new Monitoria();
        regMonitoria.setValue(monitoria);
        progressDialog.dismiss();
        Toast.makeText(ExcluirMonitoria.this,"Monitoria excluida",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
        finish();

    }

}
