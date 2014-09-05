package com.weather.service.vo;

public class Main {
	public Double temp;
	public Double temp_min;
	public Double temp_max;
	public Double getTemp() {
		return temp;
	}


	public void setTemp(Double temp) {
		this.temp = temp;
	}


	public Double getTemp_min() {
		return temp_min;
	}


	public void setTemp_min(Double temp_min) {
		this.temp_min = temp_min;
	}


	public Double getTemp_max() {
		return temp_max;
	}


	public void setTemp_max(Double temp_max) {
		this.temp_max = temp_max;
	}


	@Override
	public String toString() {
		return "Main [temp=" + temp + ", temp_min=" + temp_min + ", temp_max="
				+ temp_max + "]";
	}
	
}
