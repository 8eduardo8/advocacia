/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.abce.advocacia.repository.impl;

import br.com.abce.advocacia.repository.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author alanb
 */
public abstract class AbstractRepositoryImpl<T> implements AbstractRepository<T> {

    @PersistenceContext(unitName = "advocacia")
    private EntityManager em;

    protected EntityManager getEntityManager(){
        return em;
    }

    public void salvar(T entity) {
        getEntityManager().persist(entity);
    }

    public void salvar(List<T> entity) {
        getEntityManager().persist(entity);
    }

    public void editar(List<T>  entity) {
        getEntityManager().merge(entity);
    }

    public void editar(T entity) {
        getEntityManager().merge(entity);
    }

    public void remover(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T buscar(final Long id) {
        return (T) getEntityManager().find(getGenericTypeClass(), id);
    }

    public void remover(final Long idEntity) {

        EntityManager entityManager = getEntityManager();

        T entity = (T) entityManager.getReference(getGenericTypeClass(), idEntity);

        entityManager.remove(entity);
    }

    public List<T> listar() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getGenericTypeClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getGenericTypeClass()));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments();

        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(getGenericTypeClass());
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
