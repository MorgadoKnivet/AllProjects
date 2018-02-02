package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import morgado.mdsoftware.monitoriacefet_teste.R;

public class AtualizarMonitoria extends AppCompatActivity {
    public static final int CONSTANTE_ATUALIZAR_MONITORIA = 2;
    EditText nome, sobrenome, materia, ano, turno;
    Button auxBuscar;
    ArrayList auxRegistro;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_monitoria);



        auxBuscar = (Button) findViewById(R.id.procurarMonitoriaAM);

        nome = (EditText) findViewById(R.id.nomeAtt);
        sobrenome = (EditText) findViewById(R.id.sobrenomeATT);
        materia = (EditText) findViewById(R.id.MateriaATT);
        ano = (EditText) findViewById(R.id.anoAtt);
        turno = (EditText) findViewById(R.id.turnoAtt);

        progressDialog = new ProgressDialog(this);

        auxBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = (EditText) findViewById(R.id.nomeAtt);
                sobrenome = (EditText) findViewById(R.id.sobrenomeATT);
                materia = (EditText) findViewById(R.id.MateriaATT);
                ano = (EditText) findViewById(R.id.anoAtt);
                turno = (EditText) findViewById(R.id.turnoAtt);

                try {


                    atualizarMonitoria();


                }catch (DatabaseException d) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException i) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();

                } catch (NullPointerException n) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void atualizarMonitoria() {

        final String nomeL = nome.getText().toString().toUpperCase().replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();
        final String sobrenomeL = sobrenome.getText().toString().toUpperCase().replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();
        final String materiaL = materia.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();
        final String anoL = ano.getText().toString().toUpperCase()
                .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .replace("ANO","")
                .trim();
        final String turnoL = turno.getText().toString().toUpperCase()
                .replace("Ã","A")
                .trim();

        if (TextUtils.isEmpty(nomeL)) {
            Toast.makeText(this, "Esqueceu o nome do monitor", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(sobrenomeL)) {
            Toast.makeText(this, "Esqueceu o sobrenome do monitor", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(materiaL)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(anoL)) {
            Toast.makeText(this, "Escreva o ano foco da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(turnoL)) {
            Toast.makeText(this, "Escreva o turno da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materiaL).child(anoL).child(turnoL).child(nomeL + " " + sobrenomeL).child("Dados");
        //  DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child("MATEMATICA").child("PRIMEIROANO").child("MANHA").child("GUILHERME_MORGADO");

        auxRegistro = new ArrayList();

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String value = d.getValue(String.class);
                    auxRegistro.add(value);

                    //Log.i("SCRIPT","Entrou no for" + value + " " + nome);

                }

                try {

                    Bundle params = new Bundle();
                    params.putString("Nome", ""+auxRegistro.get(5) );
                    params.putString("Sobrenome",""+auxRegistro.get(6));
                    params.putString("Dias", "" + auxRegistro.get(1));
                    params.putString("Horario", "" + auxRegistro.get(2));
                    params.putString("Local", "" + auxRegistro.get(3));
                    params.putString("Materia", "" + auxRegistro.get(4));
                    params.putString("Ano", "" + auxRegistro.get(0));
                    params.putString("Obs",""+auxRegistro.get(7));
                    params.putString("Turno", "" + auxRegistro.get(8));

                    Intent intent = new Intent(AtualizarMonitoria.this, MenuAtualizarMonitoria.class);
                    intent.putExtras(params);
                    finish();
                    startActivityForResult(intent, CONSTANTE_ATUALIZAR_MONITORIA);


                    //   Log.i("Script", "" + nome + " " + auxRegistro.get(1) + " " + auxRegistro.get(2) + " " + auxRegistro.get(3));
                } catch (IndexOutOfBoundsException i) {
                    Log.i("Erro", "Erro do arraylist retornando nada");
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Monitoria não encontrada ", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AtualizarMonitoria.this, "Ops! Ocorreu um erro " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }

        });
    }
}
