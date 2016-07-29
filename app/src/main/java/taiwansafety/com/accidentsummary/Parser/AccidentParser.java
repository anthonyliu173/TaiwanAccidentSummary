package taiwansafety.com.accidentsummary.Parser;

import android.location.Location;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import taiwansafety.com.accidentsummary.Model.Accident;

/**
 * Created by anthonyliu on 16/7/28.
 * Copyright © 2016年 17Media. All rights reserved.
 */
public class AccidentParser {

    private static final String TAG = "AccidentParser";

    public static ArrayList<Accident> parseAccident(JSONArray json_array) {

        Log.i(TAG, "parseAccident: " + json_array);

        ArrayList<Accident> accidents = new ArrayList<>();


        for (int i = 0; i < json_array.length(); i++) {
            try {
                accidents.add(buildAccident(json_array.getJSONObject(i)));
            } catch (JSONException je) {
                Log.i(TAG, "JSONException: " + je.toString());
            }
        }

        return accidents;

    }

    private static Accident buildAccident(JSONObject jsonAccident) {

        Accident accident = new Accident();

        try {

            int death = Integer.parseInt(jsonAccident.getString("死亡").toString());
            int injury = Integer.parseInt(jsonAccident.getString("受傷").toString());
            String address = jsonAccident.getString("地點");
            Date date = parseAccidentDate(jsonAccident.getString("日期"), jsonAccident.getString("時間"));

            accident.setAddress(address);
            accident.setDeath(death);
            accident.setInjury(injury);
            accident.setDate(date);

            if (jsonAccident.has("lat") && !jsonAccident.isNull("lat") && jsonAccident.has("lng") && !jsonAccident.isNull("lng")) {
                double lat = jsonAccident.getDouble("lat");
                double lng = jsonAccident.getDouble("lng");
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lng);
                accident.setLocation(location);
            }

//            ArrayList<Accident.CAR_TYPE> carTypes = getCarTypes("車種");
//            accident.setCarTypes(carTypes);

        } catch (JSONException je) {

        }

        Log.i(TAG, "accident: " + accident.toString());

        return accident;

    }

    /**
     * data format: "日期":"20150101"(date),"時間":"0943"(time)
     */
    private static Date parseAccidentDate(String date, String time) {

        try {

            String dateString = date + time;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            Date date_ = sdf.parse(dateString);

            return date_;

        } catch (ParseException pe) {

            return new Date();

        }

    }

    private static ArrayList<Accident.CAR_TYPE> getCarTypes(String vehicles) {
        Log.i(TAG, "getCarTypes: " + vehicles);

        ArrayList<Accident.CAR_TYPE> carTypes = new ArrayList<>();

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.PEDESTRIAN.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.SCOOTER.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.CAR.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.BIKE.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.SMALL_TRUCK.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.BIG_TRUCK.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        for (int i = 0; i < StringUtils.countMatches(vehicles, Accident.CAR_TYPE.BUS.toString()); i++) {
            carTypes.add(Accident.CAR_TYPE.PEDESTRIAN);
        }

        return carTypes;

    }

}
