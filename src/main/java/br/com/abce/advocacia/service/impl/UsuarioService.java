package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.bean.UsuarioResumidoBean;
import br.com.abce.advocacia.entity.EnderecoEntity;
import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.repository.UsuarioRepository;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.Util;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioService implements Serializable {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private Util util;

    public List<UsuarioBean> listar() throws RecursoNaoEncontradoException {

        final List<UsuarioEntity> listaUsuario = usuarioRepository.listar();

        if (listaUsuario.isEmpty())

            throw new RecursoNaoEncontradoException("Usuário(s) não encontrados(s).");

        return (List<UsuarioBean>) getUsuarioBeanList(listaUsuario);
    }

    public List<UsuarioResumidoBean> listarResumido() throws RecursoNaoEncontradoException {

        final List<UsuarioEntity> listaUsuario = usuarioRepository.listar();

        if (listaUsuario.isEmpty())

            throw new RecursoNaoEncontradoException("Usuário(s) não encontrados(s).");

        return (List<UsuarioResumidoBean>) getUsuarioBeanList(listaUsuario);
    }

    public List<? extends UsuarioResumidoBean> getUsuarioBeanList(List<UsuarioEntity> listaUsuario) {

        List<UsuarioBean> listaUsuarioBeans = new ArrayList<>() ;

        for (UsuarioEntity usuarioEntity : listaUsuario) {

            final UsuarioBean bean = getUsuarioBean(usuarioEntity);

            listaUsuarioBeans.add(bean);
        }

        return listaUsuarioBeans;
    }

    public UsuarioBean getUsuarioBean(UsuarioEntity usuarioEntity) {

        UsuarioBean bean = new UsuarioBean();
        bean.setId(usuarioEntity.getId());
        bean.setAtivo(usuarioEntity.getSituacao() == Consts.REGISTRO_ATIVO);
        bean.setId(usuarioEntity.getId());
        bean.setCpf(usuarioEntity.getCpf());
        bean.setLogin(usuarioEntity.getLogin());
        bean.setEmail(usuarioEntity.getEmail());
        bean.setSenha(usuarioEntity.getSenha());
        bean.setSexo(usuarioEntity.getSexo());
        bean.setRecuperarSenha(usuarioEntity.getRecuperarSenha() == Consts.RECUPERACAO_SENHA);
        bean.setSenhaTemporaria(usuarioEntity.getSenhaProvisoria());
        bean.setPerfil(usuarioEntity.getPerfil());
        bean.setSobrenome(usuarioEntity.getSobreNome());
        bean.setNome(usuarioEntity.getNome());
        bean.setDataAtualizacao(usuarioEntity.getDataAtualizacao());
        bean.setDataCadastro(usuarioEntity.getDataCadastro());
        bean.setDataExclusao(usuarioEntity.getDataExclusao());
        bean.setTelefoneCelular(usuarioEntity.getTelefoneCelular());
        bean.setTelefoneFixo(usuarioEntity.getTelefoneFixo());

        EnderecoEntity enderecoEntity = usuarioEntity.getEnderecoByEnderecoId();

        // alterar bean para considerar o endereco como classe
        bean.getEnderecoBean().setCep(String.valueOf(enderecoEntity.getCep()));
        bean.getEnderecoBean().setCidade(enderecoEntity.getCidade());
        bean.getEnderecoBean().setComplemento(enderecoEntity.getComplemento());
        bean.getEnderecoBean().setBairro(enderecoEntity.getBairro());
        bean.getEnderecoBean().setNumero(enderecoEntity.getNumero());
        bean.getEnderecoBean().setLogradouro(enderecoEntity.getLogradouro());
        bean.getEnderecoBean().setUf(enderecoEntity.getUf());
        bean.getEnderecoBean().setId(enderecoEntity.getId());

        List<String> listaIdProcesso = new ArrayList<>();

        for (ProcessoUsuarioEntity entity : usuarioEntity.getProcessoUsuariosById()) {

            listaIdProcesso.add(entity.getProcessoByProcessoId().getNumero());
        }

        bean.setListaProcessoId(listaIdProcesso);

        return bean;
    }

    @Transactional
    public void salvar(final UsuarioBean usuarioBean) throws ValidacaoException, InfraestruturaException {

        validaCamposObrigatorios(usuarioBean);

        validaTamanhoCampos(usuarioBean);

        validaRegrasNegocio(usuarioBean);

        UsuarioEntity entity = new UsuarioEntity();
        EnderecoEntity enderecoEntity = new EnderecoEntity();

        entity.setId(usuarioBean.getId());
        entity.setSituacao(usuarioBean.isAtivo() ? Consts.REGISTRO_ATIVO : Consts.REGISTRO_INATIVO);
        entity.setCpf(usuarioBean.getCpf());
        entity.setDataCadastro(usuarioBean.getDataCadastro());
        entity.setEmail(usuarioBean.getEmail());
        entity.setLogin(usuarioBean.getLogin());
        entity.setNome(util.trataParametro(usuarioBean.getNome()));
        entity.setSobreNome(util.trataParametro(usuarioBean.getSobrenome()));
        entity.setPerfil(usuarioBean.getPerfil());
        entity.setSexo(usuarioBean.getSexo());
        entity.setTelefoneCelular(usuarioBean.getTelefoneCelular());
        entity.setTelefoneFixo(usuarioBean.getTelefoneFixo());
        entity.setSenha(usuarioBean.getSenha());

        enderecoEntity.setId(usuarioBean.getEnderecoBean().getId());
        enderecoEntity.setCep(usuarioBean.getEnderecoBean().getCep());
        enderecoEntity.setCidade(util.trataParametro(usuarioBean.getEnderecoBean().getCidade()));
        enderecoEntity.setComplemento(util.trataParametro(usuarioBean.getEnderecoBean().getComplemento()));
        enderecoEntity.setLogradouro(util.trataParametro(usuarioBean.getEnderecoBean().getLogradouro()));
        enderecoEntity.setNumero(util.trataParametro(usuarioBean.getEnderecoBean().getNumero()));
        enderecoEntity.setUf(util.trataParametro(usuarioBean.getEnderecoBean().getUf()));

        entity.setEnderecoByEnderecoId(enderecoEntity);


        if (isNovoUsuario(entity.getId())) {
            entity.setDataCadastro(new Date());
            usuarioRepository.salvar(entity);

        } else {
            entity.setDataAtualizacao(new Date());
            usuarioRepository.editar(entity);
        }
    }

    private boolean isNovoUsuario(Long entityId) {
        return entityId == null;
    }

    private void validaRegrasNegocio(UsuarioBean usuarioBean) throws InfraestruturaException, ValidacaoException {

        if (!util.isCPF(usuarioBean.getCpf()))
            throw new ValidacaoException("CPF inválido.");

        if (isNovoUsuario(usuarioBean.getId())) {

            UsuarioEntity usuarioEntity = usuarioRepository.buscar(usuarioBean.getLogin());

            if (usuarioEntity != null)
                throw new ValidacaoException("Login de usuário já utilizado.");

            usuarioEntity = usuarioRepository.buscarCpfCnpj(usuarioBean.getCpf());

            if (usuarioEntity != null)
                throw new ValidacaoException("CPF de usuário já cadastrado.");
        }
    }

    private void validaTamanhoCampos(final UsuarioBean usuarioBean) throws ValidacaoException {

        if (StringUtils.length(usuarioBean.getTelefoneCelular()) != 11
            || StringUtils.length(usuarioBean.getTelefoneCelular()) != 10)
            throw new ValidacaoException("Tefefone celular inválido! Deve conter de 10 à 11 dígitos. ");

        if (StringUtils.length(usuarioBean.getTelefoneFixo()) != 10)
            throw new ValidacaoException("Telefone fixo inválido! Deve conter 10 dígitos. Ex. 6239994575");

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

        if (StringUtils.isBlank(usuarioBean.getEnderecoBean().getCep()))
            throw new ValidacaoException("Cep não informado.");

        if (StringUtils.isBlank(usuarioBean.getEnderecoBean().getCidade()))
            throw new ValidacaoException("Cidade não informado.");

        if (StringUtils.isBlank(usuarioBean.getEnderecoBean().getUf()))
            throw new ValidacaoException("UF não informado.");
    }

    public UsuarioBean buscar(final Long id) throws RecursoNaoEncontradoException {

        final UsuarioEntity entity = usuarioRepository.buscar(id);

        if (entity == null)

            throw new RecursoNaoEncontradoException("Usuário não encontrado.");

        return getUsuarioBean(entity);
    }

    public UsuarioBean buscar(final String login) throws InfraestruturaException, RecursoNaoEncontradoException {

        final UsuarioEntity entity = usuarioRepository.buscar(login);

        if (entity == null)

            throw new RecursoNaoEncontradoException("Usuário não encontrado.");

        return  getUsuarioBean(entity);
    }

    @Transactional
    public void alterarSenha(UsuarioBean usuarioBean, String novaSenha, String senhaAtual, String confirmaSenha) throws ValidacaoException, InfraestruturaException {

        if (StringUtils.isNotBlank(novaSenha))
            throw new ValidacaoException("Nova senha não informada");

        if (StringUtils.isNotBlank(senhaAtual))
            throw new ValidacaoException("Senha atual não informada");

        if (StringUtils.isNotBlank(confirmaSenha))
            throw new ValidacaoException("Confimração de senha não informada");

        if (usuarioBean.isRecuperarSenha()) {

            if (!senhaAtual.equals(usuarioBean.getSenhaTemporaria()))
                throw new ValidacaoException("A senha digitada não confere com a sua senha provisória!");

        } else {

            if (!senhaAtual.equals(usuarioBean.getSenha())) {
                throw new ValidacaoException("A senha digitada não confere com a sua senha!");
            }

        }

        if (!novaSenha.equals(confirmaSenha)) {
            throw new ValidacaoException("Confirmação de senha não confere com a nova senha!");
        }

        usuarioBean.setSenha(novaSenha);

        if (usuarioBean.isRecuperarSenha()) {

            usuarioBean.setSenhaTemporaria(null);
            usuarioBean.setRecuperarSenha(false);
        }

        salvar(usuarioBean);
    }
}
