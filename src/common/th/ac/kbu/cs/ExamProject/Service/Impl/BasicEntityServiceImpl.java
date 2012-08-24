package th.ac.kbu.cs.ExamProject.Service.Impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.engine.QueryParameters;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.ac.kbu.cs.ExamProject.Common.TransactionalProcess;
import th.ac.kbu.cs.ExamProject.Common.Dao.BasicEntityDao;
import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Util.MethodUtils;


@Service
public class BasicEntityServiceImpl implements BasicEntityService {
	
	private BasicEntityDao basicEntityDao;

	@Autowired
	public void setBasicEntityDao(BasicEntityDao basicEntityDao) {
		this.basicEntityDao = basicEntityDao;
	}

	public BasicEntityDao getBasicEntityDao() {
		return basicEntityDao;
	}

	@Transactional
	public void delete(Object entity) {
		getBasicEntityDao().delete(entity);
	}

	@Transactional
	public <T> List<T> findAll(Class<T> entityClass) {
		return getBasicEntityDao().findAll(entityClass);
	}

	@Transactional
	public <T> T findByIdentifier(Class<T> entityClass, Serializable id) {
		return getBasicEntityDao().findByIdentifier(entityClass, id);
	}

	@Transactional
	public <T> T findExistingByIdentifier(Class<T> entityClass, Serializable id) {
		return getBasicEntityDao().findExistingByIdentifier(entityClass, id);
	}

	@Transactional
	public Serializable save(Object entity) {
		return getBasicEntityDao().save(entity);
	}

	@Transactional
	public void saveOrUpdate(Object entity) {
		getBasicEntityDao().saveOrUpdate(entity);
	}

	@Transactional
	public void update(Object entity) {
		getBasicEntityDao().update(entity);
	}

	@Transactional
	public void flush() {
		getBasicEntityDao().flush();
	}

	@Transactional
	public void clear() {
		getBasicEntityDao().clear();
	}

	@Transactional
	public int bulkUpdate(String hql, Object value) {
		return getBasicEntityDao().bulkUpdate(hql, value);
	}

	@Transactional
	public int bulkUpdate(String hql, Object[] values) {
		return getBasicEntityDao().bulkUpdate(hql, values);
	}

	@Transactional
	public int bulkUpdate(String hql) {
		return getBasicEntityDao().bulkUpdate(hql);
	}

	@Transactional
	public <T> void deleteAll(Collection<T> entities) {
		getBasicEntityDao().deleteAll(entities);
	}

	@Transactional
	public <T> T execute(HibernateCallback<T> action) {
		return getBasicEntityDao().execute(action);
	}

	@Transactional
	public void persist(Object entity) {
		getBasicEntityDao().persist(entity);
	}

	@Transactional
	public <T> void deleteAll(Class<T> entityClass, List<Serializable> ids) {
		for (Serializable id : ids) {
			T t = basicEntityDao.findExistingByIdentifier(entityClass, id);
			basicEntityDao.delete(t);
		}
	}

	@Transactional
	public <T> List<Serializable> save(List<T> objects) {
		List<Serializable> ids = new ArrayList<Serializable>();
		for (Object object : objects) {
			ids.add(basicEntityDao.save(object));
		}
		return ids;
	}

	@Transactional
	public <T> void saveAndUpdate(List<T> saveObjects, List<T> updateObjects) {
		if (saveObjects != null && saveObjects.size() > 0) {
			for (T saveObject : saveObjects) {
				basicEntityDao.save(saveObject);
			}
		}

		if (updateObjects != null && updateObjects.size() > 0) {
			for (T updateObject : updateObjects) {
				basicEntityDao.update(updateObject);
			}
		}
	}

	@Transactional
	public <T> void saveOrUpdate(List<T> objects) {
		for (Object object : objects) {
			basicEntityDao.saveOrUpdate(object);
		}
	}

	@Transactional
	public <T> void update(List<T> objects) {
		for (Object object : objects) {
			basicEntityDao.update(object);
		}
	}

	@Deprecated
	@Transactional
	public <T> Object doTransaction(T scope, String methodName, Object... args) {
		Class<?>[] classes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			classes[i] = args[i].getClass();
		}
		Method method = MethodUtils.getMethod(scope.getClass(), methodName, classes);
		return doTransaction(scope, method, args);
	}

	@Deprecated
	@Transactional
	public Object doTransaction(Object scope, Method method, Object... args) {
		Object result = null;
		try {
			result = method.invoke(scope, args);
		} catch (IllegalArgumentException e) {
		
		} catch (IllegalAccessException e) {
		
		} catch (InvocationTargetException e) {
		}
		return result;
	}

	@Transactional
	public Object execute(TransactionalProcess transactionalProcess, Object... args) {
		return transactionalProcess.doTransaction(args);
	}

	@Transactional
	public void delete(final DetachedCriteria detachedCriteria) {
		basicEntityDao.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = detachedCriteria.getExecutableCriteria(session);
				CriteriaImpl criteriaImpl = (CriteriaImpl) c;
				SessionFactoryImplementor factory = (SessionFactoryImplementor) session.getSessionFactory();

				CriteriaQueryTranslator innerQuery = new CriteriaQueryTranslator(factory, criteriaImpl, criteriaImpl.getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);

				final OuterJoinLoadable persister = (OuterJoinLoadable) factory.getEntityPersister(criteriaImpl.getEntityOrClassName());
				CriteriaJoinWalker walker = new CriteriaJoinWalker(persister, innerQuery, factory, criteriaImpl, criteriaImpl.getEntityOrClassName(), criteriaImpl.getSession().getLoadQueryInfluencers(), innerQuery.getRootSQLALias());

				String sql = walker.getSQLString();
				sql = " delete " + sql.substring(sql.indexOf("from"));
				SQLQuery sqlQuery = session.createSQLQuery(sql);

				QueryParameters parameters = innerQuery.getQueryParameters();
				sqlQuery.setParameters(parameters.getPositionalParameterValues(), parameters.getPositionalParameterTypes());
				return sqlQuery.executeUpdate();
			}
		});
	}

	@Transactional(readOnly=true)
	public Session getCurrentSession(){
		return getBasicEntityDao().getCurrentSession();
	}
}