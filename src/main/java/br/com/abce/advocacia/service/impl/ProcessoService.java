package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioResumidoBean;
import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.ProcessoRepository;
import br.com.abce.advocacia.repository.ProcessoUsuarioRepository;
import br.com.abce.advocacia.repository.UsuarioRepository;
import br.com.abce.advocacia.util.Consts;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessoService implements Serializable {

    @Inject
    private ProcessoRepository processoRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ProcessoUsuarioRepository processoUsuarioRepository;

    @Transactional
    public void salvar(final ProcessoBean processoBean) throws ValidacaoException {

        if (StringUtils.isBlank(processoBean.getArea()))
            throw new ValidacaoException("Área não informada.");

        if (StringUtils.isBlank(processoBean.getNumero()))
            throw new ValidacaoException("Número não informado.");

        if (StringUtils.isBlank(processoBean.getComarca()))
            throw new ValidacaoException("Comarca não informado.");

        ProcessoEntity entity = new ProcessoEntity();

        entity.setId(processoBean.getId());
        entity.setArea(processoBean.getArea());
        entity.setNumero(processoBean.getNumero());
        entity.setComarca(processoBean.getComarca());
        entity.setDataInicio(processoBean.getDataInicio());
        entity.setDataCadastro(processoBean.getDataCadastro());
        entity.setDataAtualizacao(processoBean.getDataCadastro());
        entity.setDataExclusao(processoBean.getDataExclusao());

        if (entity.getId() == null) {

            if (processoBean.getListaUsuarios() != null)
                for (UsuarioResumidoBean usuarioBean : processoBean.getListaUsuarios()) {
                    ProcessoUsuarioEntity processoUsuarioEntity = new ProcessoUsuarioEntity();
                    processoUsuarioEntity.setUsuarioByUsuarioId(usuarioRepository.buscar(usuarioBean.getId()));
                    processoUsuarioEntity.setDataCadastro(new Date());
                    processoUsuarioEntity.setProcessoByProcessoId(entity);
                    entity.getProcessoUsuariosById().add(processoUsuarioEntity);
                }

            entity.setDataCadastro(new java.util.Date());
            processoRepository.salvar(entity);

        } else {
            entity.setDataAtualizacao(new java.util.Date());
            processoRepository.editar(entity);

        }
    }

    public List<ProcessoBean> listar() throws RecursoNaoEncontradoException {

        final List<ProcessoEntity> listEntity = processoRepository.listar();

        if (listEntity.isEmpty())

            throw new RecursoNaoEncontradoException("Processo(s) não encontrado.");

        return  getProcessoBeans(listEntity);
    }

    private List<ProcessoBean> getProcessoBeans(final List<ProcessoEntity> listEntity) {

        List<ProcessoBean> processoBeanList = new ArrayList<>();

        for (ProcessoEntity entity : listEntity) {

            ProcessoBean bean = getProcessoBean(entity);

            processoBeanList.add(bean);
        }
        return processoBeanList;
    }

    private ProcessoBean getProcessoBean(final ProcessoEntity entity) {

        ProcessoBean bean = new ProcessoBean();

        bean.setId(entity.getId());
        bean.setArea(entity.getArea());
        bean.setDataInicio(entity.getDataInicio());
        bean.setNumero(entity.getNumero());
        bean.setComarca(entity.getComarca());
        bean.setSituacao(String.valueOf(entity.getSituacao()));
        bean.setDataCadastro(entity.getDataCadastro());
        bean.setDataAtualizacao(entity.getDataAtualizacao());
        bean.setDataExclusao(entity.getDataExclusao());

        bean.setListaUsuarios(getListaUsuarioResumido(entity));

        return bean;
    }

    public List<UsuarioResumidoBean> getListaUsuarioResumido(ProcessoEntity entity) {

        List<UsuarioResumidoBean> usuarioBeanList = new ArrayList<>();

        for (ProcessoUsuarioEntity processoUsuarioEntity : entity.getProcessoUsuariosById()) {

            if (processoUsuarioEntity.getDataExclusao() == null) {

                final UsuarioEntity usuarioEntity = processoUsuarioEntity.getUsuarioByUsuarioId();

                if (usuarioEntity != null)

                    usuarioBeanList.add(usuarioService.getUsuarioBean(usuarioEntity));

            }
        }

        return usuarioBeanList;
    }

    public ProcessoBean buscar(final Long id) throws RecursoNaoEncontradoException, ValidacaoException {

        if (id == null || id == 0L)

            throw new ValidacaoException(Consts.ID_PROCESSO_NAO_INFORMADO);

        final ProcessoEntity entity = processoRepository.buscar(id);

        if (entity == null)

            throw new RecursoNaoEncontradoException("Processo não encontrado.");

        return getProcessoBean(entity);
    }

    public List<ProcessoBean> listar(final Long idUsuario) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (idUsuario == null || idUsuario == 0L)

            throw new ValidacaoException(Consts.ID_USUARIO_NAO_INFORMADO);

        final List<ProcessoEntity> entityList = processoRepository.listar(idUsuario);

        if (entityList.isEmpty())

            throw new RecursoNaoEncontradoException("Lista de processos não encontrada para o usuário.");

        return getProcessoBeans(entityList);
    }

    public ProcessoBean buscarPorNumero(final String numProcesso) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (StringUtils.isBlank(numProcesso))

            throw new ValidacaoException("Nº do processo não informado.");

        final ProcessoEntity processoEntity = processoRepository.buscarPorNumProcesso(numProcesso);

        if (processoEntity == null)

            throw new RecursoNaoEncontradoException("Processo não encontrado.");

        return getProcessoBean(processoEntity);
    }

    @Transactional
    public void addUsuario(Long idUsuario, Long idProcesso) throws ValidacaoException {

        if (idUsuario == null || idUsuario == 0L)
            throw new ValidacaoException(Consts.ID_USUARIO_NAO_INFORMADO);

        if (idProcesso == null || idProcesso == 0L)
            throw new ValidacaoException("Id do processo não informado.");

        ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository.buscar(idUsuario, idProcesso);

        if (processoUsuarioEntity == null) {

            ProcessoEntity entity = processoRepository.buscar(idProcesso);

            processoUsuarioEntity = new ProcessoUsuarioEntity();
            processoUsuarioEntity.setUsuarioByUsuarioId(usuarioRepository.buscar(idUsuario));
            processoUsuarioEntity.setDataCadastro(new Date());
            processoUsuarioEntity.setProcessoByProcessoId(entity);

            processoUsuarioRepository.salvar(processoUsuarioEntity);

        } else {

            processoUsuarioEntity.setDataExclusao(null);
            processoUsuarioEntity.setDataAtualizacao(new Date());

            processoUsuarioRepository.editar(processoUsuarioEntity);
        }
    }

    @Transactional
    public void remover(Long idUsuario, Long idProcesso) throws ValidacaoException {

        if (idUsuario == null || idUsuario == 0L)
            throw new ValidacaoException(Consts.ID_USUARIO_NAO_INFORMADO);

        if (idProcesso == null || idProcesso == 0L)
            throw new ValidacaoException("Id do processo não informado.");

        ProcessoUsuarioEntity processoUsuarioEntity = processoUsuarioRepository.buscar(idUsuario, idProcesso);

        if (processoUsuarioEntity == null)

            throw new ValidacaoException("Usuário não consta na relação de envolvido do processo.");


        processoUsuarioEntity.setDataExclusao(new Date());

        processoUsuarioRepository.editar(processoUsuarioEntity);
    }

    public List<UsuarioResumidoBean> listarEnvolvidos(Long idProcesso) throws ValidacaoException, RecursoNaoEncontradoException {

            if (idProcesso == null)
                throw new ValidacaoException(Consts.ID_PROCESSO_NAO_INFORMADO);

        final ProcessoEntity entity = processoRepository.buscar(idProcesso);

        if (entity == null)

            throw new RecursoNaoEncontradoException("Processo não encontrado.");

        return getListaUsuarioResumido(entity);
    }
}
