package com.oddle.app.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private Integer id;
    private String city;
    @JsonFormat(pattern="dd.MM.yyyy")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private LocalDate date;
    private String mainCode;
    private String mainDescription;
    private String mainIcon;
    private String temp;
    private String pressure;
    private String humidity;
    private String tempMin;
    private String tempMax;
    private String visibility;
    private String wind;
    private String windSpeed;
    private String windDeg;
    private String cloudsAll;
}