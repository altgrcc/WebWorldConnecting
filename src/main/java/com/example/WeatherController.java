package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final String WEATHER_API = "https://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY = "5a7876a58dcdee34f7cfd242e8126b33";

    @GetMapping()
    public String getWeatherByCoordinates(@RequestParam String latitude, @RequestParam String longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = WEATHER_API + "?lat=" + latitude + "&lon=" + longitude 
            + "&units=metric" + "&appid=" + API_KEY;
        String result = restTemplate.getForObject(url, String.class);
        if (result != null) {
            return result;
        }
        return "Error: Unable to fetch weather data";
    }

    @GetMapping("/city")
    public String getWeatherByCity(@RequestParam String city, @RequestParam String country) {
        RestTemplate restTemplate = new RestTemplate();
        

        String locationUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "," + country + "&limit=1&appid=" + API_KEY;
        WeatherRequest[] locationResult = restTemplate.getForObject(locationUrl, WeatherRequest[].class);
        
        if (locationResult == null || locationResult.length == 0) {
            return "Error: City not found";
        }

        WeatherRequest location = locationResult[0];

        String weatherUrl = WEATHER_API + "?lat=" + location.getLat() + "&lon=" + location.getLon() 
            + "&units=metric" + "&appid=" + API_KEY;
        String result = restTemplate.getForObject(weatherUrl, String.class);
        
        if (result != null) {
            return result;
        }
        return "Error: Unable to fetch weather data";
    }
}
