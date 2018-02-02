package moorg.monitoriacefet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoorG on 07/07/2017.
 */

public class InserirDadosMonitoria extends AppCompatActivity {

    EditText auxLocal, auxHorario, auxMonitor, auxAno, auxMateria, auxDias, auxTurno, auxMonitorSobrenome;
    Button auxRegistrar;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dados_monitoria);

        progressDialog = new ProgressDialog(this);
        auxRegistrar = (Button)findViewById(R.id.Registrar);


        auxMonitor = (EditText)findViewById(R.id.Monitor);
        auxMonitorSobrenome = (EditText)findViewById(R.id.sobrenomeMonitor);
        auxLocal = (EditText)findViewById(R.id.Local);
        auxHorario = (EditText)findViewById(R.id.Horario);
        auxAno = (EditText)findViewById(R.id.ano);
        auxMateria = (EditText)findViewById(R.id.Materia);
        auxDias = (EditText)findViewById(R.id.dias);

        auxRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxMonitor = (EditText)findViewById(R.id.Monitor);
                auxMonitorSobrenome = (EditText)findViewById(R.id.sobrenomeMonitor);
                auxLocal = (EditText)findViewById(R.id.Local);
                auxHorario = (EditText)findViewById(R.id.Horario);
                auxAno = (EditText)findViewById(R.id.ano);
                auxMateria = (EditText)findViewById(R.id.Materia);
                auxDias = (EditText)findViewById(R.id.dias);
                auxTurno = (EditText)findViewById(R.id.TurnoP);
                try {
                    registrar();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });



    }


    public void registrar() throws InterruptedException {


        String auxLocalR, auxHorarioR, auxMonitorR, auxAnoR, auxMateriaR, auxDiasR, auxTurnoR;

        auxLocalR = auxLocal.getText().toString();
        auxHorarioR = auxHorario.getText().toString();
        auxMonitorR = auxMonitor.getText().toString().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ã","A").replace("Õ","O").replace("Ô","O").replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO").replace("Ê","E");
        String sobrenomeL = auxMonitorSobrenome.getText().toString().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ã","A").replace("Õ","O").replace("Ô","O").replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO").replace("Ê","E");
        auxAnoR = auxAno.getText().toString().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ã","A").replace("Õ","O").replace("Ô","O").replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO").replace("Ê","E");
        auxMateriaR = auxMateria.getText().toString().toUpperCase().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O");;
        auxDiasR = auxDias.getText().toString();
        auxTurnoR = auxTurno.getText().toString().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ã","A").replace("Õ","O").replace("Ô","O").replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO").replace("Ê","E");

        if (TextUtils.isEmpty(auxMonitorR)) {
            Toast.makeText(this, "Escreva o nome do Monitor", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(sobrenomeL)) {
            Toast.makeText(this, "Esqueceu o sobrenome do monitor", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxMateriaR)) {
            Toast.makeText(this, "Escreva a matéria da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxAnoR)) {
            Toast.makeText(this, "Escreva o ano da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxDiasR)) {
            Toast.makeText(this, "Escreva o(s) dia(s) da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxHorarioR)) {
            Toast.makeText(this, "Escreva o horário", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxLocalR)) {
            Toast.makeText(this, "Escreva o local", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxTurnoR)){
            Toast.makeText(this, "Escreva o turno", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Logando no sistema...");
        progressDialog.show();

        /*          TESTE OBJETO */


        Monitoria monitoria = new Monitoria();

       monitoria.setMonitor(auxMonitorR);
        monitoria.setSobrenome(sobrenomeL);
        monitoria.setMateria(auxMateriaR);
        monitoria.setAno(auxAnoR);
        monitoria.setTurno(auxTurnoR);
        monitoria.setDia(auxDiasR);
        monitoria.setHorario(auxHorarioR);
        monitoria.setLocal(auxLocalR);



         /*          TESTE OBJETO  fim */



    //    listRegistrar.put("Monitor",auxMonitorR);
      //  listRegistrar.put("Materia",auxMateriaR);
       // listRegistrar.put("Ano",auxAnoR);
     //   listRegistrar.put("Local",auxLocalR);
     //   listRegistrar.put("Dia(s)",auxDiasR);
     //   listRegistrar.put("Horario",auxHorarioR);


       // progressDialog.setMessage("Logando no sistema...");
       // progressDialog.show();

        database = FirebaseDatabase.getInstance();

       regMonitoria = database.getReference().child("Monitorias").child(auxMateriaR.toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ã","A").replace("Õ","O").replace("Ô","O"))
             .child(auxAnoR.toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("3°", "TERCEIRO").replace("°","").replace("4", "QUARTO").replace("Ê", "E")
                        .replace("1°","PRIMEIRO").replace("2°","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO").replace(" ","")).child(auxTurnoR).child(auxMonitorR +" "+ sobrenomeL).child("Dados");

    //    regMonitoria = database.getReference();
        regMonitoria.setValue(monitoria);

        Toast.makeText(InserirDadosMonitoria.this,"Registro concluído",Toast.LENGTH_SHORT).show();
        // finish();



        progressDialog.dismiss();

        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

    }




    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }





}
