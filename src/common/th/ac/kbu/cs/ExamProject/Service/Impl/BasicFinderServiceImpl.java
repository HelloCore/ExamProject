package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.ac.kbu.cs.ExamProject.Common.Dao.BasicFinderDao;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;

@Service
public class BasicFinderServiceImpl implements BasicFinderService
{
    private BasicFinderDao basicFinderDao;
    
    @Autowired
    public void setBasicFinderDao(BasicFinderDao basicFinderDao)
    {
        this.basicFinderDao = basicFinderDao;
    }
    
    public BasicFinderDao getBasicFinderDao()
    {
        return basicFinderDao;
    }

    
    public <T> List<T> find(String queryString)
    {
        return getBasicFinderDao().find(queryString);
    }

    
    public <T> List<T> find(String queryString, Object value)
    {
        return getBasicFinderDao().find(queryString, value);
    }

    
    public <T> List<T> find(String queryString, Object[] values)
    {
        return getBasicFinderDao().find(queryString, values);
    }

    
    public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria)
    {
        return getBasicFinderDao().findByCriteria(detachedCriteria);
    }

    
    public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults)
    {
        return getBasicFinderDao().findByCriteria(detachedCriteria, firstResult, maxResults);
    }

    
    public <T> List<T> findByExample(T exampleEntity)
    {
        return getBasicFinderDao().findByExample(exampleEntity);
    }

    
    public <T> List<T> findByExample(T exampleEntity, int firstResult, int maxResults)
    {
        return getBasicFinderDao().findByExample(exampleEntity, firstResult, maxResults);
    }

    
    public <T> List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values)
    {
        return getBasicFinderDao().findByNamedParam(queryString, paramNames, values);
    }

    
    public <T> List<T> findByNamedParam(String queryString, String paramName, Object value)
    {
        return getBasicFinderDao().findByNamedParam(queryString, paramName, value);
    }

    
    public <T> List<T> findByNamedQuery(String queryName)
    {
        return getBasicFinderDao().findByNamedQuery(queryName);
    }

    
    public <T> List<T> findByNamedQuery(String queryName, Object value)
    {
        return getBasicFinderDao().findByNamedQuery(queryName, value);
    }

    
    public <T> List<T> findByNamedQuery(String queryName, Object[] values)
    {
        return getBasicFinderDao().findByNamedQuery(queryName, values);
    }

    
    public <T> List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
    {
        return getBasicFinderDao().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    
    public <T> List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
    {
        return getBasicFinderDao().findByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    
    public <T> List<T> findByNamedQueryAndValueBean(String queryName, Object valueBean)
    {
        return getBasicFinderDao().findByNamedQueryAndValueBean(queryName, valueBean);
    }

    
    public <T> List<T> findByValueBean(String queryString, Object valueBean)
    {
        return getBasicFinderDao().findByValueBean(queryString, valueBean);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUnique(String queryString)
    {
        return (T) getBasicFinderDao().findUnique(queryString);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUnique(String queryString, Object value)
    {
        return (T) getBasicFinderDao().findUnique(queryString, value);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUnique(String queryString, Object[] values)
    {
        return (T) getBasicFinderDao().findUnique(queryString, values);
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueByCriteria(DetachedCriteria detachedCriteria)
    {
        return (T) getBasicFinderDao().findUniqueByCriteria(detachedCriteria);
    }

    
    public <T> T findUniqueByExample(T exampleEntity)
    {
        return getBasicFinderDao().findUniqueByExample(exampleEntity);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedParam(String queryString, String[] paramNames, Object[] values)
    {
        return (T) getBasicFinderDao().findUniqueByNamedParam(queryString, paramNames, values);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedParam(String queryString, String paramName, Object value)
    {
        return (T) getBasicFinderDao().findUniqueByNamedParam(queryString, paramName, value);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQuery(String queryName)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQuery(queryName);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQuery(String queryName, Object value)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQuery(queryName, value);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQuery(String queryName, Object[] values)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQuery(queryName, values);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByNamedQueryAndValueBean(String queryName, Object valueBean)
    {
        return (T) getBasicFinderDao().findUniqueByNamedQueryAndValueBean(queryName, valueBean);
    }

    @SuppressWarnings("unchecked")
    
    public <T> T findUniqueByValueBean(String queryString, Object valueBean)
    {
        return (T) getBasicFinderDao().findUniqueByValueBean(queryString, valueBean);
    }
    
    public <T> T get(Class<T> entityClass,Serializable id){
    	return (T) getBasicFinderDao().get(entityClass, id);
    }
}
