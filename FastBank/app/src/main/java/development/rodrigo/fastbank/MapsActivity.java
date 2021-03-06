package development.rodrigo.fastbank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean allowNetwork;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private int cont = 0;
    EditText procurarTarefasEdit, endProxUm, endProxDois;

    View mapView;
    String tipo;

    FirebaseAuth mAuth;
    private Map mapBancosGeral ;
    public static String bancoProx1, bancoProx2;


    Set set = new HashSet();
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ImageButton procurarTarefa = (ImageButton) findViewById(R.id.butProcurarTarefa);
        procurarTarefasEdit = (EditText)findViewById(R.id.procurarTarefa);
        endProxUm = (EditText)findViewById(R.id.bancoProx1);
        endProxDois = (EditText)findViewById(R.id.bancoProx2);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                tipo = params.getString("Tipo").trim();

                if (tipo.equals("comum")){
                    procurarTarefa.setVisibility(View.GONE);
                    procurarTarefasEdit.setVisibility(View.GONE);
                    endProxUm.setVisibility(View.GONE);
                    endProxDois.setVisibility(View.GONE);
                }else {
                    procurarTarefa.setVisibility(View.VISIBLE  );
                    procurarTarefasEdit.setVisibility(View.VISIBLE);
                    endProxUm.setVisibility(View.VISIBLE);
                    endProxDois.setVisibility(View.VISIBLE);
                }

            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        procurarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encontrarEndereco(procurarTarefasEdit.getText().toString(),endProxUm.getText().toString(),endProxDois.getText().toString());
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapBancosGeral = new HashMap();

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,20,30);


            View compassButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            RelativeLayout.LayoutParams layoutParamsCompass = (RelativeLayout.LayoutParams)
                    compassButton.getLayoutParams();

            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            layoutParamsCompass.setMargins(20,0,0,30);



        }


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers");
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){

                }

                for (Object i : set) {

                    String palavra = (String) i;

                    Log.i("TesteNomeMarker",palavra);
                    lerLatLng(palavra);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Object ob = marker.getTag();
                MarkerPersonalizado mp = (MarkerPersonalizado)ob;

                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Object ob = marker.getTag();
                MarkerPersonalizado mp = (MarkerPersonalizado)ob;

                ArrayList auxList = new ArrayList();
                auxList.add(mp.getEndereçoBancoProxUm());
                auxList.add(mp.getEndereçoBancoProxDois());

                for (int i=0;i<auxList.size();i++){
                    String endereco = (String)auxList.get(i);

                    List<Address> list = new ArrayList<Address>();
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        list = (ArrayList<Address>) geocoder.getFromLocationName(endereco, 1);
                    } catch (IOException e) {
                     e.printStackTrace();
                    }
                    catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
                    if(list != null && list.size() > 0) {
                        Address a = list.get(0);

                        double auxLat = a.getLatitude();
                        double auxLng = a.getLongitude();

                        String idProx =  (""+auxLat +"_"+ auxLng).replace(".","").replace("-","sM_");

                        if (i==0){
                           mp.setIdBancoProxUm(idProx);
                        }else{
                            if (i==1){
                                mp.setIdBancoProxDois(idProx);

                                Intent intentAux1 = new Intent(getApplicationContext(), Estatisticas.class);

                                Bundle bundle2 = new Bundle();
                                bundle2.putString("agencia",marker.getTitle());
                                bundle2.putString("Valor",""+mp.getValor());
                                bundle2.putString("status",mp.getStatus());
                                bundle2.putString("prox1Endereco",mp.getEndereçoBancoProxUm());
                                bundle2.putString("prox2Endereco",mp.getEndereçoBancoProxDois());

                                MarkerPersonalizado banco1 = (MarkerPersonalizado) mapBancosGeral.get("sM_22876982899999998_sM_433347878");
                                MarkerPersonalizado banco2 = (MarkerPersonalizado) mapBancosGeral.get("sM_22872634299999998_sM_433396505");


                                bundle2.putString("proxValor1", ""+banco1.getValor());
                                bundle2.putString("proxValor2", ""+banco2.getValor());
                                bundle2.putInt("n1G",+mp.getN1G());
                                bundle2.putInt("n2G",+mp.getN2G());
                                bundle2.putInt("n3G",+mp.getN3G());
                                intentAux1.putExtras(bundle2);
                                startActivity(intentAux1);

                            }
                        }

                    }else{
                        Toast.makeText(MapsActivity.this,"Local não encontrado",Toast.LENGTH_SHORT).show();

                    }




                }


            }
        });


    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(MapsActivity.this,
                            "Permissão de Localização Negada, ...:(", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        allowNetwork = true;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent it = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            allowNetwork = false;
        }

        if (allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            configurarLocalizaçãoAtual(new LatLng(location.getLatitude(), location.getLongitude())); // método ainda não implementado
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void configurarLocalizaçãoAtual(LatLng latLng){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

         //   metodoCentral(latLng.latitude,latLng.longitude);
            mMap.setMyLocationEnabled(true);

            if (cont == 0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
                cont++;
            }

            MyLocation myLocation = new MyLocation();
            mMap.setLocationSource(myLocation);
            myLocation.setLocation(latLng);

        }
    }



    public class MyLocation implements LocationSource {
        private OnLocationChangedListener listener;

        @Override
        public void activate(OnLocationChangedListener listener) {
            this.listener = listener;


        }

        @Override
        public void deactivate() {

        }


        public void setLocation(LatLng latLng){
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);

            if(listener != null){
                listener.onLocationChanged(location);
            }
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }



    public void encontrarEndereco(String endereco, String prox1, String prox2){
        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this);

        try {
            list = (ArrayList<Address>) geocoder.getFromLocationName(endereco, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();

        }
        if(list != null && list.size() > 0) {
            Address a = list.get(0);

            double auxLat = a.getLatitude();
            double auxLng = a.getLongitude();

            String addressN = "Agencia: "+ a.getThoroughfare()  + "," ; // Rua

            LatLng latLng = new LatLng(auxLat,auxLng);

            customAddMarker(latLng, addressN, prox1, prox2);

        }else{
            Toast.makeText(MapsActivity.this,"Local não encontrado",Toast.LENGTH_SHORT).show();
        }



    }

    public void customAddMarker(LatLng latLng, String descricao, String banco1, String banco2 ){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bb);

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latLng.latitude, latLng.longitude)).icon(icon);



        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(update);
        mMap.addMarker(marker);

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_"));
        Tarefa tarefa = new Tarefa();

        tarefa.setLatLng(latLng);

        regMarkers.setValue(tarefa);

        regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_")).child("Endereco");
        tarefa = new Tarefa();
        tarefa.setEndereço(descricao);

        regMarkers.setValue(tarefa);

        regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_")).child("BancosProximos");
        tarefa = new Tarefa();
        tarefa.setBancoProxUm(banco1);
        tarefa.setBancoProxDois(banco2);

        regMarkers.setValue(tarefa);

    }


    public void lerLatLng(final String id){

        final ArrayList arrayList = new ArrayList();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);
                    arrayList.add(aux);

                }

                try {

               DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child(id).child("Endereco");
                    regMarkers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot d : dataSnapshot.getChildren()) {

                                 String aux = d.getValue(String.class);

                                final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bb);
                                MarkerOptions markerLeitura = new MarkerOptions().position(
                                        new LatLng( Double.parseDouble(""+ arrayList.get(0)) ,  Double.parseDouble(""+ arrayList.get(1)))).icon(icon).title(aux);
                                double numero = Math.random() * 20;
                                int valorAleatorio = ((int) Math.round(numero) * 3);

                                final MarkerPersonalizado tag = new MarkerPersonalizado();
                                tag.setLatitude(arrayList.get(0).toString());
                                tag.setLongetude(arrayList.get(1).toString());
                                tag.setValor(valorAleatorio);


                                int n1 = (int)Math.round(Math.random() * 20)*2;
                                int n2 =(int)Math.round(Math.random() * 20)*2;
                                int n3 = (int)Math.round(Math.random() * 20)*2;

                                tag.setId(id);

                                int tempo = valorAleatorio;
                                if (tempo <= 10){
                                    markerLeitura.snippet("Tempo de espera: Baixo");
                                    tag.setStatus("baixo");
                                    while (n1<1){
                                        if (n1 < 1){
                                            n1 = (int)Math.round(Math.random() * 20)*2;
                                        }
                                    }
                                }else{
                                    if (tempo >10 && tempo<=20){
                                        markerLeitura.snippet("Tempo de espera: medio");
                                        tag.setStatus("medio");
                                        while (n2<1){
                                            if (n2 < 1){
                                                n2 = (int)Math.round(Math.random() * 20)*2;
                                            }
                                        }
                                    }else{
                                        if (tempo > 20){
                                            markerLeitura.snippet("Tempo de espera: alto");
                                            tag.setStatus("Alto");
                                            while (n3<1){
                                                if (n3 < 1){
                                                    n3 = (int)Math.round(Math.random() * 20)*2;
                                                }
                                            }
                                        }
                                    }

                                }
                                tag.setN1G(n1);
                                tag.setN2G(n2);
                                tag.setN3G(n3);

                                Marker marker =  mMap.addMarker(markerLeitura);
                                marker.setTag(tag);

                                DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child(id).child("BancosProximos");
                                regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ArrayList list = new ArrayList();
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                                            String aux = d.getValue(String.class);
                                            list.add(aux);
                                        }

                                        tag.setEndereçoBancoProxUm(list.get(0).toString());
                                        tag.setEndereçoBancoProxDois(list.get(1).toString());
                                        mapBancosGeral.put(id,tag);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }catch (IndexOutOfBoundsException i){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}