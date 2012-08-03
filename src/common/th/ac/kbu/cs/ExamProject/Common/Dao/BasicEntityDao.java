package th.ac.kbu.cs.ExamProject.Common.Dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;

public interface BasicEntityDao {
	<T> T findByIdentifier(Class<T> entityClass, Serializable id);

	<T> T findExistingByIdentifier(Class<T> entityClass, Serializable id);

	<T> List<T> findAll(Class<T> entityClass);

	Serializable save(Object entity);

	void saveOrUpdate(Object entity);

	void update(Object entity);

	int bulkUpdate(String hql);

	int bulkUpdate(String hql, Object value);

	int bulkUpdate(String hql, Object[] values);

	void delete(Object entity);

	<T> void deleteAll(Collection<T> entities);

	void persist(Object entity);

	void flush();

	void clear();

	<T> T execute(HibernateCallback<T> action);
}
