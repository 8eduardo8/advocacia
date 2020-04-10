package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.ProcessoRepository;
import br.com.abce.advocacia.repository.UsuarioRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProcessoService implements Serializable {

    @Inject
    private ProcessoRepository processoRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private UsuarioService usuarioService;

    public void salvar(final ProcessoBean processoBean) {

        ProcessoEntity entity = new ProcessoEntity();

        entity.setArea(processoBean.getArea());
        entity.setNumero(processoBean.getNumero());
        entity.setId(processoBean.getId());
        entity.setDataInicio(new Date(processoBean.getDataInicio().getTime()));

        List<ProcessoUsuarioEntity> processoUsuarioEntityList = new ArrayList<>();

        for (UsuarioBean usuarioBean : processoBean.getListaUsuarios()) {
            ProcessoUsuarioEntity processoUsuarioEntity = new ProcessoUsuarioEntity();
            processoUsuarioEntity.setProcessoByProcessoId(entity);
            processoUsuarioEntity.setUsuarioByUsuarioId(usuarioRepository.buscar((long) usuarioBean.getId()));
            processoUsuarioEntityList.add(processoUsuarioEntity);
        }

        entity.setProcessoUsuariosById(processoUsuarioEntityList);

        processoRepository.salvar(entity);

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

        bean.setArea(entity.getArea());
        bean.setDataInicio(entity.getDataInicio());
        bean.setNumero(entity.getNumero());
        bean.setSituacao(String.valueOf(entity.getSituacao()));

        List<UsuarioBean> usuarioBeanList = new ArrayList<>();

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
