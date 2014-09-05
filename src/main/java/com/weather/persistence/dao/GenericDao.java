package com.weather.persistence.dao;

import java.util.List;
import java.util.Map;


public interface GenericDao<T> {
    
	/**
	 * This function return the count of objects of type T 
	 * form data base with the give constrains in params
	 * select all if params = null
	 * @return long count 
	 */
    long countAll(Map<String, Object> params);
    /**
	 * Create object T
	 * @return saved object in data base
	 */
    T create(T t) throws Exception;
    /**
	 * delete Object with the given Id
	 */
    void delete(Object id);
    /**
	 * delete all records of specific object (Table)
	 */
	public void deleteAll();
	/**
	 * Find object with the give Id from data base and return the object
	 * @param id
	 * @return object with given Id
	 */
    T find(Object id);
    /**
     * Update object with the new data and return the new updated object
     * @param t object to be updated
     * @return object with the new data
     */
	T update(T t);
	/**
	 * return all records of object T from data base
	 * @return List of T
	 */
	List<T> list();
	/**
	 * return List of records from data base of object T with specific constrains given in 
	 * params 
	 * @param params : constrains
	 * @param andOr : if andOr in query
	 * @return List of object T
	 */

	List<T> list(final Map<String, Object> params, Boolean andOr);

}
