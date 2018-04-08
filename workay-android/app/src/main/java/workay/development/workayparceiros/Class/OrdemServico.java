package workay.development.workayparceiros.Class;

import android.widget.ImageView;

/**
 * Created by MoorG on 02/02/2018.
 */

public class OrdemServico {

    // Atributos de Layout

    private String idOS;
    private String nomeCliente;
    private String titulo;
    private String status;

    private String data;
    private String prazo;

    private String dataRealizacao, dataMarcacao, dataEnvio;

    private String pergunta, resposta;

    private String endereco, descricao;

    private String preco;
    private String loja;

    private String comodo;
    private String cpf, email;



// Atributos internos do sistema

    private String tipoServico;


    public OrdemServico(String aux) {
        this.idOS = aux;
        this.nomeCliente =  aux;
        this.titulo =  aux;
        this.status =  aux;
        this.data =  aux;
        this.prazo =  aux;
        this.dataRealizacao = aux;
        this.dataMarcacao =  aux;
        this.dataEnvio =  aux;
        this.pergunta =  aux;
        this.resposta =  aux;
        this.endereco =  aux;
        this.descricao =  aux;
        this.tipoServico =  aux;
        this.preco = aux;
        this.comodo = aux;
        this.cpf = aux;
        this.email = aux;

    }

    public OrdemServico() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComodo() {
        return comodo;
    }

    public void setComodo(String comodo) {
        this.comodo = comodo;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }


    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(String dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getDataMarcacao() {
        return dataMarcacao;
    }

    public void setDataMarcacao(String dataMarcacao) {
        this.dataMarcacao = dataMarcacao;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }


    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }


    public String getIdOS() {
        return idOS;
    }

    public void setIdOS(String idOS) {
        this.idOS = idOS;
    }
    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }
}
