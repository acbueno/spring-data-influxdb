package br.com.acbueno.lodon.service;

import java.time.Instant;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherMetricsService {

  @Value("${influxdb.url}")
  private String influxDbUrl;

  @Value("${influxdb.username}")
  private String influxDbUsername;

  @Value("${influxdb.password}")
  private String influxDbPassword;

  @Value("${influxdb.database}")
  private String influxDbDatabase;

  @Value("${weather.api.key}")
  private String weatherApiKey;

  @Value("${influxdb.token}")
  private String influxDbToken;

  @Value("${influxdb.bucket}")
  private String influxDbBucket;

  @Value("${influxdb.org}")
  private String influxDbOrg;

  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private CityService cityService;

  private InfluxDBClient influxDBClient;

  @PostConstruct
  public void init() {
    this.influxDBClient = InfluxDBClientFactory.create(influxDbUrl, influxDbToken.toCharArray(),
        influxDbOrg, influxDbBucket);
    if (this.influxDBClient != null) {
      log.info("Conexão com InfluxDB está OK.");
    } else {
      log.error("Falha na conexão com InfluxDB.");
    }
  }

  @Scheduled(initialDelay = 2000, fixedRate = 3600000)
  public void fetchWeatherDataForAllCities() {
    collectAndStoreWeatherData();
  }

  public void collectAndStoreWeatherData() {
    WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
    for (String city : cityService.getCityOfRmcSaoPaulo()) {
      String apiUrl = String.format(
          "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city,
          weatherApiKey);
      Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

      if (response != null) {
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        double temperature = (Double) main.get("temp");
        int humidity = (Integer) main.get("humidity");
        //@formatter:off
            Point point = Point.measurement("weather")
                .addTag("city", city)
                .addField("temperature", temperature)
                .addField("humidity", humidity)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            writeApi.writePoint(point);
            //@formtatter:on          
       }
     }   
   }
  
  @PreDestroy
  public void close() {
    if(influxDBClient !=null) {
      influxDBClient.close();
    }
  }
}
