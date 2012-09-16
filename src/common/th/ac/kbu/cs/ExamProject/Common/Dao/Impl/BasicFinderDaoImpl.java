package th.ac.kbu.cs.ExamProject.Common.Dao.Impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Common.CustomHibernateDaoSupport;
import th.ac.kbu.cs.ExamProject.Common.Dao.BasicFinderDao;

@Repository
public class BasicFinderDaoImpl extends CustomHibernateDaoSupport implements
		BasicFinderDao {
	
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString) {
		return this.getHibernateTemplate().find(queryString);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object value) {
		return this.getHibernateTemplate().find(queryString, value);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object[] values) {
		return this.getHibernateTemplate().find(queryString, values);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByExample(T exampleEntity) {
		return this.getHibernateTemplate().findByExample(exampleEntity);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByExample(T exampleEntity, int firstResult,
			int maxResults) {
		return this.getHibernateTemplate().findByExample(exampleEntity,
				firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedParam(String queryString,
			String[] paramNames, Object[] values) {
		return this.getHibernateTemplate().findByNamedParam(queryString,
				paramNames, values);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedParam(String queryString, String paramName,
			Object value) {
		return this.getHibernateTemplate().findByNamedParam(queryString,
				paramName, value);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(String queryName) {
		return this.getHibernateTemplate().findByNamedQuery(queryName);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(String queryName, Object value) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(String queryName, Object[] values) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramNames, values);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) {
		return this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, paramName, value);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQueryAndValueBean(String queryName,
			Object valueBean) {
		return this.getHibernateTemplate().findByNamedQueryAndValueBean(
				queryName, valueBean);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByValueBean(String queryString, Object valueBean) {
		return this.getHibernateTemplate().findByValueBean(queryString,
				valueBean);
	}
	public <T> T get(Class<T> entityClass,Serializable id){
		return this.getHibernateTemplate().get(entityClass, id);
	}
	@SuppressWarnings("unchecked")
	public <T> T findUnique(String queryString) {
		return (T) ensureUniqureResult(find(queryString));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUnique(String queryString, Object value) {
		return (T) ensureUniqureResult(find(queryString, value));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUnique(String queryString, Object[] values) {
		return (T) ensureUniqureResult(find(queryString, values));
	}

	public <T> T findUniqueByExample(T exampleEntity) {
		return (T) ensureUniqureResult(findByExample(exampleEntity));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedParam(String queryString,
			String[] paramNames, Object[] values) {
		return (T) ensureUniqureResult(findByNamedParam(queryString,
				paramNames, values));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedParam(String queryString, String paramName,
			Object value) {
		return (T) ensureUniqureResult(findByNamedParam(queryString, paramName,
				value));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQuery(String queryName) {
		return (T) ensureUniqureResult(findByNamedQuery(queryName));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQuery(String queryName, Object value) {
		return (T) ensureUniqureResult(findByNamedQuery(queryName, value));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQuery(String queryName, Object[] values) {
		return (T) ensureUniqureResult(findByNamedQuery(queryName, values));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) {
		return (T) ensureUniqureResult(findByNamedQueryAndNamedParam(queryName,
				paramNames, values));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) {
		return (T) ensureUniqureResult(findByNamedQueryAndNamedParam(queryName,
				paramName, value));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQueryAndValueBean(String queryName,
			Object valueBean) {
		return (T) ensureUniqureResult(findByNamedQueryAndValueBean(queryName,
				valueBean));
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByValueBean(String queryString, Object valueBean) {
		return (T) ensureUniqureResult(findByValueBean(queryString, valueBean));
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria,
			int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria,
				firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByCriteria(DetachedCriteria detachedCriteria) {
		return (T) ensureUniqureResult(findByCriteria(detachedCriteria));
	}

	protected <T> T ensureUniqureResult(List<T> findedResults) {
		if (findedResults == null || findedResults.size() == 0) {
			return null;
		} else if (findedResults.size() == 1) {
			return findedResults.get(0);
		} else {
			throw new IncorrectResultSizeDataAccessException(1,
					findedResults.size());
		}
	}
	@Transactional(readOnly=true)
	public Session getCurrentSession(){
		return this.getSession(true);
	}
	
}
