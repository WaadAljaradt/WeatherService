package com.weather.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.weather.persistence.dao.WeatherDao;
import com.weather.persistence.model.Weather;
@Repository
@Transactional
public class WeatherDaoImpl extends GenericDaoImpl<Weather> implements WeatherDao{

}
