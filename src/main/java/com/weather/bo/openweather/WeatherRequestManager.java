package com.weather.bo.openweather;

import java.util.ArrayList;


import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.stereotype.Component;

@Component
public class WeatherRequestManager {

	private static Logger logger = LoggerFactory
			.getLogger(WeatherRequestManager.class);
	private String className = "WeatherRequestManager";

	/**
	 * this function calls openWeather service to retrieve weather info for the
	 * given cityName
	 * 
	 * @param cityName
	 *            : given CityName
	 * @return Json response from openWeather
	 */
	public Response getWeather(String cityName) {
		String methodName = "getWeather";
		logger.info("In: " + className);
		logger.debug("In :" + methodName);
		logger.debug("retrieve info from openWeather service for city :"
				+ cityName);
		List providers = new ArrayList();
		providers.add(new JacksonJaxbJsonProvider());
		String baseUrl = "http://api.openweathermap.org/data/2.5";
		WebClient client = WebClient.create(baseUrl, providers);
		client = client.accept("application/json").type("application/json")
				.path("/weather").query("q", cityName);
		logger.debug("return Response with status " + client.get().getStatus());
		return client.get();
	}
}
