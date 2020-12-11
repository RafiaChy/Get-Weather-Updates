package com.example.getweatherupdates;

import com.google.gson.annotations.SerializedName;

public class FetchMainUpdates {
    @SerializedName("main")
    Main main;
    @SerializedName("clouds")
    Clouds cloud;
    @SerializedName("wind")
    Wind wind;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getCloud() {
        return cloud;
    }

    public void setCloud(Clouds cloud) {
        this.cloud = cloud;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
