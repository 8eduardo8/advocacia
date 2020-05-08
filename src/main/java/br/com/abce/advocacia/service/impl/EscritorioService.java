package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.entity.EnderecoEntity;
import br.com.abce.advocacia.entity.EscritorioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.EscritorioRepository;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Util;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EscritorioService implements Serializable {

    @Inject
    private EscritorioRepository escritorioRepository;

    @Inject
    private Util util;

    @Transactional
    public void salvar(final EscritorioBean escritorioBean) throws InfraestruturaException, ValidacaoException {


        if (StringUtils.isBlank(escritorioBean.getRazao()))
            throw new ValidacaoException("Razão social não informada.");

        if (StringUtils.isBlank(escritorioBean.getCnpj()))
            throw new ValidacaoException("CNPJ não informada.");

        if (!util.isCNPJ(escritorioBean.getCnpj()))
            throw new ValidacaoException("CNPJ inválido.");

        if (StringUtils.isBlank(escritorioBean.getFantasia()))
            throw new ValidacaoException("Nome Fantasia não informado.");

        try {

            EscritorioEntity escritorioEntity = new EscritorioEntity();
            escritorioEntity.setId(escritorioBean.getId());
            escritorioEntity.setCnpj(util.trataParametro(escritorioBean.getCnpj()));
            escritorioEntity.setRazaoSocial(util.trataParametro(escritorioBean.getRazao()));
            escritorioEntity.setNomeFantasia(util.trataParametro(escritorioBean.getFantasia()));
            escritorioEntity.setSituacao(escritorioBean.isAtivo() ? Consts.REGISTRO_ATIVO : Consts.REGISTRO_INATIVO);

            EnderecoEntity entity = new EnderecoEntity();
            entity.setId(escritorioBean.getEnderecoBean().getId());
            entity.setCep(escritorioBean.getEnderecoBean().getCep());
            entity.setCidade(util.trataParametro(escritorioBean.getEnderecoBean().getCidade()));
            entity.setComplemento(util.trataParametro(escritorioBean.getEnderecoBean().getComplemento()));
            entity.setBairro(util.trataParametro(escritorioBean.getEnderecoBean().getBairro()));
            entity.setLogradouro(util.trataParametro(escritorioBean.getEnderecoBean().getLogradouro()));
            entity.setNumero(util.trataParametro(escritorioBean.getEnderecoBean().getNumero()));
            entity.setUf(util.trataParametro(escritorioBean.getEnderecoBean().getUf()));

            escritorioEntity.setEnderecoByEnderecoId(entity);

            if (isNovoEscritorio(escritorioEntity.getId())) {
                escritorioEntity.setDataCadastro(new Date());
                escritorioRepository.salvar(escritorioEntity);
            } else {
                escritorioEntity.setDataAtualizacao(new Date());
                escritorioRepository.editar(escritorioEntity);
            }

        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new InfraestruturaException(e.getMessage());
        }
    }

    private boolean isNovoEscritorio(Long idEscritorio) {
        return idEscritorio == null;
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

        bean.setId(entity.getId());
        bean.setCnpj(entity.getCnpj());
        bean.setRazao(entity.getRazaoSocial());
        bean.setDataCadastro(entity.getDataCadastro());
        bean.setFantasia(entity.getNomeFantasia());
        bean.setDataAtualizacao(entity.getDataAtualizacao());
        bean.setDataExclusao(entity.getDataExclusao());
        bean.setAtivo(entity.getSituacao() == Consts.REGISTRO_ATIVO);

        EnderecoEntity enderecoEntity = entity.getEnderecoByEnderecoId();

        bean.getEnderecoBean().setCep(String.valueOf(enderecoEntity.getCep()));
        bean.getEnderecoBean().setCidade(enderecoEntity.getCidade());
        bean.getEnderecoBean().setComplemento(enderecoEntity.getComplemento());
        bean.getEnderecoBean().setBairro(enderecoEntity.getBairro());
        bean.getEnderecoBean().setNumero(enderecoEntity.getNumero());
        bean.getEnderecoBean().setLogradouro(enderecoEntity.getLogradouro());
        bean.getEnderecoBean().setUf(enderecoEntity.getUf());
        bean.getEnderecoBean().setId(enderecoEntity.getId());

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

    public List<EscritorioBean> listar(final Long idUsuario) throws RecursoNaoEncontradoException, InfraestruturaException, ValidacaoException {

        final List<EscritorioEntity> entityList = (idUsuario != null && idUsuario > 0L)
                ? escritorioRepository.listar(idUsuario) : escritorioRepository.listar();

        if (entityList.isEmpty())
            throw new RecursoNaoEncontradoException("Lista de escritorio não encontrada para o usuário.");

        return getEscritorioBeans(entityList);
    }
}
