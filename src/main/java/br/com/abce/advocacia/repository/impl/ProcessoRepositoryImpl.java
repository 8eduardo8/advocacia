package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.ProcessoRepository;

import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class ProcessoRepositoryImpl extends AbstractRepositoryImpl<ProcessoEntity> implements ProcessoRepository {

    @Override
    public List<ProcessoEntity> listar(Long idUsuario) throws InfraestruturaException {
        try {

            return getEntityManager().createQuery(
                    "select p " +
                            "from ProcessoEntity p, " +
                            "     ProcessoUsuarioEntity pu " +
                            "where p.id = pu.processoByProcessoId.id " +
                            "  and pu.usuarioByUsuarioId.id = :id_usuario")
                    .setParameter("id_usuario", idUsuario)
                    .getResultList();

        } catch (PersistenceException ex){
            throw new InfraestruturaException(ex.getMessage());
        }
    }

    @Override
    public ProcessoEntity buscarPorNumProcesso(final Long numProcesso) throws InfraestruturaException {

        ProcessoEntity processoEntity = null;

        try {

            processoEntity = (ProcessoEntity) getEntityManager()
                    .createQuery(
                    "select p " +
                            "from ProcessoEntity p " +
                            "where p.numero = :num_processo")
                    .setParameter("num_processo", numProcesso)
                    .getSingleResult();

        } catch (NonUniqueResultException ex) {
            //ignore
        } catch (PersistenceException ex){
            throw new InfraestruturaException(ex.getMessage());
        }

        return processoEntity;
    }
}