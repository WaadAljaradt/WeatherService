package com.weather.test.bo;

import static org.junit.Assert.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.weather.bo.DBManager;
import com.weather.persistence.dao.CityDao;
import com.weather.persistence.dao.CoordinateDao;
import com.weather.persistence.dao.WeatherDao;
import com.weather.persistence.model.City;
import com.weather.persistence.model.Coordinate;
import com.weather.persistence.model.Weather;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= { "/test-spring-ctx.xml" })
@TransactionConfiguration( transactionManager ="transactionManager", defaultRollback = true)
@Transactional
public class DBManagerTest {
	
	private static Logger logger = LoggerFactory.getLogger(DBManagerTest.class);
	@Autowired
	private DBManager dbManager;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private CoordinateDao coordinateDao;
	@Autowired
	private WeatherDao weatherDao;
	private String className ="DBManagerTest";
	@Test
	public void getCityTest() throws Exception {
		String methodName ="getCityTest";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city = new City();
		city.setCityName("dubai");
		city.setSunRise(34857403);
		city.setSunRise(927469392);
		Weather weather = new Weather();
		weather.setDescription("clear");
		weather.setMain("clear");	
		Coordinate coordinate = new Coordinate();
		coordinate.setLat(new Double(45));
		coordinate.setLon(78d);
		city.setCoordinate(coordinate);
		weather.setTemp(77d);
		weather.setTempMax(78d);
		weather.setTempMin(60d);
		city.getWeathers().add(weather);
		logger.debug("call create from City Dao");
		City cityT = cityDao.create(city);
		Assert.assertTrue(cityT.getCityName().equalsIgnoreCase(dbManager.getCity("dubai").getCityName()));
		logger.debug("Get City Success of City with Name : dubai");
		
	}
	@Test
	public void testSaveCity() throws Exception {
		String methodName ="testSaveCity";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city = new City();
		city.setCityName("dubai");
		city.setSunRise(34857403);
		city.setSunRise(927469392);
		Weather weather = new Weather();
		weather.setDescription("clear");
		weather.setMain("clear");	
		Coordinate coordinate = new Coordinate();
		coordinate.setLat(new Double(45));
		coordinate.setLon(78d);
		city.setCoordinate(coordinate);
		weather.setTemp(77d);
		weather.setTempMax(78d);
		weather.setTempMin(60d);
		city.getWeathers().add(weather);
		logger.debug("call create from City Dao");
		dbManager.saveCity(city);
		Map<String, Object> params = new HashMap<String, Object> ();
		params.put("city_name", "dubai");
		List <City> cityList = cityDao.list(params, null);
		
		Assert.assertTrue(city.getCityName().equalsIgnoreCase(cityList.get(0).getCityName()));
		logger.debug("Creation Success of City with Name : dubai");
	}
	@Test
	public void testSaveCoordinate() throws Exception {
		String methodName ="testSaveCoordinate";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create coordinate");
		Coordinate coord = new Coordinate();
		coord.setLat(34d);
		coord.setLon(44d);
		Coordinate coorddb = dbManager.saveCordinate(coord);
		Map<String, Object> params = new HashMap<String, Object> ();
		params.put("lon", "44");
		Integer saveCoord=0; 
		List <Coordinate> coordList = coordinateDao.list(params, null);
		for (int i = 0; i < coordList.size(); i++) {
			if(coordList.get(i).getId() == coorddb.getId()){
				saveCoord = i;
			}
		}
		Assert.assertEquals(coord.getLat(),coordList.get(saveCoord).getLat());
		logger.debug("Save Success of Coordinate ");
	}
	@Test
	public void testSaveWeather() throws Exception {
		String methodName ="testSaveWeather";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create Weather");
		
		Weather weather = new Weather ();
		Date date = new Date();
		weather.setDate(date);
		weather.setDescription("description");
		weather.setMain("Main");
		weather.setTemp(44d);
		weather.setTempMax(55d);
		weather.setTempMin(20d);
		Weather weatherdb = dbManager.saveWeather(weather);
		Map<String, Object> params = new HashMap<String, Object> ();
		params.put("description", "description");
		Integer saveweather=0; 
		List <Weather> weatherList = weatherDao.list(params, null);
		Assert.assertFalse(weatherList.isEmpty());
		for (int i = 0; i < weatherList.size(); i++) {
			if(weatherList.get(i).getWeatherId() == weatherdb.getWeatherId()){
				saveweather = i;
			}
		}
		Assert.assertEquals(weather.getTemp(),weatherList.get(saveweather).getTemp());
		logger.debug("Save Success of Weather ");		
	}
	
	@Test
	public void testUpdateCity() throws Exception {
		String methodName ="testUpdateCity";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city = new City();
		city.setCityName("dubai");
		city.setSunRise(34857403);
		city.setSunRise(927469392);
		Weather weather = new Weather();
		weather.setDescription("clear");
		weather.setMain("clear");	
		Coordinate coordinate = new Coordinate();
		coordinate.setLat(new Double(45));
		coordinate.setLon(78d);
		city.setCoordinate(coordinate);
		weather.setTemp(77d);
		weather.setTempMax(78d);
		weather.setTempMin(60d);
		city.getWeathers().add(weather);
		logger.debug("call create from City Dao");
		City citydb = dbManager.saveCity(city);
		Integer id = citydb.getId();
		citydb.setCountryName("AAA");
		dbManager.updateCity(citydb);
		City updatedCity = cityDao.find(id);
		Assert.assertEquals("AAA", updatedCity.getCountryName());
		logger.debug("Updating Success of City with Name : dubai");
	}
	
		
		
		

}
