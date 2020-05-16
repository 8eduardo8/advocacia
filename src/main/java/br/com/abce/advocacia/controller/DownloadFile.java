package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.service.impl.NotaDocumentoService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Named
@RequestScoped
public class DownloadFile implements Serializable {

    @Inject
    private NotaDocumentoService notaDocumentoService;

    private StreamedContent file;

    public StreamedContent generateFile(NotaDocumento notaDocumento) {

        return generateFile(notaDocumento.getIdDocumento());
    }

    public StreamedContent generateFile(final Long idNotaDocumento) {

        try {

            NotaDocumento document = notaDocumentoService.buscar(idNotaDocumento);

            file = new DefaultStreamedContent(new ByteArrayInputStream(document.getArquivo()),
                    document.getFormato(), document.getNome());

        } catch (InfraestruturaException e) {
            LoggerUtil.error(e.getMessage(), e);
            Mensagem.erro(e.getMessage());
        } catch (RecursoNaoEncontradoException e) {
            LoggerUtil.info(e.getMessage());
        }

        return file;
    }

    public StreamedContent getFile() {
        return file;
    }

}
