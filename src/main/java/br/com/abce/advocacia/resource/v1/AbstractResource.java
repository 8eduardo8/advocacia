package br.com.abce.advocacia.resource.v1;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

public abstract class AbstractResource {

    @Context
    private HttpServletRequest httpServletRequest;

    public AbstractResource() {
        super();
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
