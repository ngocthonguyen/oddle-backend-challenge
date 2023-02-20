package com.oddle.app.weather.services;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.providers.OpenWeatherProvider;
import com.oddle.app.weather.providers.WeatherInfo;
import com.oddle.app.weather.repositories.WeatherRepository;
import com.oddle.app.weather.exceptions.BadRequestException;
import com.oddle.app.weather.exceptions.WeatherNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
public class WeatherService implements IWeatherService {

    @Autowired
    WeatherRepository weatherRepo;

    @Autowired
    OpenWeatherProvider openWeatherProvider;

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

    @Override
    @Scheduled(fixedRate = 1000 * 60 * 5) // 5 minutes
    public void feedCurrentWeatherData() { // TODO Optimize performance by making bulk call instead of unit call for each city
        log.info("Retrieving weather data from provider ...");
        openWeatherProvider.getCityList().forEach(c -> {
            openWeatherProvider.getCurrentWeather(c).ifPresent(currentWeather->{
                LocalDate today = LocalDate.now();
                WeatherEntity weatherEntity = weatherRepo.findByCityNameAndDate(c, today).orElse(new WeatherEntity());
                weatherEntity.setMainCode(currentWeather.weather.get(0).main);
                weatherEntity.setCity(c);
                weatherEntity.setDate(today);
                weatherRepo.save(weatherEntity);
            });
        });
        log.info("Retrieving weather data from provider DONE.");
    }

    private void checkExist(int id) {
        weatherRepo.findById(id).orElseThrow(() -> new WeatherNotFoundException("Weather not found for id " + id));
    }
}