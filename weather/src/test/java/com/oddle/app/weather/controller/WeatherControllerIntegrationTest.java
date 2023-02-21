package com.oddle.app.weather.controller;

import com.oddle.app.weather.WeatherApplication;
import com.oddle.app.weather.controller.helper.HttpHelper;
import com.oddle.app.weather.dto.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerIntegrationTest {

    private static final String BASIC_URL = "http://localhost:%s/weathers";
    private static final String BASIC_ID_URL = "http://localhost:%s/weathers/%s";

    private static final int ID = 10;

    private static final String CODE = "CODE";
    private static final String NEW_CODE = "NEW_CODE";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void should_find_weather_by_id() {
        //Given
        Integer id = createWeather().getBody().getId();
        String url = String.format(BASIC_ID_URL, port, id);
        HttpEntity<String> request = HttpHelper.getHttpEntity();
        //When
        ResponseEntity<Weather> response = testRestTemplate.exchange(url, HttpMethod.GET, request, Weather.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), id);
        assertEquals(response.getBody().getMainCode(), CODE);
    }

    @Test
    public void should_add_new_weather() {
        //Given
        ResponseEntity<Weather> response = createWeather();
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getMainCode(), CODE);
    }

    private ResponseEntity<Weather> createWeather() {
        return testRestTemplate.exchange(String.format(BASIC_URL, port), HttpMethod.POST, HttpHelper.getHttpEntity(givenCreateRequest()), Weather.class);
    }

    @Test
    public void should_update_weather() {
        //Given
        Integer id = createWeather().getBody().getId();
        String url = String.format(BASIC_ID_URL, port, id);
        ResponseEntity<Weather> response = testRestTemplate.exchange(url, HttpMethod.PUT, HttpHelper.getHttpEntity(givenWeather()), Weather.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals(NEW_CODE, response.getBody().getMainCode());
    }

    @Test
    public void should_delete_weather_by_id() {
        //Given
        Integer id = createWeather().getBody().getId();
        String url = String.format(BASIC_ID_URL, port, id);
        HttpEntity<String> request = HttpHelper.getHttpEntity();
        //When
        ResponseEntity<Weather> response = testRestTemplate.exchange(url, HttpMethod.DELETE, request, Weather.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }

    private Weather givenCreateRequest() {
        Weather createRequest = new Weather();
        createRequest.setMainCode(CODE);
        return createRequest;
    }

    private Weather givenWeather() {
        Weather weatherDto = new Weather();
        weatherDto.setMainCode(NEW_CODE);
        return weatherDto;
    }
}
