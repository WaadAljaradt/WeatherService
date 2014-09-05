package com.weather.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.weather.persistence.dao.CityDao;
import com.weather.persistence.model.City;

@Repository
@Transactional
public class CityDaoImpl extends GenericDaoImpl<City> implements CityDao{

}
