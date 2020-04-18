package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.ProcessoUsuarioEntity;

public interface ProcessoUsuarioRepository extends AbstractRepository<ProcessoUsuarioEntity> {

    ProcessoUsuarioEntity buscar(Long idUsuario, Long idProcesso);
}
