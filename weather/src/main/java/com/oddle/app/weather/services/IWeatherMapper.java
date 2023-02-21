package com.oddle.app.weather.services;

import com.oddle.app.weather.dto.Weather;
import com.oddle.app.weather.models.WeatherEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IWeatherMapper {

    WeatherEntity mapToWeatherEntity(Weather weather);

    Weather mapToWeather(WeatherEntity weatherEntity);
}
