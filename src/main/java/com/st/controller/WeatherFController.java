package com.st.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.st.exception.CityNotFoundException;
import com.st.pojo.WeatherResponse;
import com.st.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
//@RestController
//@RequestMapping("/weather")
@Tag(name = "Weather API", description = "Get current weather by city")
public class WeatherFController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	
	@Autowired
	private WeatherService weatherService;
	
	
	@GetMapping("/")
    public String home() {
        return "index";
    }
	
	@Operation(summary = "Get weather by city", description = "Returns temperature and conditions")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success"),
	        @ApiResponse(responseCode = "404", description = "City not found"),
	        @ApiResponse(responseCode = "500", description = "Server error")
	    })
	@GetMapping("/weather")
	@Cacheable("weather")
    public String getWeather(@RequestParam String city, Model model) {
        try {
            WeatherResponse weather = weatherService.getTemperatureByCity(city);
            model.addAttribute("weather", weather);
        } catch (CityNotFoundException e) {
            model.addAttribute("error", "City not found: " + city);
        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong");
        }
        return "index"; // Returns to index.html with data
    }
	
	@CacheEvict(value = "weather", allEntries = true)
	@GetMapping("/clear-cache")
	public String clearCache() {
		return "Cache Cleared";
	}
	
}//class
