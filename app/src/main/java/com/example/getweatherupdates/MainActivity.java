package com.example.getweatherupdates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
        EditText city;
        TextView myResult;
        Button button;

      //  String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
        String apikey = "e0aa76877b32d5f8bd121b6fb169c942";

       //String url= "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=e0aa76877b32d5f8bd121b6fb169c942";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myResult = findViewById(R.id.result);
        button = findViewById(R.id.button);
        city = findViewById(R.id.getCity);


    }

    public void getWeather(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherapi myApi = retrofit.create(weatherapi.class);
        Call<FetchMainUpdates> fetchMainUpdatesCall = myApi.getWeather(city.getText().toString().trim(),apikey);

        fetchMainUpdatesCall.enqueue(new Callback<FetchMainUpdates>() {
            @Override
            public void onResponse(Call<FetchMainUpdates> call, Response<FetchMainUpdates> response) {
                if(response.code()==404){
                    Toast.makeText(MainActivity.this, "Please, enter a valid city", Toast.LENGTH_LONG).show();
                }

                else if (!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                }

                FetchMainUpdates myData = response.body();

                Main main = myData.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int)(temp-273.15);
                Double feelsLike = main.getFeelsLike();
                Integer feelings = (int) (feelsLike-273.15);
                Integer humidity = main.getHumidity();

                Clouds cloud = myData.getCloud();
                Integer all = cloud.getAll();

                Wind wind = myData.getWind();
                Double speed = wind.getSpeed();

                myResult.setText("Temperature: "+String.valueOf(temperature)+" °C");
                myResult.append("\nFeels Like: "+String.valueOf(feelings)+" °C");
                myResult.append("\nHumidity: "+String.valueOf(humidity)+" gkg-1");
                myResult.append("\nClouds: "+ String.valueOf(all));
               myResult.append("\nWind speed: "+String.valueOf(speed));
            }

            @Override
            public void onFailure(Call<FetchMainUpdates> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}