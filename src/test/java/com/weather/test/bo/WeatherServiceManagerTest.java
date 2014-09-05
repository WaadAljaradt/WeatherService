package com.weather.test.bo;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.weather.bo.WeatherServiceManager;
import com.weather.bo.openweather.WeatherRequestManager;
import com.weather.service.vo.WeatherInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= { "/test-spring-ctx.xml" })
@TransactionConfiguration( transactionManager ="transactionManager", defaultRollback = true)
@Transactional
public class WeatherServiceManagerTest {
	@Autowired
	WeatherServiceManager serviceManager;
	@Autowired
	WeatherRequestManager request;

	@Test
	public void test() throws Exception {
		WeatherInfo info =serviceManager.getWeatherInfo("dubai");
		Assert.assertNotNull(info.getCity());
		
		
	}

}
