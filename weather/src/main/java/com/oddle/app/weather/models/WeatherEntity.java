package com.oddle.app.weather.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_weather")
public class WeatherEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //TODO Put city data into tbl_city table
    private String city;
    private LocalDate date;

    private String mainCode;
    private String mainDescription;//": "light intensity drizzle",
    private String mainIcon;//": "09d"
    private String temp;//": 280.32,
    private String pressure;//": 1012,
    private String humidity;//": 81,
    private String tempMin;//": 279.15,
    private String tempMax;//": 281.15
    private String visibility;//": 10000,
    private String wind;//": {
    private String windSpeed;//": 4.1,
    private String windDeg;//": 80
    private String cloudsAll;//": 90
}