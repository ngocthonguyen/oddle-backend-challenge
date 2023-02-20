package com.oddle.app.weather.controller;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.dto.Weather;
import com.oddle.app.weather.exceptions.WeatherNotFoundException;
import com.oddle.app.weather.services.IWeatherService;
import com.oddle.app.weather.services.IWeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/weathers")
public class WeatherController {

    @Autowired
    IWeatherService weatherService;

    @Autowired
    IWeatherMapper mapper;

    @GetMapping(value = "/{id}")
    public Weather getWeatherById(@PathVariable("id") int id) {
        return weatherService.findById(id)
                .map(mapper::mapToWeather)
                .orElseThrow(() -> new WeatherNotFoundException("Weather with " + id + " is Not Found!"));
    }

    @GetMapping(value = "")
    public Weather getWeatherByCityAndDate(@RequestParam String city,
                                        @RequestParam(required = false) @DateTimeFormat(pattern="dd.MM.yyyy") LocalDate date) {

        return weatherService.findByCityAndDate(city, Optional.ofNullable(date).orElse(LocalDate.now()))
                .map(mapper::mapToWeather)
                .orElseThrow(() -> new WeatherNotFoundException("No weather found for " + city));
    }

    @PostMapping(value = "")
    public Weather addWeather(@RequestBody Weather weather) {
        WeatherEntity weatherEntity = mapper.mapToWeatherEntity(weather);
        return mapper.mapToWeather(weatherService.add(weatherEntity));
    }

    @PutMapping("/{id}")
    public Weather updateWeather(@PathVariable int id, @RequestBody Weather weather) {
        WeatherEntity weatherEntity = mapper.mapToWeatherEntity(weather);
        weatherEntity.setId(id);
        return mapper.mapToWeather(weatherService.update(weatherEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteWeatherById(@PathVariable int id) {
        weatherService.deleteById(id);
    }

    @GetMapping("/runSchedule")
    public void runSchedule(){
        this.weatherService.feedCurrentWeatherData();
    }
}