package taiwansafety.com.accidentsummary.Util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anthonyliu on 16/7/28.
 * Copyright © 2016年 17Media. All rights reserved.
 */
public class JSONAssetHelper {

    public static JSONArray loadJSON(Context context, String filename) {

        String jsonString = null;

        try {

            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            jsonString = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonString);

            return jsonArray;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException je){
            je.printStackTrace();
            return null;
        }

    }

}
