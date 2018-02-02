package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;

public class PerfilVisualizar extends AppCompatActivity {
    TextView celular,email,fb,outros,aviso1,aviso2,aviso3,aviso4, titulo, number1,number2,number3,number4, ultimaData;
    String nome, materia, nomeNormal;
    ArrayList auxDetalhesContatos, auxDetalhesAvisos;
    Date date = new Date();
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_visualizar);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                nome =  params.getString("Nome").toUpperCase().trim();
                nomeNormal =  params.getString("Nome");
                materia = params.getString("Materia");
                Log.i("Nome",nome + " e  " + nomeNormal);
            }
        }
        sdf = new SimpleDateFormat("dd-MM-yy");

        try {
            date = sdf.parse(""+date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = sdf.format(date);

       Log.i("Horário",""+str);

// Os traços a esqueda dos aviso são os "Number"

        number1 = (TextView)findViewById(R.id.number1);
        number2 = (TextView)findViewById(R.id.number2);
        number3= (TextView)findViewById(R.id.number3);
        number4= (TextView)findViewById(R.id.number4);

        ultimaData = (TextView)findViewById(R.id.ultimaData);



        titulo = (TextView)findViewById(R.id.perfilTituloNomeMonitor);
        titulo.setText("Monitoria de "+materia.toLowerCase());

        celular = (TextView)findViewById(R.id.visualizarPerfilCelular);
        email = (TextView)findViewById(R.id.visualizarPerfilEmail);

        fb = (TextView)findViewById(R.id.visualizarPerfilFB);
        outros = (TextView)findViewById(R.id.visualizarPerfilOutros);


        aviso1 = (TextView)findViewById(R.id.visualizarAviso1);
        aviso2 = (TextView)findViewById(R.id.personalizarAviso2);
        aviso3 = (TextView)findViewById(R.id.visualizarAviso3);
        aviso4 = (TextView)findViewById(R.id.visualizarAviso4);


        retornarDadosContato();
        retornarDadosAviso();
        retornarUltimaAtt();



    }

    public void retornarDadosContato(){
     //   celular = (TextView)findViewById(R.id.visualizarPerfilCelular);
        auxDetalhesContatos = new ArrayList();

        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Visualizar").child(nomeNormal).child("Contatos");

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    Log.i("Detalhes_Contato", aux);
                    auxDetalhesContatos.add(aux);

                }
                try {
                    celular.setText(""+auxDetalhesContatos.get(0));
                    email.setText(""+auxDetalhesContatos.get(1));
                    fb.setText(""+auxDetalhesContatos.get(2));
                    outros.setText(""+auxDetalhesContatos.get(3));
                }catch (IndexOutOfBoundsException i){
                    //
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void retornarDadosAviso(){

        auxDetalhesAvisos = new ArrayList();


        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Visualizar").child(nomeNormal).child("Avisos");

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    Log.i("Detalhes_Aviso", aux);
                    auxDetalhesAvisos.add(aux);

                }
                try {
                    String info1 = ""+auxDetalhesAvisos.get(0);
                    String info2 = ""+auxDetalhesAvisos.get(1);
                    String info3 = ""+auxDetalhesAvisos.get(2);
                    String info4 = ""+auxDetalhesAvisos.get(3);


                    if (info1.equals("")){
                        aviso1.setVisibility(View.GONE);
                        number1.setVisibility(View.GONE);
                    }else{
                        aviso1.setText("" + auxDetalhesAvisos.get(0));
                        aviso1.setVisibility(View.VISIBLE);
                        number1.setVisibility(View.VISIBLE);
                    }

                    if (info2.equals("")){
                        aviso2.setVisibility(View.GONE);
                        number2.setVisibility(View.GONE);
                    }else{
                        aviso2.setText("" + auxDetalhesAvisos.get(1));
                        aviso2.setVisibility(View.VISIBLE);
                        number2.setVisibility(View.VISIBLE);
                    }


                    if (info3.equals("")){
                        aviso3.setVisibility(View.GONE);
                        number3.setVisibility(View.GONE);
                    }else{
                        aviso3.setText("" + auxDetalhesAvisos.get(2));
                        aviso3.setVisibility(View.VISIBLE);
                        number3.setVisibility(View.VISIBLE);
                    }


                    if (info4.equals("")){
                        aviso4.setVisibility(View.GONE);
                        number4.setVisibility(View.GONE);
                    }else{
                        aviso4.setText("" + auxDetalhesAvisos.get(3));
                        aviso4.setVisibility(View.VISIBLE);
                        number4.setVisibility(View.VISIBLE);

                    }


                }catch (IndexOutOfBoundsException i){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void retornarUltimaAtt(){
        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Visualizar").child(nomeNormal).child("UltimaData");
        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);
                    ultimaData.setText("Atualizado dia " + aux);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed() {
        finish();
    }

}
