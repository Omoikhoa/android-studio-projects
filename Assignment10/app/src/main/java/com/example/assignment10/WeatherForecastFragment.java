// Assignment 10
// WeatherForecastFragment.java
// Alex Ilevbare

package com.example.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment10.databinding.ForecastListViewBinding;
import com.example.assignment10.databinding.FragmentWeatherForecastBinding;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {

    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private City mCity;
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<Forecast> forecasts = new ArrayList<>();
    private WeatherForecastAdapter adapter;

    public WeatherForecastFragment() {

    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewCity.setText(mCity.toString());
        adapter = new WeatherForecastAdapter(getContext(), R.layout.forecast_list_view, forecasts);
        binding.listView.setAdapter(adapter);
        getForecast();

    }
    private void getForecast() {
        String url = "https://api.weather.gov/points/" + mCity.getLat() + "," + mCity.getLng();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("WeatherForecastFragment", "Failed to fetch forecast URL", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String forecastUrl = jsonObject.getJSONObject("properties").getString("forecast");
                        getForecast(forecastUrl);
                    } catch (JSONException e) {
                        Log.e("WeatherForecastFragment", "Failed to parse JSON", e);
                    }
                }
            }
        });
    }

    private void getForecast(String forecastUrl) {

        Request request = new Request.Builder().url(forecastUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("WeatherForecastFragment", "Failed to fetch forecast", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray periods = jsonObject.getJSONObject("properties").getJSONArray("periods");

                        forecasts.clear();
                        for (int i = 0; i < periods.length(); i++) {
                            JSONObject period = periods.getJSONObject(i);
                            Forecast forecast = new Forecast(period);
                            forecasts.add(forecast);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("WeatherForecastFragment", "Failed to parse JSON", e);
                    }
                }
            }
        });
    }

    class WeatherForecastAdapter extends ArrayAdapter<Forecast> {
        public WeatherForecastAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Forecast> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ForecastListViewBinding mBinding;
            if (convertView == null) {
                mBinding = ForecastListViewBinding.inflate(getLayoutInflater(), parent, false);
                convertView = mBinding.getRoot();
                convertView.setTag(mBinding);
            } else {
                mBinding = (ForecastListViewBinding) convertView.getTag();
            }

            Forecast forecast = getItem(position);

            mBinding.textViewTime.setText(forecast.getStartTime());
            mBinding.textViewTemperature.setText(forecast.getTemperature() + "°F");
            mBinding.textViewPrecipitation.setText("Precipitation: " + forecast.getProbabilityOfPrecipitation() + "%");
            mBinding.textViewShortForecast.setText(forecast.getShortForecast());
            mBinding.textViewWindSpeed.setText("Wind Speed: " + forecast.getWindSpeed());
            Picasso.get().load(forecast.getIcon()).into(mBinding.imageView);

            return convertView;
        }
    }
}