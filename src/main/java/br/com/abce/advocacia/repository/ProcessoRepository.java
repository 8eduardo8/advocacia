package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

import java.util.Date;
import java.util.List;

public interface ProcessoRepository extends AbstractRepository<ProcessoEntity> {

    List<ProcessoEntity> listar(Long idUsuario) throws InfraestruturaException;

    ProcessoEntity buscarPorNumProcesso(String numProcesso) throws InfraestruturaException;

    List<ProcessoEntity> listar(String filtro, Integer situacao, Date dataInicio, final Long usuarioId) throws InfraestruturaException;
}
