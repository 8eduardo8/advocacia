package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

public interface UsuarioRepository extends AbstractRepository<UsuarioEntity> {

    UsuarioEntity buscar(final String login) throws InfraestruturaException;

    UsuarioEntity buscarCpfCnpj(final String cpfCnpj) throws InfraestruturaException;
}
