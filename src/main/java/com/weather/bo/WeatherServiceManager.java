package com.weather.bo;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weather.bo.openweather.WeatherRequestManager;
import com.weather.service.vo.City;
import com.weather.service.vo.Main;
import com.weather.service.vo.Weather;
import com.weather.service.vo.WeatherInfo;

@Service
public class WeatherServiceManager {
	@Autowired
	private DBManager dbManager;
	@Autowired
	private UtilMap utilMap;
	@Autowired
	private WeatherRequestManager request;
	@Autowired
	private JsonParser jsonParser;
	private static Logger logger = LoggerFactory
			.getLogger(WeatherServiceManager.class);
	private String className = "WeatherServiceManager";

	/**
	 * this function is responsible for managing request according to city name
	 * 1. if city is not found return Error Message and set cod = 404 2. if city
	 * is asked for the first time retrieve it's info and save it to data base
	 * 3. if city is already in data base it compare the latest weather info of
	 * this city with the current date if the current is later than the latest
	 * weather saved it retrieves info again from open weather and save it
	 * again, if not it return the already fetched data from data base
	 * 
	 * @param cityName
	 * @return WeatherInfo
	 * @throws Exception
	 *             :db failure
	 */
	@Transactional
	public WeatherInfo getWeatherInfo(String cityName) throws Exception {
		String methodName = "getWeatherInfo";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug(" get weather info for City :" + cityName);
		WeatherInfo info = new WeatherInfo();
		Response response;
		
		if(cityName == null || !utilMap.isAlpha(cityName)){
			logger.error(" city entered is incorrect" + cityName);
			info.setCode(404);
			logger.debug(" return Error Message");
			return info;
			
		}
		com.weather.persistence.model.City cityDB = dbManager.getCity(cityName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (cityDB == null) {
			logger.debug(" city Not found in data base , cityName :" + cityName);
			logger.debug(" call response from openWeather");
			response = retrieveInfoFromOpenWeather(cityName);
			if (jsonParser.isErrorResponse(response)) {
				logger.error(" city Not found error" + cityName);
				info.setCode(404);
				logger.debug(" return Error Message");
				return info;
			}
			logger.debug("call json parser to create City object and save info from the response");
			cityDB = jsonParser.ParseToCity(response, 0);
			logger.debug("save city object to data base");
			cityDB.setCityName(cityName);
			saveCity(cityDB);
		} else {
			Integer id = cityDB.getId();
			logger.debug("city is found in data base and with id :" + id);
			com.weather.persistence.model.Weather temp = utilMap
					.getCurrentWeather(cityDB.getWeathers());
			if (temp != null) {

				// make sure that DB has the latest weather Info for today
				try {
					logger.debug("compare latest weather date with current date :" + id);
					Date DBdate = sdf.parse(sdf.format(temp.getDate()));
					Date date = sdf.parse(sdf.format(new Date()));
					if (date.compareTo(DBdate) > 0) {
						logger.debug("city doesnt have the latest weather info");
						logger.debug("call openweather for latest info ");
						response = retrieveInfoFromOpenWeather(cityName);
						logger.debug("parse json to City, with flag 1 to indicate not first time city ");
						cityDB = jsonParser.ParseToCity(response, 1);
						cityDB.setId(id);
						logger.debug("weather with new data need to be inserted in data base");
						logger.debug(" call city update");
						dbManager.updateCity(cityDB);
					}
				} catch (ParseException e) {
					logger.error("Date parsing Error");
					logger.error("Error Message"+ e.getMessage());
					throw e;
				}
			}
		}
		logger.debug("map from city model to vo to be set in weatherInfo object later ");
		City city = utilMap.getCityVo(cityDB);
		if(cityDB.getResponse_name() == null ||cityDB.getResponse_name().isEmpty()){
			city.setName(cityName);
		}else{
			city.setName(cityDB.getResponse_name());
		}
		info.setCity(city);
		logger.debug("map from weather model to vo to be set in weatherInfo object later ");
		Weather weather = utilMap.getWeatherVo(cityDB);
		info.setWeather(weather);
		logger.debug("get Main object from citydb weatherInfo object later ");
		Main main = utilMap.getMain(cityDB);
		info.setMain(main);
		logger.debug("set to current date ");
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		info.setDate(dateFormat.format(date));
		logger.debug("return info " + info.toString());
		return info;

	}
	/**
	 * this function calls request to open weather with the given cityName 
	 * @param cityName
	 * @return json response from openweather 
	 */

	private Response retrieveInfoFromOpenWeather(String cityName) {
		String methodName = "retrieveInfoFromOpenWeather";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug(" retrieve info from openweather for city:"+ cityName);
		Response response = request.getWeather(cityName);
		logger.debug(" return response "+response.readEntity(String.class));
		return response;
	}
	/**
	 * this function save City object to data base and it's related objects of weather and coordinate
	 * @param city object with info to be saved
	 * @throws Exception : db failure 
	 */

	private void saveCity(com.weather.persistence.model.City city)
			throws Exception {
		String methodName = "saveCity";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug("save coordinate to data base");
		dbManager.saveCordinate(city.getCoordinate());
		logger.debug("save latest weather added to City");
		com.weather.persistence.model.Weather latestWeather = utilMap
				.getCurrentWeather(city.getWeathers());
		logger.debug("save weather to data base");
		dbManager.saveWeather(latestWeather);
		logger.debug("save city to data base");
		dbManager.saveCity(city);
	}

}
