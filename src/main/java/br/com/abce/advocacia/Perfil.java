package br.com.abce.advocacia;

public enum Perfil {


    CLIENTE(1, "Cliente"),
    ADVOGADO(2, "Advogado"),
    SECRETARIA(3, "Secretário"),
    ADMINISTRADOR(4, "Administrador");

    private int codigo;
    private String nome;

    Perfil(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getDescricao(){
        return this.nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Perfil getPerfil(final int codPerfil) {

        Perfil ePerfil = null;

        for (Perfil perfil1 : values()) {
            if (perfil1.getCodigo() == codPerfil){
                ePerfil = perfil1;
                break;
            }
        }

        return ePerfil;
    }
}
