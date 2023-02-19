package com.oddle.app.weather.dao;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.repositories.WeatherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WeatherRepositoryTest {

    private static final String CODE = "CODE";
    private static final String UPDATED_CODE = "UPDATED_CODE";

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void should_findByCityName() {
        //Given
        WeatherEntity weather = givenWeather();
        //When
        weather = weatherRepository.save(weather);

        Optional<WeatherEntity> actual = weatherRepository.findByCityNameAndDate("Name", LocalDate.now());
        //Then
        //WeatherEntity actual = testEntityManager.find(WeatherEntity.class, weather.getId());
        assertEquals(actual.get(), weather);
    }

    private WeatherEntity givenWeather() {
        WeatherEntity weather = new WeatherEntity();
        weather.setMainCode(CODE);
        weather.setCity("name");
        weather.setDate(LocalDate.now());
        return weather;
    }
}
