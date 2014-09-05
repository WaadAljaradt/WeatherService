package com.weather.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id", nullable = false, updatable = false)
	private Integer id;
	@Column(name = "city_name", unique = true)
	private String cityName;
	@Column(name = "country_name")
	private String countryName;
	@Column(name = "sun_rise")
	private Integer sunRise;
	@Column(name = "sun_set")
	private Integer sunSet;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "city", cascade = CascadeType.ALL)
	private Coordinate coordinate;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "city", cascade = CascadeType.ALL)
	private List<Weather> weathers = new ArrayList<Weather>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getSunRise() {
		return sunRise;
	}

	public void setSunRise(Integer sunRise) {
		this.sunRise = sunRise;
	}

	public Integer getSunSet() {
		return sunSet;
	}

	public void setSunSet(Integer sunSet) {
		this.sunSet = sunSet;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<Weather> getWeathers() {
		return weathers;
	}

	public void setWeathers(List<Weather> weathers) {
		this.weathers = weathers;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof City) {
			City toCompare = (City) o;
			return this.getId() == toCompare.getId();
		}
		return false;
	}

}
