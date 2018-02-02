package morgado.mdsoftware.megafone;

/**
 * Created by MoorG on 03/12/2017.
 */

public class GriteAquiClass {

    String titulo, texto;

    public GriteAquiClass(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
