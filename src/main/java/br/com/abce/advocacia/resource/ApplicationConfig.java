/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.abce.advocacia.resource;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author alanb
 */
@ApplicationPath("/rest")
public class ApplicationConfig extends Application {


    public ApplicationConfig() {

        BeanConfig conf = new BeanConfig();
        conf.setTitle("API Sistema de Gestão de Processo Judicial");
        conf.setDescription("Sistema de gestão de processo judicial");
        conf.setVersion("1.0.0");
        conf.setBasePath("/advocacia/rest");
        conf.setResourcePackage("br.com.abce.advocacia");
        conf.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**Z
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        
        resources.add(br.com.abce.advocacia.resource.TesteResource.class);

        // Swagger API documentation

        // Swagger API documentation
        resources.add(io.swagger.jaxrs.listing.AcceptHeaderApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    }
    
}
