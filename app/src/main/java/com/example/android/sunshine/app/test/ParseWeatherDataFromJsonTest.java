package com.example.android.sunshine.app.test;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;


public class ParseWeatherDataFromJsonTest {
    String forecastJsonStr = null;

    @Before
    public void Before() {
        forecastJsonStr = "{\"city\":{\"id\":5375480,\"name\":\"Mountain View\",\"coord\":{\"lon\":-122.083847,\"lat\":37.386051},\"country\":\"US\",\"population\":0},\"cod\":\"200\",\"message\":0.0151,\"cnt\":7,\"list\":[{\"dt\":1478458800,\"temp\":{\"day\":20.62,\"min\":9.71,\"max\":20.62,\"night\":9.71,\"eve\":19.66,\"morn\":20.62},\"pressure\":994.21,\"humidity\":77,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.5,\"deg\":244,\"clouds\":8},{\"dt\":1478545200,\"temp\":{\"day\":16.69,\"min\":9.91,\"max\":20.69,\"night\":10.29,\"eve\":20.05,\"morn\":9.91},\"pressure\":996.71,\"humidity\":85,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.36,\"deg\":75,\"clouds\":32},{\"dt\":1478631600,\"temp\":{\"day\":18.58,\"min\":7.55,\"max\":22.41,\"night\":9.64,\"eve\":21.44,\"morn\":7.55},\"pressure\":995.65,\"humidity\":76,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"speed\":1.47,\"deg\":71,\"clouds\":8},{\"dt\":1478718000,\"temp\":{\"day\":17.41,\"min\":10.94,\"max\":20.7,\"night\":13.64,\"eve\":20.7,\"morn\":10.94},\"pressure\":1017.02,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":1.69,\"deg\":13,\"clouds\":1},{\"dt\":1478804400,\"temp\":{\"day\":17.72,\"min\":12.17,\"max\":20.65,\"night\":12.87,\"eve\":20.65,\"morn\":12.17},\"pressure\":1015.61,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":1.43,\"deg\":39,\"clouds\":24},{\"dt\":1478890800,\"temp\":{\"day\":17.51,\"min\":11.15,\"max\":18.8,\"night\":12.45,\"eve\":18.8,\"morn\":11.15},\"pressure\":1017.15,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.04,\"deg\":115,\"clouds\":0},{\"dt\":1478977200,\"temp\":{\"day\":15.18,\"min\":11.86,\"max\":18,\"night\":12.67,\"eve\":18,\"morn\":11.86},\"pressure\":1016.4,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.97,\"deg\":328,\"clouds\":48,\"rain\":0.95}]}";

    }

    @Test
    public void testGetWeatherDataFromJson() throws JSONException {

//        List<WeatherData> mWeatherDataList = new ArrayList<>();
//        mWeatherDataList = getWeatherDataFromJson(forecastJsonStr, 7);
//
//        Assert.assertTrue(true);

    }

}