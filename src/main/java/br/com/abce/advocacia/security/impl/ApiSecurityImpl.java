package br.com.abce.advocacia.security.impl;

import br.com.abce.advocacia.exceptions.AcessoNegadoException;
import br.com.abce.advocacia.resource.v1.AbstractResource;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.util.LoggerUtil;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@ApiSecurity
@Interceptor
public class ApiSecurityImpl implements Serializable {


    public ApiSecurityImpl() {
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {

        LoggerUtil.debug("###############ApiSecurityImpl");
        HttpServletRequest req = ((AbstractResource) context.getTarget()).getHttpServletRequest();

        String abceKey = req.getHeader("abce_key");

        if (abceKey != null && abceKey.equals(this.getKey())) {
            return context.proceed();
        } else {
            throw new AcessoNegadoException("Acesso negado.");
        }
    }

    private String getKey() {
        String wso2key = null;

        try {
            InputStream inputStream = ApiSecurityImpl.class.getClassLoader().getResourceAsStream("/key.properties");
            Throwable var3 = null;

            try {
                Properties properties = new Properties();
                properties.load(inputStream);
                wso2key = (String) properties.get("abce_key");
            } catch (Throwable var13) {
                var3 = var13;
                throw var13;
            } finally {
                if (inputStream != null) {
                    if (var3 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var12) {
                            var3.addSuppressed(var12);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }
        } catch (IOException var15) {
            LoggerUtil.error(var15);
        }

        return wso2key;
    }
}