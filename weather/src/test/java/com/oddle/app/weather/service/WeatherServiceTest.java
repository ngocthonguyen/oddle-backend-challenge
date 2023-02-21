package com.oddle.app.weather.service;

import com.oddle.app.weather.models.WeatherEntity;
import com.oddle.app.weather.repositories.WeatherRepository;
import com.oddle.app.weather.exceptions.WeatherNotFoundException;
import com.oddle.app.weather.services.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest extends Assertions {

    private static final int ID = 1;
    private static final String MAIN_CODE = "MAIN_CODE";

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherRepository weatherRepositoryMock;

    @Test
    public void should_find_weather_by_id() {
        //Given
        WeatherEntity weather = givenExistingWeather();
        when(weatherRepositoryMock.findById(ID)).thenReturn(Optional.of(weather));
        //When
        //Then
        assertEquals(Optional.of(weather), weatherService.findById(ID));
    }

    @Test
    public void should_return_empty_find_weather_by_id() {
        //Given
        when(weatherRepositoryMock.findById(ID)).thenReturn(Optional.empty());
        //When
        //Then
        assertEquals(Optional.empty(), weatherService.findById(ID));
    }

    @Test
    public void should_add_weather() {
        //Given
        WeatherEntity weather = givenExistingWeather();
        when(weatherRepositoryMock.save(weather)).thenReturn(weather);
        //When
        //Then
        assertEquals(weather, weatherService.add(weather));
    }

    @Test
    public void should_update_weather() {
        //Given
        WeatherEntity weather = givenExistingWeather();
        when(weatherRepositoryMock.findById(weather.getId())).thenReturn(Optional.of(weather));
        when(weatherRepositoryMock.save(weather)).thenReturn(weather);
        //When
        //Then
        assertEquals(weather, weatherService.update(weather));
    }

    @Test
    public void should_throw_WeatherNotFoundException_update_weather() {
        //Given
        WeatherEntity weather = givenExistingWeather();
        when(weatherRepositoryMock.findById(weather.getId())).thenReturn(Optional.empty());
        //When
        //Then
        assertThrows(WeatherNotFoundException.class, () -> weatherService.update(weather));
    }

    @Test
    public void should_throw_exception_delete_weather_by_id() {
        //Given
        when(weatherRepositoryMock.findById(ID)).thenReturn(Optional.empty());
        //When
        //Then
        assertThrows(WeatherNotFoundException.class, () -> weatherService.deleteById(ID));
        verify(weatherRepositoryMock, times(0)).deleteById(ID);
    }

    @Test
    public void should_delete_weather_by_id() {
        //Given
        WeatherEntity weather = givenExistingWeather();
        when(weatherRepositoryMock.findById(weather.getId())).thenReturn(Optional.of(weather));
        doNothing().when(weatherRepositoryMock).deleteById(ID);
        //When
        weatherService.deleteById(ID);
        //Then
        verify(weatherRepositoryMock, times(1)).deleteById(ID);
    }

    private WeatherEntity givenExistingWeather() {
        WeatherEntity weather = new WeatherEntity();
        weather.setId(ID);
        weather.setMainCode(MAIN_CODE);
        return weather;
    }

}
