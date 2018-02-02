package morgado.mdsoftware.alertmaps;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VisualizarTarefas extends AppCompatActivity {
    ListaPersonalizada listaPersonalizada;
    private FirebaseAuth mAuth;
    ArrayList arrayTarefas = new ArrayList();
    ListView lv;

    Set set = new HashSet();
    Map<String, Object> map;

    String latitude, longitude;
     Toolbar toolbar;

    ProgressBar progressBar;
    TextView tvCarregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_tarefas);

        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        tvCarregando = (TextView)findViewById(R.id.tvCarregando);
        progressBar.setIndeterminate(true);
      //  progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE,android.graphics.PorterDuff.Mode.MULTIPLY);


   //     listaPersonalizada = new ListaPersonalizada(arrayTarefas,VisualizarTarefas.this);
    //    lv = (ListView) findViewById(R.id.lvTarefas);
//        lv.setAdapter(listaPersonalizada);


        toolbar = (Toolbar) findViewById(R.id.toolbarVisualizarTarefas);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(""+mAuth.getCurrentUser().getUid()).child("Makers");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                for (Object i : set) {

                    String palavra = (String) i;
                    Log.i("Nome dos Makers",palavra);
                    lerLatLng(palavra);

                }
                Log.i("Entrou no setLV","LV" + arrayTarefas.size());
                progressBar.setVisibility(View.GONE);
                tvCarregando.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv = (ListView) findViewById(R.id.lvTarefas);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toolbar.setVisibility(View.VISIBLE);

                Tarefa tarefa = (Tarefa)parent.getAdapter().getItem(position);
                latitude = tarefa.getLat();
                longitude = tarefa.getLng();
                Log.i("Lat Lng", latitude + "  " + longitude);



                return false;
            }
        });




    }


    public void lerLatLng(String id){
        final ArrayList arrayList = new ArrayList();

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);

                    arrayList.add(aux);

                }

                try {


                Log.i("Dados Makers LerLatLng",""+ arrayList.get(0) + "   " + ""+ arrayList.get(1));
                contruirListaTarefas(Double.parseDouble(""+ arrayList.get(0)) ,  Double.parseDouble(""+ arrayList.get(1)));
                }catch (IndexOutOfBoundsException i){
                    // erro tratado
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void contruirListaTarefas(double lat, double lng){

        final Tarefa tarefa = new Tarefa();
        Geocoder gc = new Geocoder(VisualizarTarefas.this);
        List<Address> addressList;
        String endereço,bairro,localidade,areaAdministrativa,pais;
        Log.i("construirLista Dados",""+lat + " " + lng);

        try {


            addressList= gc.getFromLocation(lat, lng, 1);

            try {
                if ( addressList.get(0).getThoroughfare() == null){
                    endereço = "Não encontrado";
                }else{
                    endereço =""+ addressList.get(0).getThoroughfare() + ", " + addressList.get(0).getSubThoroughfare(); // endereço
                }

                if (addressList.get(0).getSubLocality() == null){
                    bairro = "Não encontrado";
                }else{
                    bairro = ""+addressList.get(0).getSubLocality(); // bairro
                }

                if (addressList.get(0).getLocality() == null){
                    localidade = "Não encontrado";
                }else{
                    localidade = ""+addressList.get(0).getLocality(); // localidade
                }

                if (addressList.get(0).getAdminArea() == null) {
                    areaAdministrativa = "Não encontrado";
                }else{
                    areaAdministrativa =""+ addressList.get(0).getAdminArea() ; // Area Administrativa
                }

                 pais = ""+addressList.get(0).getCountryName(); // país

              //  Log.i("Teste address",addressList.get(0).getSubThoroughfare() + " " + addressList.get(0).getSubAdminArea() + " " + addressList.get(0).getPremises() + " " + addressList.get(0).getLocale() + " " + addressList.get(0).getFeatureName());


                tarefa.setEndereço(endereço);
                tarefa.setNumero(bairro);
                tarefa.setLocalidade(localidade);
                tarefa.setAreaAdministrativa(areaAdministrativa);
                tarefa.setPais(pais);
                tarefa.setLat(""+lat);
                tarefa.setLng(""+lng);

                arrayTarefas.add(tarefa);
                listaPersonalizada = new ListaPersonalizada(arrayTarefas,VisualizarTarefas.this);
                lv = (ListView) findViewById(R.id.lvTarefas);
                lv.setAdapter(listaPersonalizada);
                Log.i("Tamanho do ArrayList","LV" + arrayTarefas.size());

            }catch (IndexOutOfBoundsException i){
                Log.i("getLocationErroAAAAA","Entrou aqui");
            }

        }catch (IOException e){
            Log.i("getLocationErroBBBBBB","Entrou aqui");
            e.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu_tarefas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.excluirTarefa:
                excluir();
                return true;

            case R.id.atualizarTarefa:
                //excluirAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    public void excluirAll(){
        set = new HashSet();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(""+mAuth.getCurrentUser().getUid()).child("Makers");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                for (Object i : set) {

                    String palavra = (String) i;

                    Log.i("Excluir palavra",palavra);

                    excluirAllMarker(palavra);
                    finish();
                    startActivity(getIntent());
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/
    public void excluir(){
         set = new HashSet();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(""+mAuth.getCurrentUser().getUid()).child("Makers");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                for (Object i : set) {

                    String palavra = (String) i;

                    Log.i("Excluir palavra",palavra);

                    excluirMarker(palavra);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void excluirMarker(final String id){
        final ArrayList arrayList = new ArrayList();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);
                    arrayList.add(aux);
                    Log.i("Excluir Marker",""+aux);

                }

                try {


                    Log.i("Entrou no excluir",latitude + "" + arrayList.get(0) + " " + arrayList.get(1) );

                if ( (""+arrayList.get(0)).equals(latitude) &&  (""+ arrayList.get(1)).equals(longitude)){
                    Log.i("Entrou no excluirIF", "aaaa");
                    DatabaseReference regMarkers2 = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
                    Tarefa tarefa = new Tarefa();
                    regMarkers2.setValue(tarefa);
                    finish();
                    startActivity(getIntent());

                }
                }catch (IndexOutOfBoundsException i){
                     // erro tratado
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void excluirAllMarker(String id){
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        Tarefa tarefa = new Tarefa();
        regMarkers.setValue(tarefa);
    }

    public void onBackPressed(){

        if (toolbar.getVisibility() == View.VISIBLE){
            toolbar.setVisibility(View.GONE);
            return;
        }else {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

    }

}
