package com.rohan.user.myretrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.rohan.user.myretrofitexample.Model.Weather;
import com.rohan.user.myretrofitexample.remote.WeatherAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.city) TextView city;
    @BindView(R.id.lastUpdated) TextView lastUpdated;
    @BindView(R.id.temparature) TextView temperature;
    @BindView(R.id.conditions) TextView conditions;
    @BindView(R.id.refresh) Button refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.refresh) public void refreshbut(){
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                temperature.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp());
                city.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());
                lastUpdated.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
                conditions.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Failed", t.getMessage());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshbut();
    }
}
