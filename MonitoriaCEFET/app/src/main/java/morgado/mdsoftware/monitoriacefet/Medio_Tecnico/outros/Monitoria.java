package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros;

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
    private String observação;
    private String zdado1;
    private String zdado2;
    private String zdado3;
    private String zdado4;
    private String zdado5;
    private String zmMonitorID;

    public String getZmMonitorID() {
        return zmMonitorID;
    }

    public void setZmMonitorID(String zmMonitorID) {
        this.zmMonitorID = zmMonitorID;
    }

    public String getZdado1() {
        return zdado1;
    }

    public void setZdado1(String zdado1) {
        this.zdado1 = zdado1;
    }

    public String getZdado2() {
        return zdado2;
    }

    public void setZdado2(String zdado2) {
        this.zdado2 = zdado2;
    }

    public String getZdado3() {
        return zdado3;
    }

    public void setZdado3(String zdado3) {
        this.zdado3 = zdado3;
    }

    public String getZdado4() {
        return zdado4;
    }

    public void setZdado4(String zdado4) {
        this.zdado4 = zdado4;
    }

    public String getZdado5() {
        return zdado5;
    }

    public void setZdado5(String zdado5) {
        this.zdado5 = zdado5;
    }

    public String getObservação() {
        return observação;
    }

    public void setObservação(String observação) {
        this.observação = observação;
    }



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
