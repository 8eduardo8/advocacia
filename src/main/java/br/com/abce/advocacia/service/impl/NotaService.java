package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.*;
import br.com.abce.advocacia.entity.NotaDocumentoEntity;
import br.com.abce.advocacia.entity.NotaEntity;
import br.com.abce.advocacia.entity.NotaTextoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.NotaRepository;
import br.com.abce.advocacia.repository.ProcessoUsuarioRepository;
import br.com.abce.advocacia.util.Consts;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotaService implements Serializable {

    @Inject
    private NotaRepository notaRepository;

    @Inject
    private ProcessoUsuarioRepository processoUsuarioRepository;

    @Inject
    private ProcessoService processoService;


    public List<NotaBean> listar(final Long idProcesso) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (idProcesso == null || idProcesso == 0L)
            throw new ValidacaoException("Id do processo não informado.");


        final List<NotaEntity> notaEntityList = notaRepository.listar(idProcesso);

        if (notaEntityList.isEmpty())

            throw new RecursoNaoEncontradoException("Nota(s) não encontrada(s)");

        List<NotaBean> notaBeanList = new ArrayList<>();

        for (NotaEntity entity : notaEntityList) {

            NotaBean bean = new NotaBean();

            bean.setDataCadastro(entity.getDataCadastro());
            bean.setId(entity.getId().intValue());
            bean.setIdUsuario(entity.getProcessoUsuarioByProcessoUsuarioId().getUsuarioByUsuarioId().getId());

            NotaTextoEntity textoEntity = entity.getNotaTextoByNotaTextoId();

            NotaMensagem notaMensagem = null;

            if (textoEntity != null) {

                notaMensagem = new NotaMensagem();
                notaMensagem.setMensagem(textoEntity.getDescricao());
                notaMensagem.setTipo(textoEntity.getTipo());
                notaMensagem.setNota(textoEntity.getId().intValue());

            }

            bean.setNotaMensagem(notaMensagem);

            notaBeanList.add(bean);
        }

        return notaBeanList;
    }

    @Transactional
    public void salvarNota(NotaBean notaBean) throws ValidacaoException, InfraestruturaException {

        if (notaBean.getIdProcesso() == null || notaBean.getIdProcesso() == 0L)

            throw new ValidacaoException("Id do processo não informado.");

        if (notaBean.getIdUsuario() == null || notaBean.getIdUsuario() == 0L)

            throw new ValidacaoException("Id do usuário não informado.");

        final NotaDocumento notaDocumento = notaBean.getNotaDocumento();

        validaNotaDocumento(notaDocumento);

        final NotaMensagem notaMensagem = notaBean.getNotaMensagem();

        validaNotaMensagem(notaMensagem);

        try {

            final ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository.buscar(notaBean.getIdUsuario(), notaBean.getIdProcesso());

            if (processoUsuarioEntity == null)

                throw new ValidacaoException("Processo/Usuario não encontrado.");

            NotaEntity entity = new NotaEntity();

            entity.setDataCadastro(new Date());

            if (notaMensagem != null) {

                NotaTextoEntity notaTextoEntity = new NotaTextoEntity();

                notaTextoEntity.setDescricao(notaMensagem.getMensagem());
                notaTextoEntity.setTipo(notaMensagem.getTipo());

                entity.setNotaTextoByNotaTextoId(notaTextoEntity);
            }

            if (notaDocumento != null) {

                NotaDocumentoEntity documentoEntity = new NotaDocumentoEntity();

                documentoEntity.setArquivo(notaDocumento.getArquivo());
                documentoEntity.setDescricao(notaDocumento.getDescricao());
                documentoEntity.setFormarto(notaDocumento.getFormato());
                documentoEntity.setNome(notaDocumento.getNome());

                entity.setNotaDocumentoByNotaDocumentoId(documentoEntity);
            }

            entity.setProcessoUsuarioByProcessoUsuarioId(processoUsuarioEntity);

            notaRepository.salvar(entity);

        } catch (PersistenceException e) {
            throw new InfraestruturaException(e.getMessage(), e);
        }

    }

    private void validaNotaMensagem(NotaMensagem notaMensagem) throws ValidacaoException {

        if (notaMensagem != null) {

            if (StringUtils.isBlank(notaMensagem.getMensagem()))

                throw new ValidacaoException("Mensagem não informado.");

            if (notaMensagem.getTipo() == 0)

                throw new ValidacaoException("Mensagem não informado.");
        }
    }

    private void validaNotaDocumento(NotaDocumento notaDocumento) throws ValidacaoException {

        if (notaDocumento != null) {

            if (StringUtils.isBlank(notaDocumento.getFormato()))

                throw new ValidacaoException("Formato do arquivo não informado.");

            if (StringUtils.isBlank(notaDocumento.getNome()))

                throw new ValidacaoException("Nome do arquivo não informado.");

            if (StringUtils.isBlank(notaDocumento.getDescricao()))

                throw new ValidacaoException("Descrição do arquivo não informado.");

        }
    }

    @Transactional
    public void salvarAndamento(NotaAndamento andamentoBean) throws ValidacaoException, InfraestruturaException {

        if (StringUtils.isBlank(andamentoBean.getDescricao()))
            throw new ValidacaoException("Andamento não informado.");

        try {

            NotaTextoEntity notaTextoEntity = new NotaTextoEntity();

            notaTextoEntity.setTipo(Consts.TIPO_ANDAMENTO_PROCESSO);
            notaTextoEntity.setDescricao(andamentoBean.getDescricao().toUpperCase());

            final ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository
                    .buscar(andamentoBean.getIdUsuario(), andamentoBean.getIdProcesso());

            if (processoUsuarioEntity == null)
                throw new ValidacaoException("Usuário não está autorizado a atualizar informações do processo.");

            NotaEntity notaEntity = new NotaEntity();

            notaEntity.setDataCadastro(new Date());
            notaEntity.setNotaTextoByNotaTextoId(notaTextoEntity);
            notaEntity.setProcessoUsuarioByProcessoUsuarioId(processoUsuarioEntity);

            notaTextoEntity.setNotaEntity(notaEntity);

            notaRepository.salvar(notaEntity);

        } catch (PersistenceException e) {
            throw new InfraestruturaException(e.getMessage(), e);
        }
    }

    public List<NotaAndamento> listarAndamentos(final Long idProcesso) throws InfraestruturaException, RecursoNaoEncontradoException, ValidacaoException {

        if (idProcesso == null)
            throw new ValidacaoException("Id do processo não informado.");

        final List<NotaEntity> entityLists = notaRepository.listarAndamentos(idProcesso, Consts.TIPO_ANDAMENTO_PROCESSO);

        if (entityLists == null || entityLists.isEmpty())

            throw new RecursoNaoEncontradoException("Lista de andamentos não encontrada");

        List<NotaAndamento> listAndamento = new ArrayList<>();

        for (NotaEntity entity : entityLists) {

            NotaAndamento notaAndamento = new NotaAndamento();

            NotaTextoEntity textoEntity = entity.getNotaTextoByNotaTextoId();

            notaAndamento.setDescricao(textoEntity.getDescricao());

            notaAndamento.setNota(entity.getId());
            notaAndamento.setDataCadastro(entity.getDataCadastro());
            ProcessoUsuarioEntity processoUsuarioId = entity.getProcessoUsuarioByProcessoUsuarioId();
            notaAndamento.setIdProcesso(processoUsuarioId.getUsuarioByUsuarioId().getId());
            notaAndamento.setIdProcesso(processoUsuarioId.getProcessoByProcessoId().getId());

            listAndamento.add(notaAndamento);

        }

        return listAndamento;

    }

    public List<NotaDocumento> listarDocumentos(final Long idProcesso) throws InfraestruturaException, RecursoNaoEncontradoException, ValidacaoException {

        if (idProcesso == null)
            throw new ValidacaoException("Id do processo não informado.");

        final List<NotaEntity> entityLists = notaRepository.listarDocumentos(idProcesso);

        if (entityLists == null || entityLists.isEmpty())

            throw new RecursoNaoEncontradoException("Lista de documentos não encontrada");

        List<NotaDocumento> lista = new ArrayList<>();

        for (NotaEntity entity : entityLists) {

            NotaDocumento notaDocumento = new NotaDocumento();

            NotaDocumentoEntity notaDocumentoEntity = entity.getNotaDocumentoByNotaDocumentoId();

            notaDocumento.setIdDocumento(notaDocumentoEntity.getId());
            notaDocumento.setDescricao(notaDocumentoEntity.getDescricao());
            notaDocumento.setNome(notaDocumentoEntity.getNome());
            notaDocumento.setFormato(notaDocumento.getFormato());

            notaDocumento.setDataCadastro(entity.getDataCadastro());
            ProcessoUsuarioEntity processoUsuarioId = entity.getProcessoUsuarioByProcessoUsuarioId();
            notaDocumento.setIdProcesso(processoUsuarioId.getUsuarioByUsuarioId().getId());
            notaDocumento.setIdProcesso(processoUsuarioId.getProcessoByProcessoId().getId());

            lista.add(notaDocumento);

        }

        return lista;

    }

    @Transactional
    public void salvarDocumento(NotaDocumento notaDocumento) throws ValidacaoException, InfraestruturaException {

        if (StringUtils.isBlank(notaDocumento.getDescricao()))
            throw new ValidacaoException("Descrição não informado.");

        if (StringUtils.isBlank(notaDocumento.getFormato()))
            throw new ValidacaoException("Formato não informado.");

        if (notaDocumento.getArquivo() == null)
            throw new ValidacaoException("Arquivo não informado.");

        if (notaDocumento.getIdProcesso() == null)
            throw new ValidacaoException("Id do processo não informado.");

        if (notaDocumento.getIdUsuario() == null)
            throw new ValidacaoException("Id do usuário não informado.");

        try {

            NotaDocumentoEntity notaDocumentoEntity = new NotaDocumentoEntity();

            notaDocumentoEntity.setDescricao(notaDocumento.getDescricao().toUpperCase());
            notaDocumentoEntity.setNome(notaDocumento.getNome());
            notaDocumentoEntity.setFormarto(notaDocumento.getFormato());
            notaDocumentoEntity.setArquivo(notaDocumento.getArquivo());

            final ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository
                    .buscar(notaDocumento.getIdUsuario(), notaDocumento.getIdProcesso());

            if (processoUsuarioEntity == null)
                throw new ValidacaoException("Usuário não está autorizado a atualizar informações do processo.");

            NotaEntity notaEntity = new NotaEntity();

            notaEntity.setDataCadastro(new Date());
            notaEntity.setNotaDocumentoByNotaDocumentoId(notaDocumentoEntity);
            notaEntity.setProcessoUsuarioByProcessoUsuarioId(processoUsuarioEntity);

            notaDocumentoEntity.setNotaEntity(notaEntity);

            notaRepository.salvar(notaEntity);

        } catch (PersistenceException e) {
            throw new InfraestruturaException(e.getMessage(), e);
        }
    }
}
