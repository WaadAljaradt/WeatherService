package com.weather.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weather.service.vo.Coord;

@Component
public class UtilMap {
	private static Logger logger = LoggerFactory.getLogger(UtilMap.class);
	private String className = "UtilMap";

	/**
	 * This function convert a City from the model form to the vo form maps the
	 * properties to it's probable match and saves it's values in the vo form
	 * 
	 * @param City
	 *            from the data base cityDB of type model
	 * @return return com.weather.service.vo.City City
	 */

	public com.weather.service.vo.City getCityVo(
			com.weather.persistence.model.City cityDB) {
		String methodName = "getCityVo";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		logger.debug("map info from city of model form to Vo form ");
		com.weather.service.vo.City cityVo = new com.weather.service.vo.City();
		logger.debug("mapping Start ");
		cityVo.setName(cityDB.getCityName());
		// set coordinate info
		Coord coord = new Coord();
		if (cityDB.getCoordinate() != null) {
			Double lat = cityDB.getCoordinate().getLat();
			coord.setLat(lat);
			Double lon = cityDB.getCoordinate().getLon();
			coord.setLon(lon);
		}
		cityVo.setCoord(coord);
		cityVo.setSunrise(cityDB.getSunRise());
		cityVo.setSunset(cityDB.getSunSet());
		cityVo.setCountry(cityDB.getCountryName());
		logger.debug("mapping Finish ");
		return cityVo;
	}

	/**
	 * This function convert a Weather from the model form to the vo form maps
	 * the properties to it's probable match and saves it's values in the vo
	 * form
	 * 
	 * @param cityDB
	 *            to retrieve weather info
	 * @return com.weather.service.vo.Weather weather
	 */
	com.weather.service.vo.Weather getWeatherVo(
			com.weather.persistence.model.City cityDB) {
		String methodName = "getWeatherVo";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		// set weather info
		logger.debug("map info from Weather of model form to Vo form ");
		com.weather.service.vo.Weather weatherVo = new com.weather.service.vo.Weather();
		logger.debug("mapping Start ");
		com.weather.persistence.model.Weather weather = getCurrentWeather(cityDB
				.getWeathers());
		String description = weather.getDescription();
		weatherVo.setDescription(description);
		String main = weather.getMain();
		weatherVo.setMain(main);
		logger.debug("mapping Finish ");
		return weatherVo;
	}

	/**
	 * This function create Main object and retrieve info from cityDB and maps
	 * the properties to it's probable match and saves it's values in the Main
	 * object
	 * 
	 * @param cityDB
	 *            to retrieve weather info
	 * @return com.weather.service.vo.Main
	 */
	com.weather.service.vo.Main getMain(
			com.weather.persistence.model.City cityDB) {
		String methodName = "getMain";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);

		// set Main info
		logger.debug("map info from Main of model form to Vo form ");
		com.weather.service.vo.Main main = new com.weather.service.vo.Main();
		logger.debug("mapping Start ");
		com.weather.persistence.model.Weather tempreture = getCurrentWeather(cityDB
				.getWeathers());
		if (tempreture != null) {
			Double temp = tempreture.getTemp();
			if (temp != null) {
				main.setTemp(temp);
			}
			Double temp_min = tempreture.getTempMin();
			main.setTemp_min(temp_min);
			Double temp_max = tempreture.getTempMax();
			main.setTemp_max(temp_max);
		}
		logger.debug("mapping Finish ");
		return main;
	}

	/**
	 * This function return the Weather object with the most recent date , takes
	 * a list of weathers as a parameters and iterate through it , compare the
	 * date and saves the most recent
	 * 
	 * @param list
	 *            of weathers
	 * @return com.weather.persistence.model.Weather weather that has the most
	 *         recent date
	 */
	public com.weather.persistence.model.Weather getCurrentWeather(
			List<com.weather.persistence.model.Weather> weathers) {
		String methodName = "getLatestWeather";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		com.weather.persistence.model.Weather latestweather = null;
		for (com.weather.persistence.model.Weather o : weathers) {
			logger.debug("weather in list with Date :" + o.getDate());
			if (latestweather == null
					|| o.getDate().compareTo(latestweather.getDate()) > 0) {
				latestweather = o;
			}
		}
		logger.debug("return the most recent weather with Date :"
				+ latestweather.getDate());
		return latestweather;
	}
	/**
	 * This Function checks if string only contains alphabetic with strings
	 * @param string
	 * @return boolean
	 */

	public boolean isAlpha(String s) {
		if(s == null|| s.isEmpty()){
			return false;
		}
		String pattern = "^[a-zA-Z ]*$";
		if (s.matches(pattern)) {
			return true;
		}
		return false;
	}

}
