package com.weather.test.dao;
import java.util.ArrayList;

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
import com.weather.persistence.model.City;
import com.weather.persistence.model.Coordinate;
import com.weather.persistence.model.Weather;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= { "/test-spring-ctx.xml" })
@TransactionConfiguration( transactionManager ="transactionManager", defaultRollback = true)
@Transactional
public class DaoTest {
	@Autowired
	private CityDao cityDao;
	String className ="CityDaoTest";
	
	private static Logger logger = LoggerFactory.getLogger(DaoTest.class);
	@Test
	public void testCreate() throws Exception {
		String methodName ="testCreate";
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
		Assert.assertEquals(cityT.getCityName(), city.getCityName());
		logger.debug("Creation Success of City with Name : dubai");
	}
	
	@Test
	public void testCountAll() throws Exception{
		String methodName ="testCountAll";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city1 = new City();
		city1.setCityName("dubai");
		city1.setSunRise(34857403);
		city1.setSunRise(927469392);
		Weather weather1 = new Weather();
		weather1.setDescription("clear");
		weather1.setMain("clear");	
		Coordinate coordinate1 = new Coordinate();
		coordinate1.setLat(new Double(45));
		coordinate1.setLon(78d);
		city1.setCoordinate(coordinate1);
		weather1.setTemp(77d);
		weather1.setTempMax(78d);
		weather1.setTempMin(60d);
		city1.getWeathers().add(weather1);
		cityDao.create(city1);
		// create city1 
		logger.debug("Create City object with name : london");
		City city2 = new City();
		city2.setCityName("London");
		city2.setSunRise(34857403);
		city2.setSunRise(927469392);
		Weather weather2 = new Weather();
		weather2.setDescription("clear");
		weather2.setMain("clear");	
		Coordinate coordinate2 = new Coordinate();
		coordinate2.setLat(new Double(45));
		coordinate2.setLon(78d);
		city2.setCoordinate(coordinate2);
		weather2.setTemp(77d);
		weather2.setTempMax(78d);
		weather2.setTempMin(60d);
		city2.getWeathers().add(weather2);
		cityDao.create(city2);
		// create City3
		logger.debug("Create City object with name : toronto");
		City city3 = new City();
		city3.setCityName("toronto");
		city3.setSunRise(34857403);
		city3.setSunRise(927469392);
		Weather weather3 = new Weather();
		weather3.setDescription("clear");
		weather3.setMain("clear");	
		Coordinate coordinate3 = new Coordinate();
		coordinate3.setLat(new Double(45));
		coordinate3.setLon(78d);
		city3.setCoordinate(coordinate3);
		weather3.setTemp(77d);
		weather3.setTempMax(78d);
		weather3.setTempMin(60d);
		city3.getWeathers().add(weather3);
		cityDao.create(city3);
		List <City> cityList = cityDao.list();
		logger.debug(" call countAll from City Dao, Expected value : "+cityList.size());
		Long count = cityDao.countAll(null);
		System.out.println(cityList.size());
		logger.debug(" returned value :"+count);
		Assert.assertEquals(cityList.size(), cityDao.countAll(null));	
		logger.debug(" countAll Success");
	}
	
	@Test
	public void deleteTest() throws Exception{
		String methodName ="deleteTest";
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
		City cityT = cityDao.create(city);
		Assert.assertEquals(cityT.getCityName(), city.getCityName());
		logger.debug("call delete from CityDao ");
		cityDao.delete(cityT.getId());
		Assert.assertNull(cityDao.find(cityT.getId()));	
		logger.debug(" delete Success ");
	}
	@Test
	public void deleteAllTest() throws Exception
	{
		String methodName ="deleteAllTest";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city1 = new City();
		city1.setCityName("dubai");
		city1.setSunRise(34857403);
		city1.setSunRise(927469392);
		Weather weather1 = new Weather();
		weather1.setDescription("clear");
		weather1.setMain("clear");	
		Coordinate coordinate1 = new Coordinate();
		coordinate1.setLat(new Double(45));
		coordinate1.setLon(78d);
		city1.setCoordinate(coordinate1);
		weather1.setTemp(77d);
		weather1.setTempMax(78d);
		weather1.setTempMin(60d);
		city1.getWeathers().add(weather1);
		cityDao.create(city1);
		// create city1 
		logger.debug("Create City object with name : London");
		City city2 = new City();
		city2.setCityName("London");
		city2.setSunRise(34857403);
		city2.setSunRise(927469392);
		Weather weather2 = new Weather();
		weather2.setDescription("clear");
		weather2.setMain("clear");	
		Coordinate coordinate2 = new Coordinate();
		coordinate2.setLat(new Double(45));
		coordinate2.setLon(78d);
		city2.setCoordinate(coordinate2);
		weather2.setTemp(77d);
		weather2.setTempMax(78d);
		weather2.setTempMin(60d);
		city2.getWeathers().add(weather2);
		cityDao.create(city2);
		// create City3
		logger.debug("Create City object with name : toronto");
		City city3 = new City();
		city3.setCityName("toronto");
		city3.setSunRise(34857403);
		city3.setSunRise(927469392);
		Weather weather3 = new Weather();
		weather3.setDescription("clear");
		weather3.setMain("clear");	
		Coordinate coordinate3 = new Coordinate();
		coordinate3.setLat(new Double(45));
		coordinate3.setLon(78d);
		city3.setCoordinate(coordinate3);
		weather3.setTemp(77d);
		weather3.setTempMax(78d);
		weather3.setTempMin(60d);
		city3.getWeathers().add(weather3);
		cityDao.create(city3);
		logger.debug("call delete All from City Dao");
		cityDao.deleteAll();
		Assert.assertNull(cityDao.find(city1.getId()));
		Assert.assertNull(cityDao.find(city2.getId()));
		Assert.assertNull(cityDao.find(city3.getId()));	
		logger.debug("delete All Sucess");
	}
	@Test
	public void findTest() throws Exception{
		String methodName ="findTest";
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
		City cityT = cityDao.create(city);
		logger.debug("call find for city with id :"+ cityT.getId());
		Assert.assertEquals(cityT.getCityName(),cityDao.find(cityT.getId()).getCityName() );
		Assert.assertEquals(cityT.getCityName(),city.getCityName());
		logger.debug("find Success");
		
	}
	
	@Test
	public void updateTest() throws Exception {
		String methodName ="updateTest";
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
		City cityT = cityDao.create(city);
		logger.debug("change name city from dubai to london ");
		cityT.setCityName("london");
		logger.debug("call update ");
		cityDao.update(cityT);
		Assert.assertEquals("london",cityDao.find(cityT.getId()).getCityName());
		logger.debug("update Success ");
	}
	@Test
	public void ListTest() throws Exception
	{
		String methodName ="ListTest";
		logger.info("In :"+ className);
		logger.debug("In: "+ methodName);
		logger.debug("Create City object with name : dubai");
		
		City city1 = new City();
		city1.setCityName("dubai");
		city1.setSunRise(34857403);
		city1.setSunRise(927469392);
		Weather weather1 = new Weather();
		weather1.setDescription("clear");
		weather1.setMain("clear");	
		Coordinate coordinate1 = new Coordinate();
		coordinate1.setLat(new Double(45));
		coordinate1.setLon(78d);
		city1.setCoordinate(coordinate1);
		weather1.setTemp(77d);
		weather1.setTempMax(78d);
		weather1.setTempMin(60d);
		city1.getWeathers().add(weather1);
		city1 = cityDao.create(city1);
		// create city1 
		logger.debug("Create City object with name : london");
		City city2 = new City();
		city2.setCityName("London");
		city2.setSunRise(34857403);
		city2.setSunRise(927469392);
		Weather weather2 = new Weather();
		weather2.setDescription("clear");
		weather2.setMain("clear");	
		Coordinate coordinate2 = new Coordinate();
		coordinate2.setLat(new Double(45));
		coordinate2.setLon(78d);
		city2.setCoordinate(coordinate2);
		weather2.setTemp(77d);
		weather2.setTempMax(78d);
		weather2.setTempMin(60d);
		city2.getWeathers().add(weather2);
		city2 = cityDao.create(city2);
		// create City3
		logger.debug("Create City object with name : toronto");
		City city3 = new City();
		city3.setCityName("toronto");
		city3.setSunRise(34857403);
		city3.setSunRise(927469392);
		Weather weather3 = new Weather();
		weather3.setDescription("clear");
		weather3.setMain("clear");	
		Coordinate coordinate3 = new Coordinate();
		coordinate3.setLat(new Double(45));
		coordinate3.setLon(78d);
		city3.setCoordinate(coordinate3);
		weather3.setTemp(77d);
		weather3.setTempMax(78d);
		weather3.setTempMin(60d);
		city3.getWeathers().add(weather3);
		city3 = cityDao.create(city3);
		logger.debug("call list to return list of cities created");
		List <City> cityList = cityDao.list();
		Assert.assertTrue(cityList.contains(city1));
		Assert.assertTrue(cityList.contains(city2));
		Assert.assertTrue(cityList.contains(city3));
		for (int i = 0; i < cityList.size(); i++) {
			logger.debug("\n city name :"+cityList.get(i) );
		}
		logger.debug("List Success");
	}
	@Test
	public void ListMapTest() throws Exception {
		String methodName ="ListMapTest";
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
		City cityT = cityDao.create(city);
		logger.debug("Create params to be send to get customized list with cityname : dubai");
		Map<String, Object> params = new HashMap<String, Object> ();
		params.put("city_name", "dubai");
		logger.debug("call list with parameters");
		List <City> cityList = cityDao.list(params, null);
		Assert.assertTrue(cityList.contains(cityT));
		logger.debug("call list with parameters Success");
	}
	
	

}
