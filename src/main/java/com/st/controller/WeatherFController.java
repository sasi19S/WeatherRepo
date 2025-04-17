package com.st.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.st.exception.CityNotFoundException;
import com.st.pojo.WeatherResponse;
import com.st.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/weather")
@Tag(name = "Weather API", description = "Get current weather by city")
@CrossOrigin(origins = "*")
public class WeatherFController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	
	@Autowired
	private WeatherService weatherService;
	
	
	
	@Operation(summary = "Get weather by city", description = "Returns temperature and conditions")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success"),
	        @ApiResponse(responseCode = "404", description = "City not found"),
	        @ApiResponse(responseCode = "500", description = "Server error")
	    })
	@GetMapping
	@Cacheable("weather")
	public ResponseEntity<?> getWeather(@RequestParam String city) {
		logger.info("🚀 Fetching from OpenWeather for city: {}", city);
		try {
		WeatherResponse weatherResponse = weatherService.getTemperatureByCity(city);
		return ResponseEntity.ok(weatherResponse);	
		}
		catch (CityNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		}
		catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error","Something went wrong"));
		}
		
	}//method
	
	@CacheEvict(value = "weather", allEntries = true)
	@GetMapping("/clear-cache")
	public String clearCache() {
		return "Cache Cleared";
	}
	
}//class
