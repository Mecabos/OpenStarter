package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Models.Category;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bacem on 11/20/2017.
 */

public class CategoryDs {


    //**** URL STRINGS
    private final String URL_SERVER = "http://192.168.1.3/androidws/web/app_dev.php";
    //private final String URL_SERVER = "https://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String  URL_GET_ALL_CATEGORY = URL_SERVER + "/category/getAll" ;

    //**** TAG STRINGS
    private final String  TAG = "CATEGORY-WS" ;
    private final String  REQUEST_TAG_GET_ALL = "category_getAll_req" ;

    private Gson mGson;

    public CategoryDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void getAll(final Callback callback){
        // Tag used to cancel the request

        JsonArrayRequest req = new JsonArrayRequest(URL_GET_ALL_CATEGORY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Category> categoriesList = Arrays.asList(mGson.fromJson(response.toString(), Category[].class));
                        callback.onSuccessGet(categoriesList);
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
                }
        });
        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_ALL);
    }

    public interface Callback{
        void onSuccessGet(List<Category> categoriesList);
        void onFail();
    }
}