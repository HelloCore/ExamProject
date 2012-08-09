package th.ac.kbu.cs.ExamProject.Common.Dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BasicFinderDao {
	 
	<E> E findUnique(String queryString);
	<E> E findUnique(String queryString, Object value);
	<E> E findUnique(String queryString, Object[] values);
    
    <E> List<E> findByExample(E exampleEntity);
    <E> List<E> findByExample(E exampleEntity, int firstResult, int maxResults);
    
    <E> E findUniqueByExample(E exampleEntity);
    
    <E> List<E> find(String queryString);
	<E> List<E> find(String queryString, Object value);
	<E> List<E> find(String queryString, Object[] values);
    
    <E> List<E> findByNamedParam(String queryString, String[] paramNames, Object[] values);
    <E> List<E> findByNamedParam(String queryString, String paramName, Object value);
    
    <E> E findUniqueByNamedParam(String queryString, String[] paramNames, Object[] values);
    <E> E findUniqueByNamedParam(String queryString, String paramName, Object value);
    
    <E> List<E> findByNamedQuery(String queryName);
    <E> List<E> findByNamedQuery(String queryName, Object value);
    <E> List<E> findByNamedQuery(String queryName, Object[] values);
    
    <E> E findUniqueByNamedQuery(String queryName);
    <E> E findUniqueByNamedQuery(String queryName, Object value);
    <E> E findUniqueByNamedQuery(String queryName, Object[] values);
    
    <E> List<E> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
    <E> List<E> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);
    <E> List<E> findByNamedQueryAndValueBean(String queryName, Object valueBean);
    
    <E> E findUniqueByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
    <E> E findUniqueByNamedQueryAndNamedParam(String queryName, String paramName, Object value);
    <E> E findUniqueByNamedQueryAndValueBean(String queryName, Object valueBean);
    
    <E> List<E> findByValueBean(String queryString, Object valueBean);

    <E> E findUniqueByValueBean(String queryString, Object valueBean);
    
    <E> List<E> findByCriteria(DetachedCriteria detachedCriteria);
    <E> List<E> findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults);

    <E> E findUniqueByCriteria(DetachedCriteria detachedCriteria);
    <E> E get(Class<E> entityClass,Serializable id);
}
