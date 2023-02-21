package com.oddle.app.weather.providers;

import java.util.Optional;
import java.util.Set;

public interface IOpenWeatherProvider {
    Set<String> getCityList();

    Optional<WeatherInfo> getCurrentWeather(String city);
}
