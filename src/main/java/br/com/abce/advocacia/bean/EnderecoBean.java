package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class EnderecoBean implements Serializable {

    private Long id;
    private String logradouro;
    private String cep;
    private String bairro;
    private String cidade;
    private String numero;
    private String complemento;
    private String uf;

    public EnderecoBean() {
        super();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return logradouro + ", " + bairro + ", "+cidade + ", " + uf + ", " + cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
