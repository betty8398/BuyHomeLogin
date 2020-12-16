package com.example.buyhome_login.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.example.buyhome_login.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A product entry in the list of products.
 */
public class ProductEntry {
    private static final String TAG = ProductEntry.class.getSimpleName();


    public final long id;
    public final String sellerId;
    public final String title;
    public final Uri dynamicUrl;
    public final String url;
    public final String price;
    public final String description;
    public final String tag;
    public final float latitude;
    public final float longitude;
    public final Location location;
    public final BuyerRating buyerRating;

    public ProductEntry(
            long id, String sellerId, String title, String dynamicUrl, String url, String price, String description, String tag, float latitude, float longitude, Location location, BuyerRating buyerRating) {
        this.id = id;
        this.sellerId = sellerId;
        this.title = title;
        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.price = price;
        this.description = description;
        this.tag = tag;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.buyerRating = buyerRating;
    }

    public class Location{
        public final float lat;
        public final float lng;

        Location(float lat, float lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

    public class BuyerRating{
        public final String buyerId;
        public final String describe;
        public final int rate;

        BuyerRating(String buyerId, String describe, int rate) {
            this.buyerId = buyerId;
            this.describe = describe;
            this.rate = rate;
        }
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */
    public static List<ProductEntry> initProductEntryList(Resources resources) {
        JSONObject jsonObject = null;
        InputStream inputStream = resources.openRawResource(R.raw.test20201202);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        try{
            jsonObject=new JSONObject(jsonProductsString);
            System.out.println(jsonObject.get("product"));
            Gson gson = new Gson();
            Type productListType = new TypeToken<ArrayList<ProductEntry>>() {
            }.getType();
            return gson.fromJson(jsonObject.get("product").toString(), productListType);
        }catch (Exception e){

        }

        return null;
    }
}