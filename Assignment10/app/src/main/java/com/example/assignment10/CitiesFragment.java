// Assignment 10
// CitiesFragment.java
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

import com.example.assignment10.databinding.FragmentCitiesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitiesFragment extends Fragment {

    public CitiesFragment() {
    }

    FragmentCitiesBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<City> cities = new ArrayList<>();
    ArrayAdapter<City> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cities);
        binding.listView.setAdapter(adapter);
        getCities();

        binding.listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            City city = cities.get(position);
            mListener.onCitySelected(city);
        });
    }

    void getCities(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject root = new JSONObject(body);
                        JSONArray citiesJsonArray = root.getJSONArray("cities");
                        cities.clear();

                        for (int i = 0; i < citiesJsonArray.length(); i++) {
                            JSONObject cityJsonObject = citiesJsonArray.getJSONObject(i);
                            City city = new City(cityJsonObject);
                            cities.add(city);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.e("CitiesFragment", "Failed to fetch cities: " + response.code());
                }
            }
        });
    }

    CitiesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CitiesListener) context;
    }

    interface CitiesListener {
        void onCitySelected(City city);
    }


}