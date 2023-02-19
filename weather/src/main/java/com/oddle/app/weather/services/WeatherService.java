package com.oddle.app.weather.services;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.repositories.WeatherRepository;
import com.oddle.app.weather.exceptions.BadRequestException;
import com.oddle.app.weather.exceptions.WeatherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService implements IWeatherService {

    @Autowired
    WeatherRepository weatherRepo;

    @Override
    public Optional<WeatherEntity> findById(int id) {
        return weatherRepo.findById(id);
    }

    public Optional<WeatherEntity> findByCityAndDate(String cityName, LocalDate date) {
        return weatherRepo.findByCityNameAndDate(cityName, date);
    }

    @Override
    public WeatherEntity add(WeatherEntity weatherEntity) {
        weatherEntity.setId(null);
        if (weatherRepo.findByCityNameAndDate(weatherEntity.getCity(), weatherEntity.getDate()).isPresent()) {
            throw new BadRequestException("Weather already exists for " + weatherEntity.getCity() + " on " + weatherEntity.getDate());
        }
        return weatherRepo.save(weatherEntity);
    }

    @Override
    public WeatherEntity update(WeatherEntity weatherEntity) {
        checkExist(weatherEntity.getId());
        Optional<WeatherEntity> weather = weatherRepo.findByCityNameAndDate(weatherEntity.getCity(), weatherEntity.getDate());
        if (weather.isPresent() && weather.get().getId() != weatherEntity.getId()) {
            throw new BadRequestException("Weather already exists for " + weatherEntity.getCity() + " on " + weatherEntity.getDate());
        }
        return weatherRepo.save(weatherEntity);
    }

    @Override
    public void deleteById(int id) {
        checkExist(id);
        weatherRepo.deleteById(id);
    }

    private void checkExist(int id) {
        weatherRepo.findById(id).orElseThrow(() -> new WeatherNotFoundException("Weather not found for id " + id));
    }
}