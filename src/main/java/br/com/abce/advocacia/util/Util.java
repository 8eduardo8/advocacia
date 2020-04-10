package br.com.abce.advocacia.util;

import org.apache.commons.lang3.StringUtils;

public class Util {

    public String trataParametro(final String parametro) {
        return StringUtils.upperCase(StringUtils.trimToNull(parametro));
    }
}
