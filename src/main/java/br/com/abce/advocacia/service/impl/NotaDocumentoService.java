package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.entity.NotaDocumentoEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.repository.NotaDocumentoRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.io.Serializable;

public class NotaDocumentoService implements Serializable {

    @Inject
    private NotaDocumentoRepository documentoRepository;


    public NotaDocumento buscar(final Long idNotaDocumento) throws InfraestruturaException, RecursoNaoEncontradoException {

        try {

            NotaDocumentoEntity notaDocumentoEntity = documentoRepository.buscar(idNotaDocumento);

            if (notaDocumentoEntity == null)
                throw new RecursoNaoEncontradoException("Documento não encontrado!");

            NotaDocumento notaDocumento = new NotaDocumento();

            notaDocumento.setIdDocumento(notaDocumentoEntity.getId());
            notaDocumento.setNome(notaDocumentoEntity.getNome());
            notaDocumento.setFormato(notaDocumentoEntity.getFormarto());
            notaDocumento.setArquivo(notaDocumentoEntity.getArquivo());
            notaDocumento.setDescricao(notaDocumento.getDescricao());

            return notaDocumento;

        } catch (PersistenceException ex) {
            throw new InfraestruturaException(ex.getMessage(), ex);
        }
    }
}
