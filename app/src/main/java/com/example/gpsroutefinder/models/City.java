package com.example.gpsroutefinder.models;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.TimeZone;
import java.util.UUID;

public class City implements Serializable {

//    private String id;
    private String cityName;
    private boolean important;
    private TimeZone time;
    String timeValue;
    String zoneName ;

    public City(String cityName,String zoneName){
//        init();
        this.cityName = cityName;
        this.important = false;
        this.zoneName = zoneName;
        this.time = TimeZone.getTimeZone(zoneName);

        Date date =  new Date();
        SimpleDateFormat df  = new SimpleDateFormat("hh:mm:ss");
        df.setTimeZone(this.time);
        timeValue = df.format(date);

    }

    public void upDateTime() {

        this.time = TimeZone.getTimeZone(this.zoneName);
        Date date =  new Date();
        SimpleDateFormat df  = new SimpleDateFormat("hh:mm:ss");
        df.setTimeZone(this.time);
        this.timeValue = df.format(date);
    }

//    private void init() {
//        this.id = UUID.randomUUID().toString();
//    }

    public String getName() {
        return cityName;
    }

    public void setImportant(boolean val) {
        this.important = val;
    }

    public boolean isCity(City city) {
        return this.cityName.equals(city.cityName);
    }

    public boolean isImportant() {
        return this.important;
    }

    public String getTime()
    {
        return timeValue;
    }

//    public void delete(String city_name )
//    {
//        delete(city_name);
//    }

}
