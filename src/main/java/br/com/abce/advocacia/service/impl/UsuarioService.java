package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.entity.EnderecoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.UsuarioRepository;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService implements Serializable {

    @Inject
    private UsuarioRepository usuarioRepository;

    public List<UsuarioBean> listar() throws RecursoNaoEncontradoException {

        final List<UsuarioEntity> listaUsuario = usuarioRepository.listar();

        if (listaUsuario.isEmpty())

            throw new RecursoNaoEncontradoException("Usuário(s) não encontrados(s).");

        return getUsuarioBeanList(listaUsuario);
    }

    public List<UsuarioBean> getUsuarioBeanList(List<UsuarioEntity> listaUsuario) {

        List<UsuarioBean> listaUsuarioBeans = new ArrayList<>() ;

        for (UsuarioEntity usuarioEntity : listaUsuario) {

            final UsuarioBean bean = getUsuarioBean(usuarioEntity);

            listaUsuarioBeans.add(bean);
        }

        return listaUsuarioBeans;
    }

    public UsuarioBean getUsuarioBean(UsuarioEntity usuarioEntity) {

        UsuarioBean bean = new UsuarioBean();
        bean.setAtivo(usuarioEntity.getSituacao() == 1);
        bean.setId(usuarioEntity.getId());
        bean.setCpf(usuarioEntity.getCpf());
        bean.setLogin(usuarioEntity.getLogin());
        bean.setEmail(usuarioEntity.getEmail());
        bean.setSenha(usuarioEntity.getSenha());
//            bean.setPerfil(usuarioEntity.getPerfil());
        bean.setSobrenome(usuarioEntity.getSobreNome());
        bean.setNome(usuarioEntity.getNome());
        bean.setDataAtualizacao(usuarioEntity.getDataAtualizacao());
        bean.setDataCadastro(usuarioEntity.getDataCadastro());
        bean.setDataExclusao(usuarioEntity.getDataExclusao());

        EnderecoEntity enderecoEntity = usuarioEntity.getEnderecoByEnderecoId();

        // alterar bean para considerar o endereco como classe
        bean.setCep(String.valueOf(enderecoEntity.getCep()));
        bean.setCidade(enderecoEntity.getCidade());
        bean.setEndereco(enderecoEntity.getLogradouro());

        List<String> listaIdProcesso = new ArrayList<>();

        for (ProcessoUsuarioEntity entity : usuarioEntity.getProcessoUsuariosById()) {

            listaIdProcesso.add(entity.getProcessoByProcessoId().getNumero());
        }

        bean.setListaProcessoId(listaIdProcesso);

        return bean;
    }

    public void salvar(final UsuarioBean usuarioBean) throws ValidacaoException {

        validaCamposObrigatorios(usuarioBean);

        validaTamanhoCampos(usuarioBean);

        UsuarioEntity entity = new UsuarioEntity();

        entity.setCpf(usuarioBean.getCpf());
        entity.setDataCadastro(usuarioBean.getDataCadastro());
        entity.setEmail(usuarioBean.getEmail());
        entity.setLogin(usuarioBean.getLogin());
        entity.setNome(usuarioBean.getNome());
//        entity.setPerfil(usu);
        entity.setSexo(usuarioBean.getSexo());
        entity.setSenha(usuarioBean.getSenha());

        usuarioRepository.salvar(entity);
    }

    private void validaTamanhoCampos(final UsuarioBean usuarioBean) {

    }

    private void validaCamposObrigatorios(final UsuarioBean usuarioBean) throws ValidacaoException {

        if (StringUtils.isBlank(usuarioBean.getCpf()))
            throw new ValidacaoException("CPF não informado.");

        if (StringUtils.isBlank(usuarioBean.getNome()))
            throw new ValidacaoException("Nome não informado.");

        if (StringUtils.isBlank(usuarioBean.getSobrenome()))
            throw new ValidacaoException("Sobrenome não informado.");

        if (StringUtils.isBlank(usuarioBean.getEmail()))
            throw new ValidacaoException("E-mail não informado.");

        if (StringUtils.isBlank(usuarioBean.getLogin()))
            throw new ValidacaoException("Login não informado.");

        if (StringUtils.isBlank(usuarioBean.getSexo()))
            throw new ValidacaoException("Sexo não informado.");

        if (StringUtils.isBlank(usuarioBean.getSenha()))
            throw new ValidacaoException("Senha não informada.");

        if (StringUtils.isBlank(usuarioBean.getCep()))
            throw new ValidacaoException("Cep não informado.");

        if (StringUtils.isBlank(usuarioBean.getCidade()))
            throw new ValidacaoException("Cidade não informado.");

        if (StringUtils.isBlank(usuarioBean.getUf()))
            throw new ValidacaoException("UF não informado.");
    }

    public UsuarioBean buscar(final Long id) {

        UsuarioBean usuarioBean = null;

        final UsuarioEntity entity = usuarioRepository.buscar(id);

        if (entity != null) {

            usuarioBean = getUsuarioBean(entity);
        }

        return usuarioBean;
    }

    public UsuarioBean buscar(final String login) throws InfraestruturaException {

        UsuarioBean usuarioBean = null;

        final UsuarioEntity entity = usuarioRepository.buscar(login);

        if (entity != null) {

            usuarioBean = getUsuarioBean(entity);
        }

        return usuarioBean;
    }
}
