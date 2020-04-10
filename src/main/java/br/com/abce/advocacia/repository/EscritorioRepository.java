package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.EscritorioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

import java.util.List;

public interface EscritorioRepository extends AbstractRepository<EscritorioEntity> {

    List<EscritorioEntity> listar(Long idUsuario) throws InfraestruturaException;
}
