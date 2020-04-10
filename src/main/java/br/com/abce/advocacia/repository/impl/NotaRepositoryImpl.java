package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.NotaEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.NotaRepository;

import javax.persistence.PersistenceException;
import java.util.List;

public class NotaRepositoryImpl extends AbstractRepositoryImpl<NotaEntity> implements NotaRepository {

    @Override
    public List<NotaEntity> listar(final Long idProcesso) throws InfraestruturaException {

        try {

            return getEntityManager().createQuery(
                    "select n " +
                            "from NotaEntity n, " +
                            "     ProcessoUsuarioEntity pu " +
                            "where n.processoUsuarioByProcessoUsuarioId.id = pu.id " +
                            "  and pu.processoByProcessoId.id = :id_processo")

                    .setParameter("id_processo", idProcesso)
                    .getResultList();

        } catch (PersistenceException ex) {
            throw new InfraestruturaException(ex.getMessage());
        }
    }
}
