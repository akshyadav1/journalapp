package net.engineeringdigest.journalapp.service;

import net.engineeringdigest.journalapp.api.response.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiService {

    @Value("${weather.api.key}")
    private String apiKey;
    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiResponse getWeather(String city) {

        String finalApi = API.replace("API_KEY", apiKey).replace("CITY", city);
        ResponseEntity<WeatherApiResponse> weatherApiResponseResponseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherApiResponse.class);
        return weatherApiResponseResponseEntity.getBody();

    }

}
