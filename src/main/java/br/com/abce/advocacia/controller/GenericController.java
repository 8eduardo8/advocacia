package br.com.abce.advocacia.controller;

import javax.faces.context.FacesContext;
import java.util.Map;

public abstract class GenericController {

    protected String getProcessoIdFromRequest() {

        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        final String idProcesso = params.get("idProcesso");

        return idProcesso;
    }
}
