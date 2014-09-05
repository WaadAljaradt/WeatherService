package com.weather.service.rest;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.weather.bo.WeatherServiceManager;
import com.weather.service.vo.WeatherInfo;

@Produces("application/json")
@Path("/weather")
public class WeatherService {
	@Autowired
	WeatherServiceManager serviceManager;

	@GET
	@Path("/current")
	public Response getWeather(@QueryParam("q") String cityName)
			throws Exception {
		WeatherInfo info = serviceManager.getWeatherInfo(cityName);
		if (info.getCode() != null && info.getCode() == 404) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Error: city not found").build();
		}
		return Response.ok().entity(info).build();

	}

}
