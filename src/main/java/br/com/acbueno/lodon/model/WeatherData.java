package br.com.acbueno.lodon.model;

import lombok.Data;

@Data
public class WeatherData {

  private String city;
  private double temperature;
  private double humidity;

}
