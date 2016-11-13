package com.example.android.sunshine.app;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ParseWeatherDataFromJson {

    private static final String LOG_TAG = ParseWeatherDataFromJson.class.getSimpleName();


    public static String[] getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {
        JSONObject jsonObject = new JSONObject(forecastJsonStr);
        JSONArray weatherList = jsonObject.getJSONArray("list");

//        List<WeatherData> mWeatherDataList = new ArrayList<>();

        String[] dailyForecastsInPeriod = new String[numDays];

        for (int i = 0; i < weatherList.length(); i++) {
//            WeatherData weatherData = new WeatherData();
            long dateTime;
            String day;
            String description;
            double maxTemp;
            double minTemp;
            JSONObject dayForecastObject = weatherList.getJSONObject(i);
            dateTime = dayForecastObject.getLong("dt");

            day = getReadableDateString(dateTime);
            JSONObject weatherObject = dayForecastObject.getJSONArray("weather").getJSONObject(0);
            description = weatherObject.getString("main");

            JSONObject temperatureObject = dayForecastObject.getJSONObject("temp");
            maxTemp = temperatureObject.getDouble("max");
            minTemp = temperatureObject.getDouble("min");

            dailyForecastsInPeriod[i] = day + " - " + description + " " + Math.round(maxTemp) + "/" + Math.round(minTemp);

        }

        for (String s :
                dailyForecastsInPeriod) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }
        return dailyForecastsInPeriod;


    }

    private static String getReadableDateString(long dateTime) {
        Date date = new Date(dateTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd");
        format.setTimeZone(TimeZone.getDefault());

        return format.format(dateTime);
    }
}
