package com.weather.service.vo;

public class City {

	public String name;
	public String country;
	public Integer sunrise;
	public Integer sunset;
	public Coord coord;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getSunrise() {
		return sunrise;
	}

	public void setSunrise(Integer sunrise) {
		this.sunrise = sunrise;
	}

	public Integer getSunset() {
		return sunset;
	}

	public void setSunset(Integer sunset) {
		this.sunset = sunset;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", country=" + country + ", sunrise="
				+ sunrise + ", sunset=" + sunset + ", coord=" + coord.toString() + "]";
	}
	

}
