package com.example.android.sunshine.app;

import java.util.Date;

public class WeatherData {

    private Date forecastDate;
    private double maxTemperature;
    private double minTemperature;
    private String dailyWeatherStatus;

    public WeatherData() {

    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getDailyWeatherStatus() {
        return dailyWeatherStatus;
    }

    public void setDailyWeatherStatus(String dailyWeatherStatus) {
        this.dailyWeatherStatus = dailyWeatherStatus;
    }
}
