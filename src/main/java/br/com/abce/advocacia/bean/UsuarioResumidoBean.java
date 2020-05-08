package br.com.abce.advocacia.bean;

import br.com.abce.advocacia.Perfil;
import br.com.abce.advocacia.converter.Bean;

import java.beans.Transient;
import java.io.Serializable;

public class UsuarioResumidoBean implements Serializable, Bean {
    
    private Long id;
    private int perfil;
    private String nome;
    private String cpf;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Transient
    public String getDescPerfil(){
        Perfil tipoPerfil = Perfil.getPerfil(this.getPerfil());
        return tipoPerfil != null ? tipoPerfil.getDescricao() : "Desconhecido";
    }

    @Transient
    public boolean isAdmin() {
        return this.perfil == Perfil.ADMINISTRADOR.getCodigo();
    }

    @Transient
    public boolean isCliente() {
        return this.perfil == Perfil.CLIENTE.getCodigo();
    }
}
