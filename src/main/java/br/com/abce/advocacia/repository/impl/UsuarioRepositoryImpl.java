package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.UsuarioRepository;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

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
}
