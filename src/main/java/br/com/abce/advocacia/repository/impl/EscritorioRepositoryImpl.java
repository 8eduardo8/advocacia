package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.EscritorioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.EscritorioRepository;

import javax.persistence.PersistenceException;
import java.util.List;

public class EscritorioRepositoryImpl extends AbstractRepositoryImpl<EscritorioEntity> implements EscritorioRepository {

    @Override
    public List<EscritorioEntity> listar(final Long idUsuario) throws InfraestruturaException {

        try {

            return getEntityManager().createQuery(
                    "select e " +
                            "from EscritorioEntity e, " +
                            "     UsuarioEscritorioEntity ue " +
                            "where e.id = ue.escritorioByEscritorioId.id " +
                            " and ue.usuarioByUsuarioId.id = :id_usuario")
                    .setParameter("id_usuario", idUsuario)
                    .getResultList();

        } catch (PersistenceException ex){
            throw new InfraestruturaException(ex.getMessage());
        }
    }
}
