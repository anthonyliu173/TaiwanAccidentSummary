package taiwansafety.com.accidentsummary.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anthonyliu on 16/7/28.
 * Copyright © 2016年 17Media. All rights reserved.
 */
public class Accident {

    /**
     * PEDESTRIAN(人), SCOOTER(機車), CAR(小客車), BIKE(腳踏自行車), SMALL_TRUCK(小貨車)
     * BIG_TRUCK(大貨車), BUS(大客車)
     * */
    enum CAR_TYPE{
        PEDESTRIAN, SCOOTER, CAR, BIKE, SMALL_TRUCK, BIG_TRUCK, BUS
    }

    private int id;

    private String address;

    private LatLng latLng;

    private Date date; // including date and time

    private int death; // number of death

    private int injury; // number of injury

    private ArrayList<CAR_TYPE> carTypes;

    public Accident(int id, String address, LatLng latLng, Date date, int death, int injury, ArrayList<CAR_TYPE> carTypes) {

        this.id = id;
        this.address = address;
        this.latLng = latLng;
        this.date = date;
        this.death = death;
        this.injury = injury;
        this.carTypes = carTypes;

    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Date getDate() {
        return date;
    }

    public int getDeath() {
        return death;
    }

    public int getInjury() {
        return injury;
    }

    public ArrayList<CAR_TYPE> getCarTypes() {
        return carTypes;
    }

}
