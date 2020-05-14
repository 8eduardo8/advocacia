package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import br.com.abce.advocacia.util.Util;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;

@Named
@RequestScoped
public class AlterarFoto {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private Login login;

    @Inject
    private Util util;

    private UploadedFile file;

    public String alterarFoto() {

        String retorno = "";

        try {

            if (file == null)
                throw  new ValidacaoException("Favor realizar o upload da foto.");

            usuarioService.alterarFoto(util.encodeFileBase64(file.getContents()), file.getContentType(), file.getFileName(), login.getUsuarioBean().getId());

            login.setUsuarioImagem(null);

            Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

            retorno = "dashboard";

        } catch (ValidacaoException ex) {
            Mensagem.info(ex.getMessage());
        } catch (Exception e) {
            Mensagem.erro(e.getMessage());
            LoggerUtil.error(e);
        }
        return retorno;

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public StreamedContent imagePerfil() {

        StreamedContent streamedContent = null;

        if (login.getUsuarioBean() != null) {

            try {

                byte[] buscarFotoUsuarioDeserializada = usuarioService
                        .buscarFotoUsuarioDeserializada(login.getUsuarioBean().getId());

                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(
                        buscarFotoUsuarioDeserializada));

            } catch (RecursoNaoEncontradoException e) {
                Mensagem.info(Consts.NAO_POSSIVEL_DADOS_USUARIO);
            } catch (InfraestruturaException e) {
                Mensagem.erro(e.getMessage());
            }
        }

        return streamedContent;
    }


}
