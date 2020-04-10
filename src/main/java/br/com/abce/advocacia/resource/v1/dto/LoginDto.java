package br.com.abce.advocacia.resource.v1.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class LoginDto implements Serializable {

    private String usuario;
    private String senha;

    public LoginDto() {
        super();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
