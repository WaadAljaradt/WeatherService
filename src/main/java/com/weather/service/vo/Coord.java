package com.weather.service.vo;

public class Coord {
	public Double lat;
	public Double lon;
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Coord [lat=" + lat + ", lon=" + lon + "]";
	}
	

}
