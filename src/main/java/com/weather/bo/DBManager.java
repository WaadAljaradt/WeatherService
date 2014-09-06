package com.weather.bo;





import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weather.persistence.dao.CityDao;
import com.weather.persistence.dao.CoordinateDao;
import com.weather.persistence.dao.WeatherDao;
import com.weather.persistence.model.City;
import com.weather.persistence.model.Coordinate;
import com.weather.persistence.model.Weather;

@Component
public class DBManager {
	private static Logger logger = LoggerFactory.getLogger(DBManager.class);
	@Autowired
	private CityDao cityDao;
	@Autowired
	private CoordinateDao coordinateDao;
	@Autowired
	private WeatherDao weatherDao;
	private String className= "DBManager";
	/**
	 * This function return city from Database according the the cityName received 
	 * @param cityName
	 * @return City with the cityName received
	 */
	public City getCity(String cityName){
		String methodName = "getCity";
		logger.info("In: " + className);
		logger.debug("In :"+ methodName );
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("city_name", cityName);
		map.put("response_name", cityName);
		logger.debug("call list with cityName :" + cityName);
		List<City> cities = cityDao.list(map, false);
		if(cities == null ||cities.isEmpty()){
			logger.error("no records found for city with cityName  :" + cityName);
			logger.debug("return null");
			return null;
		}else{
			logger.debug("return city with CityName :"+cityName );
			return cities.get(0);
		}		
	}
	/**
	 * This function save City object that is received in the database
	 * @param city Object
	 * @throws Exception : db failure 
	 */
	public City  saveCity(City city) throws Exception {
		String methodName = "saveCity";
		logger.info("In: " + className);
		logger.debug("In :"+ methodName );
		
		try {
			if(city != null){
				logger.debug(" call create for city with cityName :"+city.getCityName());
			}
			return cityDao.create(city);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(city != null){
				logger.error("error in creating city with cityName :"+ city.getCityName());
			}
			logger.error("Error Message :"+e.getMessage());	
			throw e;
		}
		
	}
	/**
	 * This function save Coordinate object that is received in the database 
	 * @param coordinate
	 * @throws Exception : db failure 
	 */
	public Coordinate saveCordinate(Coordinate coordinate) throws Exception {
		String methodName = "saveCordinate";
		logger.info("In: " + className);
		logger.debug("In :"+ methodName );
		
		try {
			if(coordinate != null){
				logger.debug(" call create for coordinate with lat :"+coordinate.getLat()+ " and lon :"+coordinate.getLon());
			}
			return coordinateDao.create(coordinate);
		} catch (Exception e) {
			logger.error("error in creating coordinate");
			logger.error("Error Message :"+e.getMessage());	
			throw e;
		}
		
	}
	/**
	 * This function save Weather object that is received in the database  
	 * @param weather
	 * @throws Exception : db failure 
	 */
	public Weather saveWeather(Weather weather) throws Exception {
		String methodName = "saveWeather";
		logger.info("In: " + className);
		logger.debug("In :"+ methodName );
		try {
			if(weather != null){
				logger.debug(" call create for Weather :"+weather.toString());
			}
			return weatherDao.create(weather);
		} catch (Exception e) {
			logger.error("error in creating weather");
			logger.error("Error Message :"+e.getMessage());
			throw e;
		}
		
	}
	/**
	 * This function update the city object with it's new changes to the data base
	 * @param city
	 * @throws Exception : db failure 
	 */
	
	public void updateCity(City city) throws Exception {
		String methodName = "updateCity";
		logger.info("In: " + className);
		logger.debug("In :"+ methodName );
		
		try {
			if(city != null){
				logger.debug(" call update for city with cityName :"+city.getCityName());
			}
			cityDao.update(city);
		} catch (Exception e) {
			if(city != null){
				logger.error("error in updating city with cityName :"+ city.getCityName());
			}
			logger.error("Error Message :"+e.getMessage());	
			throw e;
		}	
	}
	
}
