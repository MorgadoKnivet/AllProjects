package com.example.moorg.alertmaps;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by MoorG on 15/05/2017.
 */

public class classEspecial {
    private double lat;
    ArrayList aux = new ArrayList();

    public double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public ArrayList getLatLng(){
        LatLng latLng = new LatLng(lat,lng);
        aux.add(latLng);
        return aux;
    }


}
