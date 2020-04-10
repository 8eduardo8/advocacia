package br.com.abce.advocacia.repository;

import java.util.List;

public interface AbstractRepository<T> {

    void salvar(T entity);

    void editar(T entity);

    void remover(T entity);

    T buscar(final Long id);

    void remover(final Long idEntity);

    List<T> listar();

    List<T> findRange(int[] range);

    int count();
}
