package morgado.mdsoftware.monitoriacefet.Universidade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar.AtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar.MenuAtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet.R;

public class AtualizarMonitoriaBuscaFaculdade extends AppCompatActivity {
    public static final int CONSTANTE_ATUALIZAR_MONITORIA = 2;
    ProgressDialog progressDialog;
    Set set = new HashSet();
    Map<String, Object> map;
    FirebaseAuth mAuth;
    ArrayList auxRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_monitoria_busca_faculdade);

        mAuth = FirebaseAuth.getInstance();

        Button buscar = (Button)findViewById(R.id.butAtualizarFaculBusca);


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String materia = ((EditText)findViewById(R.id.materiaAtualizarBuscaFacul)).getText().toString();
                irParaMenuAtulizar(materia);
            }
        });

    }

    public void irParaMenuAtulizar(String auxMateria){

        // LENDO MATÉRIA
        progressDialog = new ProgressDialog(AtualizarMonitoriaBuscaFaculdade.this);

        auxMateria = auxMateria.toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();

        if (TextUtils.isEmpty(auxMateria)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

 //       progressDialog.setMessage("Buscando");
   //     progressDialog.show();


        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade");

        //    Log.i("SCRIPT_TURNO",turnoL);

        final String finalAuxMateria = auxMateria;
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
                    Toast.makeText(AtualizarMonitoriaBuscaFaculdade.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                   // adView.setVisibility(View.VISIBLE);
                }
                int cont = 0;


                for (Object i : set) {

                    String palavra = (String) i;
                    if (palavra.contains(finalAuxMateria)){
                        Log.i("SCRIPT_Materia", palavra);
                        LerNome(palavra);
                    }else {
                        cont = cont + 1;
                    }

                    if (cont == set.size()){
                        Toast.makeText(AtualizarMonitoriaBuscaFaculdade.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AtualizarMonitoriaBuscaFaculdade.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void LerNome(final String auxPalavra){

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("NomeMonitor");
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String value = d.getValue(String.class);
                    Log.i("NomeMonitor",value);
                    LerDados(auxPalavra,value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void LerDados(String auxPalavra, String auxNomeMonitor){

        auxRegistro = new ArrayList();
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(auxPalavra).child(auxNomeMonitor.toUpperCase()).child("Dados");
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String value = d.getValue(String.class);
                    Log.i("Dados",value);
                    auxRegistro.add(value);
                }
                try {

                    String user = ""+auxRegistro.get(7);
                    if (!(user.equals(mAuth.getCurrentUser().getUid()))){
                        Toast.makeText(AtualizarMonitoriaBuscaFaculdade.this,"Monitoria não registrada por essa conta",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Bundle params = new Bundle();
                    params.putString("Materia", "" + auxRegistro.get(0));
                    params.putString("Nome", "" + auxRegistro.get(1));
                    params.putString("dado1", "" + auxRegistro.get(2));
                    params.putString("dado2", "" + auxRegistro.get(3));
                    params.putString("dado3", "" + auxRegistro.get(4));
                    params.putString("dado4", "" + auxRegistro.get(5));
                    params.putString("dado5", "" + auxRegistro.get(6));
                    Intent intent = new Intent(AtualizarMonitoriaBuscaFaculdade.this, AtualizarMonitoriaFaculdade.class);
                    intent.putExtras(params);
                //    progressDialog.dismiss();

                    startActivityForResult(intent, CONSTANTE_ATUALIZAR_MONITORIA);
                    finish();
                    return;

                    //   Log.i("Script", "" + nome + " " + auxRegistro.get(1) + " " + auxRegistro.get(2) + " " + auxRegistro.get(3));
                } catch (IndexOutOfBoundsException i) {
                    Log.i("Erro", "Erro do arraylist retornando nada");
                    Toast.makeText(AtualizarMonitoriaBuscaFaculdade.this, "Ops! Monitoria não encontrada ", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
