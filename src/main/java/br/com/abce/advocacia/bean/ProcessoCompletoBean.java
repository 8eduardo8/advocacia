package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class ProcessoCompletoBean extends ProcessoBean {

    private List<NotaAndamento> listaAndamentos;

    private List<NotaDocumento> listaDocumentos;

    public List<NotaDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<NotaDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<NotaAndamento> getListaAndamentos() {
        return listaAndamentos;
    }

    public void setListaAndamentos(List<NotaAndamento> listaAndamentos) {
        this.listaAndamentos = listaAndamentos;
    }
}
