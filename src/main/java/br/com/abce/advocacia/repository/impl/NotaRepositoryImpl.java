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
            throw new InfraestruturaException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<NotaEntity> listarAndamentos(final Long idProcesso, final int tipo) throws InfraestruturaException {

        try {

            return getEntityManager().createQuery(
                    "select n from NotaEntity n" +
                            "         where n.notaTextoByNotaTextoId.tipo = :tipo" +
                            "           and n.processoUsuarioByProcessoUsuarioId.processoByProcessoId.id = :id_processo" +
                            "        order by n.dataCadastro asc ")
                    .setParameter("id_processo", idProcesso)
                    .setParameter("tipo", tipo)
                    .getResultList();

        } catch (PersistenceException ex) {
            throw new InfraestruturaException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<NotaEntity>  listarDocumentos(final Long idProcesso) throws InfraestruturaException {
        try {

            return getEntityManager().createQuery(
                    "select n from NotaEntity n " +
                            "         where n.processoUsuarioByProcessoUsuarioId.processoByProcessoId.id = :id_processo" +
                            "           and n.notaDocumentoByNotaDocumentoId.id is not null " +
                            "        order by n.dataCadastro asc ")
                    .setParameter("id_processo", idProcesso)
                    .getResultList();

        } catch (PersistenceException ex) {
            throw new InfraestruturaException(ex.getMessage(), ex);
        }
    }
}
