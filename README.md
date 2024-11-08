# Spring Data InfluxDB - Weather Data Monitoring

This project demonstrates how to build a weather data monitoring application using Spring Boot, InfluxDB, and Grafana. The application fetches weather data from an external API, stores it in InfluxDB, and visualizes it with Grafana dashboards.

## Overview

The goal is to create a dynamic, real-time monitoring solution that leverages time-series data. By collecting and visualizing weather metrics, this setup provides valuable insights for tracking climate conditions and trends over time.

## Technologies Used

- **Spring Boot** - Java framework for building REST APIs
- **InfluxDB** - Time-series database optimized for handling real-time data
- **Grafana** - Visualization tool for creating real-time dashboards

## Getting Started

### Prerequisites

Ensure you have the following installed on your system:

- Docker
- Java 17 or later
- Maven

### Cloning the Repository

```bash
git clone https://github.com/acbueno/spring-data-influxdb.git
cd spring-data-influxdb
```
### Setting Up InfluxDB and Grafana
The project includes a docker-compose.yml file to set up InfluxDB and Grafana containers quickly.

#### Docker Compose Configuration
```bash
version: '3'

services:
  influxdb:
    image: influxdb:2.0
    container_name: influxdb_container
    ports:
      - "8086:8086"
    volumes:
      - ./influxdb_data:/var/lib/influxdb2
    environment:
      - INFLUXDB_ADMIN_USER=acbueno
      - INFLUXDB_ADMIN_PASSWORD=password123
      - INFLUXDB_ORG=acbueno
      - INFLUXDB_BUCKET=weather_data
    networks:
      - shared_network

  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    networks:
      - shared_network

networks:
  shared_network:
    driver: bridge
```
#### Start the Containers:
```bash
docker-compose up -d
```
### Access InfluxDB:

Open your browser and go to http://localhost:8086.
Log in with the credentials provided in docker-compose.yml.
Access Grafana:

### Open Grafana at http://localhost:3000.
Log in with admin as both username and password (or as configured).
Setting Up the Application
1. Configure application.properties
Update the src/main/resources/application.properties file with your InfluxDB details:

```bash
spring.application.name=spring-data-influxdb
influxdb.url=http://localhost:8086
influxdb.token=your_influxdb_token_here
influxdb.bucket=weather_data
influxdb.org=acbueno
weather.api.key=your_openweather_api_key
```

### Setting Up InfluxDB and Grafana
The project includes a docker-compose.yml file to set up InfluxDB and Grafana containers quickly.

Docker Compose Configuration yaml
```bash
version: '3'

services:
  influxdb:
    image: influxdb:2.0
    container_name: influxdb_container
    ports:
      - "8086:8086"
    volumes:
      - ./influxdb_data:/var/lib/influxdb2
    environment:
      - INFLUXDB_ADMIN_USER=acbueno
      - INFLUXDB_ADMIN_PASSWORD=password123
      - INFLUXDB_ORG=acbueno
      - INFLUXDB_BUCKET=weather_data
    networks:
      - shared_network

  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    networks:
      - shared_network

networks:
  shared_network:
    driver: bridge
Start the Containers:
```

```bash
docker-compose up -d
```
### Access InfluxDB:

#### Open your browser and go to http://localhost:8086.
Log in with the credentials provided in docker-compose.yml.
Access Grafana:

#### Open Grafana at http://localhost:3000.
 Log in with admin as both username and password (or as configured).
#### Setting Up the Application
 1. Configure application.properties
Update the src/main/resources/application.properties file with your InfluxDB details:

 2. Add API Key for OpenWeather
Get a free API key from OpenWeather and add it to the weather.api.key property.

3. Run the Application
With everything configured, you can start the Spring Boot application:
```bash
mvn spring-boot:run
```
The application will periodically fetch weather data for cities in São Paulo's Metropolitan Region, storing it in InfluxDB.

1. Viewing Data in Grafana
   * Connect Grafana to InfluxDB:
   * In Grafana, go to Configuration > Data Sources.
    * URL: http://influxdb:8086
     * Auth: Enable, and use your token from InfluxDB.
      * Organization: acbueno
      * Bucket: weather_data
2. Create Dashboards:
   * In Grafana, go to Create > Dashboard and start adding visualizations.
   * Use temperature and humidity as metrics for your graphs.
### Project Structure
* CityService - Provides a list of cities in São Paulo's Metropolitan Region for weather data collection.
* WeatherMetricsService - Handles fetching data from OpenWeather API and saving it to InfluxDB.
* Model - Defines the WeatherData model for weather metrics.

### License
This project is open-source and available under the MIT License.
  






