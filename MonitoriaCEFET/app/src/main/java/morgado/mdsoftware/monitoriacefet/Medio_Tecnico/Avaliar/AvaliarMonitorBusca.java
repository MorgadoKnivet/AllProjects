package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Avaliar;

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

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet.R;


public class AvaliarMonitorBusca extends AppCompatActivity {

    public static final int CONSTANTE_AVALIAR_MONITORIA = 1;

    Button auxEnviar;
    EditText nome, sobrenome, materia, ano, turno;
    ArrayList auxRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_monitor_busca);


        auxEnviar = (Button) findViewById(R.id.buscaAMB);
        nome = (EditText) findViewById(R.id.nomeAv);
        sobrenome = (EditText) findViewById(R.id.sobrenomeMonitor);
        materia = (EditText) findViewById(R.id.materiaAV);
        ano = (EditText) findViewById(R.id.anoAV);
        turno = (EditText) findViewById(R.id.turnoAv);


        auxEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   auxRegistro.clear();
                nome = (EditText) findViewById(R.id.nomeAv);
                sobrenome = (EditText) findViewById(R.id.sobrenomeMonitor);
                materia = (EditText) findViewById(R.id.materiaAV);
                ano = (EditText) findViewById(R.id.anoAV);
                turno = (EditText) findViewById(R.id.turnoAv);
                try {

                    procurarDados();


                    //  LerDetalhes();
                } catch (DatabaseException d) {
                    Toast.makeText(AvaliarMonitorBusca.this, "Ops! Seu monitor não foi encontrado", Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException i) {
                    Toast.makeText(AvaliarMonitorBusca.this, "Ops! Seu monitor não foi encontrado", Toast.LENGTH_LONG).show();

                } catch (NullPointerException n) {
                    Toast.makeText(AvaliarMonitorBusca.this, "Ops! Seu monitor não foi encontrado", Toast.LENGTH_LONG).show();
                }

            }


        });


    }

    public void procurarDados() {
        final String nomeL = nome.getText().toString().toUpperCase().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O");
        final String sobrenomeL = sobrenome.getText().toString().toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("4", "QUARTO").replace("Ê", "E").replace("°", "").replace(" ", "");
        final String materiaL = materia.getText().toString().toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("4", "QUARTO").replace("Ê", "E").replace("°", "").replace(" ", "");
        final String anoL = ano.getText().toString().toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("4", "QUARTO").replace("Ê", "E").replace("°", "").replace(" ", "");
        final String turnoL = turno.getText().toString().toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("4", "QUARTO").replace("Ê", "E").replace("°", "").replace(" ", "");


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



                    auxRegistro.get(1); // Dias
                    auxRegistro.get(2); // Horário
                    auxRegistro.get(3); // Local
                    auxRegistro.get(4); // Matéria

                    Bundle params = new Bundle();

                    params.putString("Nome", nomeL + " " + sobrenomeL);
                    params.putString("Dias", "" + auxRegistro.get(1));
                    params.putString("Horario", "" + auxRegistro.get(2));
                    params.putString("Local", "" + auxRegistro.get(3));
                    params.putString("Materia",""+auxRegistro.get(4));
                    params.putString("Ano",""+auxRegistro.get(0));
                    params.putString("Turno",""+auxRegistro.get(7));

                    Intent intent = new Intent(AvaliarMonitorBusca.this, AvaliarMonitor.class);
                    intent.putExtras(params);

                    startActivityForResult(intent, CONSTANTE_AVALIAR_MONITORIA);


                 //   Log.i("Script", "" + nome + " " + auxRegistro.get(1) + " " + auxRegistro.get(2) + " " + auxRegistro.get(3));
                } catch (IndexOutOfBoundsException i) {
                    Log.i("Erro", "Erro do arraylist retornando nada");
                    Toast.makeText(AvaliarMonitorBusca.this, "Ops! Monitoria não encontrada ", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AvaliarMonitorBusca.this, "Ops! Ocorreu um erro " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }

        });
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

}
