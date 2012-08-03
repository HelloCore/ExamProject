package th.ac.kbu.cs.ExamProject.Service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BasicFinderService
{
    <T> List<T> find(String queryString);
    <T> List<T> find(String queryString, Object value);
    <T> List<T> find(String queryString, Object[] values);
    
    <T> T findUnique(String queryString);
    <T> T findUnique(String queryString, Object value);
    <T> T findUnique(String queryString, Object[] values);
    
    <T> List<T> findByExample(T exampleEntity);
    <T> List<T> findByExample(T exampleEntity, int firstResult, int maxResults);
    
    <T> T findUniqueByExample(T exampleEntity);
    
    <T> List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values);
    <T> List<T> findByNamedParam(String queryString, String paramName, Object value);
    
    <T> T findUniqueByNamedParam(String queryString, String[] paramNames, Object[] values);
    <T> T findUniqueByNamedParam(String queryString, String paramName, Object value);
    
    <T> List<T> findByNamedQuery(String queryName);
    <T> List<T> findByNamedQuery(String queryName, Object value);
    <T> List<T> findByNamedQuery(String queryName, Object[] values);
    
    <T> T findUniqueByNamedQuery(String queryName);
    <T> T findUniqueByNamedQuery(String queryName, Object value);
    <T> T findUniqueByNamedQuery(String queryName, Object[] values);
    
    <T> List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
    <T> List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);
    <T> List<T> findByNamedQueryAndValueBean(String queryName, Object valueBean);
    
    <T> T findUniqueByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
    <T> T findUniqueByNamedQueryAndNamedParam(String queryName, String paramName, Object value);
    <T> T findUniqueByNamedQueryAndValueBean(String queryName, Object valueBean);
    
    <T> List<T> findByValueBean(String queryString, Object valueBean);

    <T> T findUniqueByValueBean(String queryString, Object valueBean);
    
    <T> List<T> findByCriteria(DetachedCriteria detachedCriteria);
    <T> List<T> findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults);
    
    <T> T findUniqueByCriteria(DetachedCriteria detachedCriteria);
}