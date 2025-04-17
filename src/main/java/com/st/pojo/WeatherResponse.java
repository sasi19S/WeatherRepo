package com.st.pojo;

public class WeatherResponse {
	
	private String city;
	private String temperature;
	private String description;
	
	public WeatherResponse(String city, String temperature, String description) {
	//	super();
		this.city = city;
		this.temperature = temperature;
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	

}
