package br.com.abce.advocacia.repository;

import br.com.abce.advocacia.entity.UsuarioEntity;
import br.com.abce.advocacia.exceptions.InfraestruturaException;

import java.util.List;

public interface UsuarioRepository extends AbstractRepository<UsuarioEntity> {

    UsuarioEntity buscar(final String login) throws InfraestruturaException;

    UsuarioEntity buscarCpfCnpj(final String cpfCnpj) throws InfraestruturaException;

    List<UsuarioEntity> listar(final String filtro, final int perfil, final boolean ativo) throws InfraestruturaException;
}
