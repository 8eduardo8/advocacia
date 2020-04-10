package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.entity.EscritorioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.EscritorioRepository;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Util;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EscritorioService implements Serializable {

    @Inject
    private EscritorioRepository escritorioRepository;

    @Inject
    private Util util;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void salvar(final EscritorioBean escritorioBean) throws InfraestruturaException {


        try {

            EscritorioEntity escritorioEntity = new EscritorioEntity();
            escritorioEntity.setCnpj(util.trataParametro(escritorioBean.getCnpj()));
            escritorioEntity.setRazaoSocial(util.trataParametro(escritorioBean.getRazao()));
            escritorioEntity.setNomeFantasia(util.trataParametro(escritorioBean.getFantasia()));
//            escritorioEntity.endereco = escritorioBean.endereco.toUpperCase().trim();

            escritorioRepository.salvar(escritorioEntity);

        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new InfraestruturaException(e.getMessage());
        }
    }

    public List<EscritorioBean> listar() throws RecursoNaoEncontradoException {


        final List<EscritorioEntity> entityList = escritorioRepository.listar();

        if (entityList.isEmpty())

            throw new RecursoNaoEncontradoException("Escritório(s) não encontrado.");

        return getEscritorioBeans(entityList);

    }

    private List<EscritorioBean> getEscritorioBeans(List<EscritorioEntity> entityList) {

        List<EscritorioBean> escritorioBeanList = new ArrayList<>();

        for (EscritorioEntity entity : entityList) {

            EscritorioBean bean = getEscritorioBean(entity);

            escritorioBeanList.add(bean);
        }
        return escritorioBeanList;
    }

    private EscritorioBean getEscritorioBean(EscritorioEntity entity) {

        EscritorioBean bean = new EscritorioBean();

        bean.setCnpj(entity.getCnpj());
        bean.setRazao(entity.getRazaoSocial());
        bean.setDataCadastro(entity.getDataCadastro());

        return bean;
    }

    public EscritorioBean buscar(final Long id) throws ValidacaoException, RecursoNaoEncontradoException {

        if (id == null || id == 0L)
            throw new ValidacaoException("Id do escritório não informado.");

        final EscritorioEntity entity = escritorioRepository.buscar(id);

        if (entity == null)
            throw new RecursoNaoEncontradoException("Escritório não encontrado.");

        return getEscritorioBean(entity);
    }

    public List<EscritorioBean> listar(final Long idUsuario) throws RecursoNaoEncontradoException, InfraestruturaException {

        final List<EscritorioEntity> entityList = (idUsuario != null || idUsuario > 0L)
                ? escritorioRepository.listar(idUsuario) : escritorioRepository.listar();

        if (entityList.isEmpty())
            throw new RecursoNaoEncontradoException("Lista de escritorio não encontrada para o usuário.");

        return getEscritorioBeans(entityList);
    }
}
