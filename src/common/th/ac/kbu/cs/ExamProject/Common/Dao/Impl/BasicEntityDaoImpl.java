package th.ac.kbu.cs.ExamProject.Common.Dao.Impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Common.CustomHibernateDaoSupport;
import th.ac.kbu.cs.ExamProject.Common.Dao.BasicEntityDao;

@Repository
public class BasicEntityDaoImpl extends CustomHibernateDaoSupport implements BasicEntityDao{
	
	
	@Transactional
	public void delete(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	
	@Transactional
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	
	public <T> T findByIdentifier(Class<T> entityClass, Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	
	@Transactional
	public <T> T findExistingByIdentifier(Class<T> entityClass, Serializable id) {
		return (T) this.getHibernateTemplate().load(entityClass, id);
	}

	
	@Transactional
	public Serializable save(Object entity) {
		return this.getHibernateTemplate().save(entity);
	}

	
	@Transactional
	public void saveOrUpdate(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	
	@Transactional
	public void update(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	
	@Transactional
	public void persist(Object entity) {
		this.getHibernateTemplate().persist(entity);
	}

	
	@Transactional
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	
	@Transactional
	public int bulkUpdate(String hql, Object value) {
		return this.getHibernateTemplate().bulkUpdate(hql, value);
	}

	
	@Transactional
	public int bulkUpdate(String hql, Object[] values) {
		return this.getHibernateTemplate().bulkUpdate(hql, values);
	}

	
	@Transactional
	public int bulkUpdate(String hql) {
		return this.getHibernateTemplate().bulkUpdate(hql);
	}

	
	@Transactional
	public <T> void deleteAll(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	
	@Transactional
	public <T> T execute(HibernateCallback<T> action) {
		return this.getHibernateTemplate().execute(action);
	}

	
	@Transactional
	public void clear() {
		this.getHibernateTemplate().clear();
	}
}
