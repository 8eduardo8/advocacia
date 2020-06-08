package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.UsuarioRepository;
import br.com.abce.advocacia.util.Consts;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class UsuarioRepositoryImpl extends AbstractRepositoryImpl<UsuarioEntity> implements UsuarioRepository {

    @Override
    public UsuarioEntity buscar(final String login) throws InfraestruturaException {

        UsuarioEntity usuarioEntity = null;

        if (StringUtils.isNotBlank(login)) {

            try {

                usuarioEntity = (UsuarioEntity) getEntityManager().createQuery("select u from UsuarioEntity u where u.login = :login")
                        .setParameter("login", login)
                        .getSingleResult();

            } catch (NoResultException ex) {
                //ignore
            } catch (PersistenceException ex){
                throw new InfraestruturaException(ex.getMessage());
            }
        }

        return usuarioEntity;
    }

    @Override
    public UsuarioEntity buscarCpfCnpj(final String cpfCnpj) throws InfraestruturaException {

        UsuarioEntity usuarioEntity = null;

        if (StringUtils.isNotBlank(cpfCnpj)) {

            try {

                usuarioEntity = (UsuarioEntity) getEntityManager().createQuery("select u from UsuarioEntity u where u.cpf = :cpf")
                        .setParameter("cpf", cpfCnpj)
                        .getSingleResult();

            } catch (NoResultException ex) {
                //ignore
            } catch (PersistenceException ex){
                throw new InfraestruturaException(ex.getMessage());
            }
        }

        return usuarioEntity;
    }

    @Override
    public List<UsuarioEntity> listar(String filtro, int perfil, boolean ativo) throws InfraestruturaException {

        try {

            List<UsuarioEntity> usuarioEntity =
                    (List<UsuarioEntity>) getEntityManager().createQuery(
                            "select u from UsuarioEntity u " +
                                    "where (:perfil is null or u.perfil = :perfil) " +
                                    "  and (:ativo is null or :ativo = u.situacao) " +
                                    "  and (:filtro is null or concat(concat(upper(u.nome), ' '), u.sobreNome) like upper(:filtro)) ")
                            .setParameter("perfil", perfil)
                            .setParameter("ativo", ativo ? Consts.REGISTRO_ATIVO : Consts.REGISTRO_INATIVO)
                            .setParameter("filtro", StringUtils.isNotBlank(filtro) ? "%" + filtro + "%" : null)
                            .getResultList();

            return usuarioEntity;
        } catch (PersistenceException ex) {
            throw new InfraestruturaException(ex.getMessage());
        }
    }
}
