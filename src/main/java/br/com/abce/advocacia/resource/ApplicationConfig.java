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
        conf.setDescription("Sistema de gestão de Processo judicial");
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

        resources.add(br.com.abce.advocacia.resource.v1.TesteResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.EscritorioResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.UsuarioResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.AutenticacaoResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.NotaResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.ProcessoResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.AndamentoResource.class);
        resources.add(br.com.abce.advocacia.resource.v1.DocumentoResource.class);

        // Swagger API documentation
        resources.add(br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException.class);
        resources.add(br.com.abce.advocacia.exceptions.ValidacaoException.class);
        resources.add(br.com.abce.advocacia.exceptions.InfraestruturaException.class);
        resources.add(br.com.abce.advocacia.exceptions.AcessoNegadoException.class);

        // Swagger API documentation
        resources.add(io.swagger.jaxrs.listing.AcceptHeaderApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    }
    
}
