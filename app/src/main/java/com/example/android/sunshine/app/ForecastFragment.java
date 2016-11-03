package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        return rootView;
    }

}