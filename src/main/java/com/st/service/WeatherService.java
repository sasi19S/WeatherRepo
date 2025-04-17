package com.st.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.st.exception.CityNotFoundException;
import com.st.pojo.WeatherResponse;

import org.json.JSONObject;



@Service
public class WeatherService {
	
	private final String apiKey = "1df9fcbc98038309f1aeafe7de7c5262";
	private final WebClient webClient = WebClient.create("https://api.openweathermap.org");

	public WeatherResponse getTemperatureByCity(String city) {
		try {
		String response = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/data/2.5/weather")
						.queryParam("q", city)
						.queryParam("appid", apiKey)
						.queryParam("units", "metric")
						.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		JSONObject json = new JSONObject(response);
		double temp = json.getJSONObject("main").getDouble("temp");
		String description  = json.getJSONArray("weather").getJSONObject(0).getString("description");
		
		//System.out.println(city+" "+temp+" "+description); 
		
		
		
		//Alt + 0176 = °
		return new WeatherResponse(city, temp+"°C", description);
		}
		catch (WebClientResponseException e) {
			throw new CityNotFoundException("City Not Found "+city);
		}
		catch(Exception e) {
			throw new RuntimeException("Unable to fetch weather data");
		}
	}//method
}//class
