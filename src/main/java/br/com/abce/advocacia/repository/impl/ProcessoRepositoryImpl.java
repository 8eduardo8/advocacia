package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.repository.ProcessoRepository;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import java.util.Date;
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
                            "  and pu.usuarioByUsuarioId.id = :id_usuario " +
                            "  and pu.dataExclusao is null " +
                            "order by p.dataAtualizacao, p.dataCadastro desc ")
                    .setParameter("id_usuario", idUsuario)
                    .getResultList();

        } catch (PersistenceException ex){
            throw new InfraestruturaException(ex.getMessage());
        }
    }

    @Override
    public ProcessoEntity buscarPorNumProcesso(final String numProcesso) throws InfraestruturaException {

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

    @Override
    public List<ProcessoEntity> listar(final String filtro, final Integer situacao, final Date dataInicio, final Long usuarioId) throws InfraestruturaException {
        try {

            return getEntityManager().createQuery(
                    "select p " +
                            "from ProcessoEntity p," +
                            "     ProcessoUsuarioEntity pu " +
                            "where p.dataExclusao is null " +
                            "  and p.id = pu.processoByProcessoId.id" +
                            "  and (:filtro is null or p.numero like :filtro )" +
                            "  and (:situacao is null or p.situacao = :situacao )" +
                            "  and (:dataInicio is null or p.dataInicio >= :dataInicio) " +
                            "  and (:usuarioId is null or pu.usuarioByUsuarioId.id = :usuarioId) " +
                            "order by p.dataAtualizacao, p.dataCadastro desc  ")
                    .setParameter("filtro", StringUtils.isNotBlank(filtro) ? "%" +filtro+ "%": null)
                    .setParameter("situacao", situacao)
                    .setParameter("dataInicio", dataInicio)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();

        } catch (PersistenceException ex){
            throw new InfraestruturaException(ex.getMessage());
        }
    }
}