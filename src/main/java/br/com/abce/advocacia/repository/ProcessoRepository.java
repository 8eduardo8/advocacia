package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.ProcessoEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

import java.util.List;

public interface ProcessoRepository extends AbstractRepository<ProcessoEntity> {

    List<ProcessoEntity> listar(Long idUsuario) throws InfraestruturaException;

    ProcessoEntity buscarPorNumProcesso(String numProcesso) throws InfraestruturaException;
}
