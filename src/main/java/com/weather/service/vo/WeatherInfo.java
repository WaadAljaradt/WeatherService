package com.weather.service.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class WeatherInfo {
	
	public String date;
	public City city;
	public Weather weather;
	public Main main;
	public Integer code;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "WeatherInfo [date=" + date + ", city=" + city.toString() + ", weather="
				+ weather.toString() + ", main=" + main.toString()  + "]";
	}
	

}
