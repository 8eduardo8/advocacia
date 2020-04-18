package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;
import br.com.abce.advocacia.repository.ProcessoUsuarioRepository;

import javax.persistence.NoResultException;

public class ProcessoUsuarioRepositoryImpl extends AbstractRepositoryImpl<ProcessoUsuarioEntity>  implements ProcessoUsuarioRepository {


    @Override
    public ProcessoUsuarioEntity buscar(Long idUsuario, Long idProcesso) {

        ProcessoUsuarioEntity processoUsuarioEntity = null;

        try {
            processoUsuarioEntity = (ProcessoUsuarioEntity) getEntityManager()
                    .createQuery(
                            "select pu from ProcessoUsuarioEntity pu " +
                                    "where pu.processoByProcessoId.id = :id_processo" +
                                    "  and pu.usuarioByUsuarioId.id = :id_usuario")
                    .setParameter("id_usuario", idUsuario)
                    .setParameter("id_processo", idProcesso)
                    .getSingleResult();

        } catch (NoResultException ex){
            // ignore
        }

        return processoUsuarioEntity;
    }
}
