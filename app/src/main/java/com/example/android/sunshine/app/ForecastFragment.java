package com.example.android.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ForecastFragment extends Fragment {

    private ArrayAdapter mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new FetchWeatherTask().execute();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> forecastArrayList = new ArrayList<>();
        forecastArrayList.add("Mon 31/10 - Sunny 29º/16º");
        forecastArrayList.add("Tue 1/11 - Stormy 30º/18º");
        forecastArrayList.add("Wed 2/11 - Stormy 32º/19º ");
        forecastArrayList.add("Thu 3/11 - Cloudy 30º/21º");
        forecastArrayList.add("Fri 4/11 - Cloudy 24º/10º ");

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecastArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

        new FetchWeatherTask().execute("94043");

        return rootView;

    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        private int numDays = 7;

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String locationPostcode = params[0];
            String forecastJsonStr;
            forecastJsonStr = OpenWeatherAPIConnection(locationPostcode);

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String[] result) {

            if (result != null) {
                mForecastAdapter.clear();
                for (String dayForecastStr :
                        result) {
                    mForecastAdapter.add(dayForecastStr);
                }

            }
            super.onPostExecute(result);


        }

        private String OpenWeatherAPIConnection(String locationPostcode) {

            String forecastJsonStr = null;

            HttpURLConnection connection = null;

            try {
                Uri uriBuilder = buildURL(locationPostcode);

                URL url = new URL(uriBuilder.toString());
                Log.d(LOG_TAG, "URL: " + url);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                Log.v(LOG_TAG, "Response code: " + responseCode);


                InputStream inputStream = connection.getInputStream();
                //forecastJsonStr = readStream(inputStream);
                if (inputStream != null) {
                    /** If we get response for our request
                     * buffer the data and store it as a String.
                     */
                    StringBuffer buffer = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    buffer.append(reader.readLine());
//                    while ((line = reader.readLine()) != null) {
//                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                        // But it does make debugging a *lot* easier if you print out the completed
//                        // buffer for debugging.
//                        buffer.append(line + "\n");
//                    }

                    forecastJsonStr = buffer.toString();
                    Log.v(LOG_TAG, "Forecast JSON string: " + forecastJsonStr);
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error: ", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }
            return forecastJsonStr;
        }

        private Uri buildURL(String postcode) {

            String units = "metric";
            String responseFormat = "json";
            String APPID = BuildConfig.OPEN_WEATHER_MAP_API_KEY;
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
            final String PATH = "daily";
            final String QUERY_PARAM = "q";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String FORMAT_PARAM = "mode";
            final String APPID_PARAM = "APPID";

            return Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(QUERY_PARAM, postcode)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(FORMAT_PARAM, responseFormat)
                    .appendQueryParameter(APPID_PARAM, APPID)
                    .build();
        }


        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {
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
            
            return dailyForecastsInPeriod;


        }

        private String getReadableDateString(long dateTime) {
            Date date = new Date(dateTime * 1000);
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd");
            format.setTimeZone(TimeZone.getDefault());

            return format.format(date);
        }

    }

}
