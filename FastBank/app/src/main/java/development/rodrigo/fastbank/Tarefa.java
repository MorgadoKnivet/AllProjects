package development.rodrigo.fastbank;

import com.google.android.gms.maps.model.LatLng;



public class Tarefa {

    private LatLng latLng;

    private String endereco;

    private String bancoProxUm, bancoProxDois;

    public LatLng getLatLng() {
        return latLng;
    }

    public String getEndereco() {
        return endereco;
    }




    public String getBancoProxUm() {
        return bancoProxUm;
    }

    public void setBancoProxUm(String bancoProxUm) {
        this.bancoProxUm = bancoProxUm;
    }

    public String getBancoProxDois() {
        return bancoProxDois;
    }

    public void setBancoProxDois(String bancoProxDois) {
        this.bancoProxDois = bancoProxDois;
    }



    public void setEndereço(String endereco) {
        this.endereco = endereco;
    }



    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}


