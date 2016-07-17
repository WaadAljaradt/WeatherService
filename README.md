WeatherService
==============

Get WeatherInfo by city Name
RESTful web service that retrieves weather information from the DB for the provided city in

the query string in the current day and returns the result as JSON. If the weather information for the current day is

not available in the DB, then it get the weather information from http://openweathermap.org/current,

and store the result back to the DB.  weather information by city name

Example of the http request: http://www.example.com/weather/current?q=dubai

Technologies :
1- Spring framework
2- All CRUD operations are done using JPA / Hibernate

Please refer to the documentation for more details 
