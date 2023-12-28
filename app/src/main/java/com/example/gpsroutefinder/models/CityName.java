package com.example.gpsroutefinder.models;

public class CityName {

    String cityName;
    String zoneName;

    public CityName(String cityName, String zoneName) {
        this.cityName = cityName;
        this.zoneName = zoneName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getZoneName() {
        return zoneName;
    }
}
