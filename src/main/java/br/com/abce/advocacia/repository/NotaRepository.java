package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.NotaEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

import java.util.List;

public interface NotaRepository extends AbstractRepository<NotaEntity> {

    List<NotaEntity> listar(Long idProcesso) throws InfraestruturaException;
}