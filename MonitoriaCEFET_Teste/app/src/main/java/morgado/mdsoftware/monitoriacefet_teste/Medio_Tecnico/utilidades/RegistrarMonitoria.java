package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades;

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

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.Monitoria;

public class RegistrarMonitoria extends AppCompatActivity {
    EditText auxNome, auxAno, auxMateria, auxTurno, auxObs, auxDado1, auxDado2, auxDado3, auxDado4, auxDado5;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_monitoria);
        Button registrar = (Button)findViewById(R.id.butRegistro);

        auxNome = (EditText)findViewById(R.id.nomeRegistro);

        auxDado1 = (EditText)findViewById(R.id.dadosRegistro1);
        auxDado2 = (EditText)findViewById(R.id.dadosRegistro2);
        auxDado3 = (EditText)findViewById(R.id.dadosRegistro3);
        auxDado4 = (EditText)findViewById(R.id.dadosRegistro4);
        auxDado5 = (EditText)findViewById(R.id.dadosRegistro5);

        auxAno = (EditText)findViewById(R.id.anoRegistro);
        auxMateria = (EditText)findViewById(R.id.materiaRegistro);

        auxObs = (EditText)findViewById(R.id.obsRegistro);
        auxTurno = (EditText)findViewById(R.id.turnoRegistro);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxNome = (EditText)findViewById(R.id.nomeRegistro);
                auxDado1 = (EditText)findViewById(R.id.dadosRegistro1);
                auxDado2 = (EditText)findViewById(R.id.dadosRegistro2);
                auxDado3 = (EditText)findViewById(R.id.dadosRegistro3);
                auxDado4 = (EditText)findViewById(R.id.dadosRegistro4);
                auxDado5 = (EditText)findViewById(R.id.dadosRegistro5);

                auxAno = (EditText)findViewById(R.id.anoRegistro);
                auxMateria = (EditText)findViewById(R.id.materiaRegistro);

                auxObs = (EditText)findViewById(R.id.obsRegistro);
                auxTurno = (EditText)findViewById(R.id.turnoRegistro);


                    registrar();



            }
        });

    }

    public void registrar()  {

        String nome, auxAnoR, auxMateriaR, auxTurnoR, auxObservacao, zdado1, zdado2, zdado3, zdado4, zdado5;

        nome = auxNome.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                //      .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .trim();
        auxMateriaR = auxMateria.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                .trim();

        auxAnoR = auxAno.getText().toString().toUpperCase()
                .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .replace("ANO","")
                .replace("/","")
                .trim();

        auxTurnoR = auxTurno.getText().toString().toUpperCase().replace("Ã","A")
                .replace("/","")
                .trim();

        auxObservacao = auxObs.getText().toString()
                .trim();

        zdado1 = auxDado1.getText().toString()
                .trim();

        zdado2 = auxDado2.getText().toString()
                .trim();
        zdado3 = auxDado3.getText().toString()
                .trim();
        zdado4 = auxDado4.getText().toString()
                .trim();
        zdado5 = auxDado5.getText().toString()
                .trim();



        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Escreva o nome do Monitor.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxMateriaR)) {
            Toast.makeText(this, "Escreva a matéria da monitoria.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxAnoR)) {
            Toast.makeText(this, "Escreva o ano da monitoria.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxTurnoR)){
            Toast.makeText(this, "Escreva o turno.", Toast.LENGTH_LONG).show();
            return;
        }

        if (auxAnoR.contains("PRIMEIRO")){
            RegistrarPrimeiroAno(nome, auxMateriaR,"PRIMEIRO", auxTurnoR, auxObservacao, zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("SEGUNDO")){
            RegistrarSegundoAno(nome, auxMateriaR,"SEGUNDO", auxTurnoR, auxObservacao, zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("TERCEIRO")){
            RegistrarTerceiroAno(nome, auxMateriaR,"TERCEIRO", auxTurnoR, auxObservacao, zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("QUARTO")){
            RegistrarQuartoAno(nome, auxMateriaR,"QUARTO", auxTurnoR, auxObservacao, zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        Toast.makeText(RegistrarMonitoria.this,"Registro concluído",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));


    }

    public void RegistrarPrimeiroAno(String nome, String materia, String ano, String turno, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

    }

    public void RegistrarSegundoAno(String nome, String materia, String ano, String turno, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

    }

    public void RegistrarTerceiroAno(String nome, String materia, String ano, String turno, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

    }

    public void RegistrarQuartoAno(String nome, String materia, String ano, String turno, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

    }

    public void RegistrarManha(String nome, String materia, String ano, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){


        Monitoria monitoria = new Monitoria();

        monitoria.setMonitor(nome);
        monitoria.setMateria(materia);
        monitoria.setAno(ano);
        monitoria.setTurno("MANHA");
        monitoria.setObservação(obs);
        monitoria.setZdado1(dado1);
        monitoria.setZdado2(dado2);
        monitoria.setZdado3(dado3);
        monitoria.setZdado4(dado4);
        monitoria.setZdado5(dado5);

        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia)
                .child(ano).child("MANHA").child(nome).child("Dados");

        regMonitoria.setValue(monitoria);
    }

    public void RegistrarTarde(String nome, String materia, String ano, String obs, String dado1, String dado2, String dado3, String dado4, String dado5){
        Monitoria monitoria = new Monitoria();

        monitoria.setMonitor(nome);
        monitoria.setMateria(materia);
        monitoria.setAno(ano);
        monitoria.setTurno("TARDE");
        monitoria.setObservação(obs);
        monitoria.setZdado1(dado1);
        monitoria.setZdado2(dado2);
        monitoria.setZdado3(dado3);
        monitoria.setZdado4(dado4);
        monitoria.setZdado5(dado5);

        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia)
                .child(ano).child("TARDE").child(nome).child("Dados");

        regMonitoria.setValue(monitoria);
    }
}
