package com.weather.persistence.dao.impl;

import java.lang.reflect.ParameterizedType;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.map.ser.SerializerCache.TypeKey;
import org.springframework.transaction.annotation.Transactional;

import com.weather.bo.openweather.WeatherRequestManager;
import com.weather.persistence.dao.GenericDao;

@Transactional
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	private static Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);
	private String className = "GenericDaoImpl";

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#countAll(java.util.Map)
	 */
	public long countAll(final Map<String, Object> params) {
		String methodName = "countAll";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		if (params != null) {
			for (String name : params.keySet()) {
				Object value = params.get(name);
				logger.debug("name :" + name + "value :" + value.toString());
			}
		}

		final StringBuffer queryString = new StringBuffer(
				"SELECT count(o) from ");

		queryString.append(type.getSimpleName()).append(" o ");
		queryString.append(this.getQueryClauses(params, null, true));
		logger.debug("created Query" + queryString);

		final Query query = this.em.createQuery(queryString.toString());
		logger.debug("return count " + query.getSingleResult());
		return (Long) query.getSingleResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#create(java.lang.Object)
	 */
	public T create(final T t) throws Exception {
		String methodName = "create";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		this.em.persist(t);
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#delete(java.lang.Object)
	 */
	public void delete(final Object id) {
		String methodName = "delete";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		this.em.remove(this.em.getReference(type, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#deleteAll()
	 */
	public void deleteAll() {
		String methodName = "deleteAll";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		List<T> list = this.list();
		for (T t : list) {
			this.em.remove(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#find(java.lang.Object)
	 */
	public T find(final Object id) {
		String methodName = "find";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		return (T) this.em.find(type, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#update(java.lang.Object)
	 */
	public T update(final T t) {
		String methodName = "update";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		return this.em.merge(t);
	}

	/**
	 * this function goes through the Map of params that is given to construct
	 * the required query with the specific constrain given
	 * 
	 * @param params
	 * @param orderParams
	 * @param andOr
	 *            : if andOr in String query
	 * @return String query
	 */
	protected String getQueryClauses(final Map<String, Object> params,
			final Map<String, Object> orderParams, Boolean andOr) {
		String methodName = "getQueryClauses";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		final StringBuffer queryString = new StringBuffer();
		if ((params != null) && !params.isEmpty()) {
			queryString.append(" where ");
			for (final Iterator<Map.Entry<String, Object>> it = params
					.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				if (entry.getValue() instanceof Boolean) {
					queryString.append(entry.getKey()).append(" is ")
							.append(entry.getValue()).append(" ");
				} else {
					if (entry.getValue() instanceof Number) {
						queryString.append(entry.getKey()).append(" = ")
								.append(entry.getValue());
					} else {
						// string equality
						queryString.append(entry.getKey()).append(" = '")
								.append(entry.getValue()).append("'");
					}
				}
				if (it.hasNext()) {
					if (andOr) {
						queryString.append(" and ");
					} else {
						queryString.append(" or ");
					}
				}
			}
		}
		if ((orderParams != null) && !orderParams.isEmpty()) {
			queryString.append(" order by ");
			for (final Iterator<Map.Entry<String, Object>> it = orderParams
					.entrySet().iterator(); it.hasNext();) {
				final Map.Entry<String, Object> entry = it.next();
				queryString.append(entry.getKey()).append(" ");
				if (entry.getValue() != null) {
					queryString.append(entry.getValue());
				}
				if (it.hasNext()) {
					queryString.append(", ");
				}
			}
		}
		logger.debug(" constructed query as required :"
				+ queryString.toString());
		return queryString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#list()
	 */
	public List<T> list() {
		String methodName = "list";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		final StringBuffer queryString = new StringBuffer("SELECT o from ");

		queryString.append(type.getSimpleName()).append(" o ");

		final Query query = em.createQuery(queryString.toString());

		List<T> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weather.persistence.dao.GenericDao#list(java.util.Map,
	 * java.lang.Boolean)
	 */
	public List<T> list(final Map<String, Object> params, Boolean andOr) {
		String methodName = "list";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug("with params of constrains");
		final StringBuffer queryString = new StringBuffer("SELECT o from ");

		queryString.append(type.getSimpleName()).append(" o ");
		queryString.append(this.getQueryClauses(params, null, andOr));

		final Query query = this.em.createQuery(queryString.toString());

		List<T> resultList = query.getResultList();
		em.close();
		return resultList;

	}

	public EntityManager getEm() {
		return em;
	}

}