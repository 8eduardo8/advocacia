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

        List<ProcessoUsuarioEntity> processoUsuarioEntityList = new ArrayList<>();

        for (UsuarioResumidoBean usuarioBean : processoBean.getListaUsuarios()) {
            ProcessoUsuarioEntity processoUsuarioEntity = new ProcessoUsuarioEntity();
            processoUsuarioEntity.setUsuarioByUsuarioId(usuarioRepository.buscar(usuarioBean.getId()));
            processoUsuarioEntity.setDataCadastro(new Date());
            processoUsuarioEntity.setProcessoByProcessoId(entity);
            processoUsuarioEntityList.add(processoUsuarioEntity);
        }

        entity.setProcessoUsuariosById(processoUsuarioEntityList);

        if (entity.getId() == null) {
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

        List<UsuarioResumidoBean> usuarioBeanList = new ArrayList<>();

        for (ProcessoUsuarioEntity processoUsuarioEntity : entity.getProcessoUsuariosById()) {

            final UsuarioEntity usuarioEntity = processoUsuarioEntity.getUsuarioByUsuarioId();

            if (usuarioEntity != null)

                usuarioBeanList.add(usuarioService.getUsuarioBean(usuarioEntity));
        }

        bean.setListaUsuarios(usuarioBeanList);

        return bean;
    }

    public ProcessoBean buscar(final Long id) throws RecursoNaoEncontradoException, ValidacaoException {

        if (id == null || id == 0L)

            throw new ValidacaoException("Id do processo não informado.");

        final ProcessoEntity entity = processoRepository.buscar(id);

        if (entity == null)

            throw new RecursoNaoEncontradoException("Processo não encontrado.");

        return getProcessoBean(entity);
    }

    public List<ProcessoBean> listar(final Long idUsuario) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (idUsuario == null || idUsuario == 0L)

            throw new ValidacaoException("Id do usuário não informado.");

        final List<ProcessoEntity> entityList = processoRepository.listar(idUsuario);

        if (entityList.isEmpty())

            throw new RecursoNaoEncontradoException("Lista de processos não encontrada para o usuário.");

        return getProcessoBeans(entityList);
    }

    public ProcessoBean buscarPorNumero(final Long numProcesso) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (numProcesso == null || numProcesso == 0L)

            throw new ValidacaoException("Nº do processo não informado.");

        final ProcessoEntity processoEntity = processoRepository.buscarPorNumProcesso(numProcesso);

        if (processoEntity == null)

            throw new RecursoNaoEncontradoException("Processo não encontrado.");

        return getProcessoBean(processoEntity);
    }
}
