package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.NotaBean;
import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.bean.NotaMensagem;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.entity.NotaDocumentoEntity;
import br.com.abce.advocacia.entity.NotaEntity;
import br.com.abce.advocacia.entity.NotaTextoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.NotaRepository;
import br.com.abce.advocacia.repository.ProcessoUsuarioRepository;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
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

        ProcessoBean processoBean = processoService.buscar(idProcesso);

        final List<NotaEntity> notaEntityList = notaRepository.listar((long) processoBean.getId());

        if (notaEntityList.isEmpty())

            throw new RecursoNaoEncontradoException("Nota(s) não encontrada(s)");

        List<NotaBean> notaBeanList = new ArrayList<>();

        for (NotaEntity entity : notaEntityList) {

            NotaBean bean = new NotaBean();

            bean.setDataCadastro(entity.getDataCadastro());
            bean.setId(entity.getId().intValue());
            bean.setIdUsuario((long) entity.getProcessoUsuarioByProcessoUsuarioId().getUsuarioByUsuarioId().getId());

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

    public void registrarNota(NotaBean notaBean) throws ValidacaoException {

        if (notaBean.getIdProcesso() == null || notaBean.getIdProcesso() == 0L)

            throw new ValidacaoException("Id do processo não informado.");

        if (notaBean.getIdUsuario() == null || notaBean.getIdUsuario() == 0L)

            throw new ValidacaoException("Id do usuário não informado.");

        if (notaBean.getIdProcessoUsuario() == null || notaBean.getIdProcessoUsuario() == 0L)

            throw new ValidacaoException("Id do processo/usuário não informado.");

        final NotaDocumento notaDocumento = notaBean.getNotaDocumento();

        validaNotaDocumento(notaDocumento);

        final NotaMensagem notaMensagem = notaBean.getNotaMensagem();

        validaNotaMensagem(notaMensagem);

        final ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository.buscar(notaBean.getIdProcessoUsuario());

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
}
