package com.oddle.app.weather.services;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.exceptions.WeatherNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

public interface IWeatherService {

    /**
     * Find WeatherEntity by ID
     * @param id
     * @return WeatherEntity for given ID if exist, else Optional.empty
     */
    Optional<WeatherEntity> findById(int id);

    /**
     * Find WeatherEntity by city Name
     * @param cityName
     * @param date
     * @return WeatherEntity for given city if exist, else Optional.empty
     */
    Optional<WeatherEntity> findByCityAndDate(String cityName, LocalDate date);

    /**
     * Add new WeatherEntity
     * @param weatherEntity
     * @return new WeatherEntity
     */
    WeatherEntity add(WeatherEntity weatherEntity);

    /**
     * Update WeatherEntity if exist, throw WeatherNotFoundException if not found
     * @param weatherEntity
     * @return up-to-date WeatherEntity
     */
    WeatherEntity update(WeatherEntity weatherEntity) throws WeatherNotFoundException;

    /**
     * Delete weather entity by ID if exist, throw WeatherNotFoundException if not found
     * @param id
     */
    void deleteById(int id) throws WeatherNotFoundException;
}