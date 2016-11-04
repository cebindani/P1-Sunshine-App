package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFragment extends Fragment {

    ArrayAdapter forecastArrayAdapter;

    public ForecastFragment() {
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

        forecastArrayAdapter = new ArrayAdapter(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecastArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastArrayAdapter);

        new FetchWeatherTask().execute();

        return rootView;

    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, String> {


        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {

            try {
                return OpenWeatherAPIConnection();
            } catch (IOException e) {
                return "Unable to retrieve data";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        private String OpenWeatherAPIConnection() throws IOException {
            String forecastJsonStr = null;

            String APPID = BuildConfig.OPEN_WEATHER_MAP_API_KEY;

            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043,US&units=metric&cnt=7&mode=json&APPID=" + APPID);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                Log.d(LOG_TAG, "Response code: " + responseCode);


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
                    Log.d(LOG_TAG, forecastJsonStr);
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error: ",e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }
            return forecastJsonStr;
        }
    }

}
