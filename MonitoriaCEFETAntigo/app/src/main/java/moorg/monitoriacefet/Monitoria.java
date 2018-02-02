package moorg.monitoriacefet;

/**
 * Created by MoorG on 27/07/2017.
 */

public class Monitoria {

    private String monitor;
    private String Sobrenome;
    private String materia;
    private String ano;
    private String turno;
    private String dia;
    private String horario;
    private String local;

    public Monitoria(){

    }

    public String getMonitorSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String Sobrenome) {
        this.Sobrenome = Sobrenome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getMateria(){
        return materia;
    }

    public String getAno() {
        return ano;
    }

    public String getDia() {
        return dia;
    }

    public String getHorario() {
        return horario;
    }

    public String getLocal() {
        return local;
    }

    public String getMonitor() {
        return monitor;
    }
}
