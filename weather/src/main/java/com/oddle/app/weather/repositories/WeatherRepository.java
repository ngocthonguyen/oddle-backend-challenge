package com.oddle.app.weather.repositories;

import com.oddle.app.weather.models.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    @Query("SELECT w FROM WeatherEntity w WHERE upper(w.city) = upper(?1) and date = ?2")
    Optional<WeatherEntity> findByCityNameAndDate(String cityName, LocalDate date);
}