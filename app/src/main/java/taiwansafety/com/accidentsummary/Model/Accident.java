package taiwansafety.com.accidentsummary.Model;

import android.location.Location;

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
    public enum CAR_TYPE{

        PEDESTRIAN("人"), SCOOTER("機車"),
        CAR("小客車"), BIKE("腳踏自行車"),
        SMALL_TRUCK("小貨車"), BIG_TRUCK("大貨車"),
        BUS("大客車");

        private final String name;

        private CAR_TYPE(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }

    private int id = -1;

    private String address;

    private Location location;

    private Date date; // including date and time

    private int death = 0; // number of death

    private int injury = 0; // number of injury

    private ArrayList<CAR_TYPE> carTypes;

    public Accident() {

    }

    public Accident(int id, String address, double lat, double lng, Date date, int death, int injury, ArrayList<CAR_TYPE> carTypes) {

        this.id = id;
        this.address = address;
        this.location = new Location("");
        this.location.setLatitude(lat);
        this.location.setLongitude(lng);
        this.date = date;
        this.death = death;
        this.injury = injury;
        this.carTypes = carTypes;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getInjury() {
        return injury;
    }

    public void setInjury(int injury) {
        this.injury = injury;
    }

    public ArrayList<CAR_TYPE> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(ArrayList<CAR_TYPE> carTypes) {
        this.carTypes = carTypes;
    }

    public String getAccident(){
        return "- 死亡: " + death + ", 受傷: " + injury + ", 地址: " + address;
    }

    @Override
    public String toString() {
        return "Accident{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", death=" + death +
                ", injury=" + injury +
                ", carTypes=" + carTypes +
                '}';
    }
}
