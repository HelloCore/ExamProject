package th.ac.kbu.cs.ExamProject.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import th.ac.kbu.cs.ExamProject.Common.TransactionalProcess;

public interface BasicEntityService {
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

	Object execute(TransactionalProcess transactionalProcess, Object... args);

	@Deprecated
	Object doTransaction(Object scope, Method method, Object... arg);

	@Deprecated
	<T> Object doTransaction(T scope, String methodName, Object... arg);

	<T> List<Serializable> save(List<T> objects);

	<T> void deleteAll(Class<T> entityClass, List<Serializable> ids);

	<T> void update(List<T> objects);

	<T> void saveOrUpdate(List<T> objects);

	<T> void saveAndUpdate(List<T> saveObjects, List<T> updateObjects);

	void delete(DetachedCriteria detachedCriteria);
	
	Session getCurrentSession();
}
