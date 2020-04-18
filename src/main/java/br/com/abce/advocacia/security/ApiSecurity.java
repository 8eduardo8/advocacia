package br.com.abce.advocacia.security;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PACKAGE})
@Documented
@Inherited
@InterceptorBinding
public @interface ApiSecurity {

}
