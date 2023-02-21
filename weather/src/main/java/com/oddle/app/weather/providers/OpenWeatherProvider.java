package com.oddle.app.weather.providers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class OpenWeatherProvider implements IOpenWeatherProvider {

    @Value("${weather.technical.open_weather_map.url}")
    private String openWeatherUrl;

    @Value("${weather.technical.open_weather_map.api_key}")
    private String openWeatherApiKey;

    @Value("#{'${weather.technical.open_weather_map.city_list}'.split(',')}")
    private List<String> cities;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Set<String> getCityList() {
        return new HashSet<>(cities);
    }

    @Override
    public Optional<WeatherInfo> getCurrentWeather(String city) {
        String url = String.format(openWeatherUrl, city, openWeatherApiKey);
        try {
            ResponseEntity<WeatherInfo> responseEntity = restTemplate.getForEntity(url, WeatherInfo.class);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return Optional.of(responseEntity.getBody());
            } else {
                log.warn("Error while retrieving data for {} from OpenWeatherMap. HttpCode = {}", city, responseEntity.getStatusCode());
                return Optional.empty();
            }
        } catch (Exception ex) {
            log.warn("Error while retrieving data for {} from OpenWeatherMap: {}", city, ex.getMessage());
            return Optional.empty();
        }
    }
}
