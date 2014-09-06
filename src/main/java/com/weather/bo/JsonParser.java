package com.weather.bo;

import java.text.DecimalFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weather.persistence.model.City;
import com.weather.persistence.model.Coordinate;
import com.weather.persistence.model.Weather;

@Component
public class JsonParser {

	private static Logger logger = LoggerFactory.getLogger(JsonParser.class);
	private String className = "JsonParser";

	/**
	 * this function parse the Json response to City and coordinate and weather
	 * objects to be later saved to data base, retrieve info from the response
	 * and match it to properties in models
	 * 
	 * @param response
	 *            : Json response from the openWeather
	 * @param flag
	 *            : this flag is used indicate first retrieving info for
	 *            coordinate since this value cannot be changed
	 * @return City : filled with proper info
	 */
	public City ParseToCity(Response response, Integer flag) {
		String methodName = "ParseToCity";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug("convert Json response to String");
		String strResponse = ResponseToString(response);
		logger.debug("Json to String :" + strResponse);
		JSONObject jsnObj = new JSONObject(strResponse);
		logger.debug("start parsing :");
		City city = new City();
		city.setCityName(jsnObj.getString("name"));
		logger.debug("city Name :" + city.getCityName());
		// get coordinate info if City is inserted for the first time
		if (flag == 0) {
			JSONObject coordjsn = jsnObj.getJSONObject("coord");
			Coordinate coordinate = new Coordinate();
			coordinate.setLat(coordjsn.getDouble("lat"));
			logger.debug("coord Lat:  :" + coordinate.getLat());
			coordinate.setLon(coordjsn.getDouble("lon"));
			logger.debug("coord Lon:  :" + coordinate.getLon());
			coordinate.setCity(city);
			city.setCoordinate(coordinate);
			logger.debug("coord is saved to Data base  :Success");
		}

		// get weather info
		JSONArray array = jsnObj.getJSONArray("weather");
		JSONObject weatherjsn = array.getJSONObject(0);
		Weather weather = new Weather();
		weather.setDescription(weatherjsn.getString("description"));
		logger.debug("weather  description:  :" + weather.getDescription());
		weather.setMain(weatherjsn.getString("main"));
		logger.debug("weather  main:  :" + weather.getMain());
		weather.setDate(new Date());
		logger.debug("weather  Date:  :" + weather.getDate());
		weather.setCity(city);
		JSONObject tempjsn = jsnObj.getJSONObject("main");
		weather.setTemp(kelvinToCelsius(tempjsn.getDouble("temp")));
		logger.debug("weather  temp:  :" + weather.getTemp());
		weather.setTempMax(kelvinToCelsius(tempjsn.getDouble("temp_max")));
		logger.debug("weather  temp:  :" + weather.getTempMax());
		weather.setTempMin(kelvinToCelsius(tempjsn.getDouble("temp_min")));
		logger.debug("weather  temp:  :" + weather.getTempMin());
		city.getWeathers().add(weather);
		// set sunrise and sunset

		JSONObject sysjsn = jsnObj.getJSONObject("sys");
		city.setSunRise(sysjsn.getInt("sunrise"));
		logger.debug("sunrise  :  :" + city.getSunRise());
		city.setSunSet(sysjsn.getInt("sunset"));
		logger.debug("sunset  :  :" + city.getSunSet());
		city.setCountryName(sysjsn.getString("country"));
		return city;

	}

	/**
	 * This function is used to indicate if the response from openWeather is an
	 * Error response or not since openWeather sends response with status of
	 * success (200) when city is not found
	 * 
	 * @param response
	 *            : Json response form openweather
	 * @return : true if response is Error and city is not found, return false
	 *         if message is not error and cod in the message = 404
	 */
	public boolean isErrorResponse(Response response) {
		String methodName = "isErrorResponse";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug("convert Json response to String");

		String strResponse = ResponseToString(response);
		logger.debug("Json to String :" + strResponse);
		JSONObject jsnObj = new JSONObject(strResponse);
		try {
			logger.debug("try to get Message value if exsits");
			// String error = jsnObj.getString("message");
			Integer cod = jsnObj.getInt("cod");
			if (cod == 404) {
				logger.debug("this response is an error Message ");
				return true;
			}
		} catch (Exception e) {
			logger.debug("this response is not an error Message ");

		}
		return false;

	}

	/**
	 * convert Json Response to String entity
	 * 
	 * @param response
	 *            : json response
	 * @return String response in string
	 */

	private String ResponseToString(Response response) {
		logger.info("In: " + className);
		String methodName = "ResponseToString";
		logger.debug("In :" + methodName);
		logger.debug("convert Json response to String");
		return response.readEntity(String.class);
	}
	/**
	 * This function convert degree from Kelvin To Celsius
	 * @param temp temperature Celsius
	 * @return : Double temperature Kelvin
	 */
	private Double kelvinToCelsius(Double temp){
		logger.debug("In kelvinToCelsius param : "+ temp);
		Double celTemp =temp -  273;
		DecimalFormat df = new DecimalFormat("#.0000");
		celTemp = Double.valueOf(df.format(celTemp));
		logger.debug(" return Temp :"+ celTemp);
		return celTemp;
		
	}

}
