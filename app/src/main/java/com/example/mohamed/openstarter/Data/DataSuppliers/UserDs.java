package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed on 11/27/2017.
 */

public class UserDs {

    static final String TAG = "adduserrr";
    // Tag used to cancel the request
    static String tag_json_obj = "json_obj_req";

    static String url = "http://192.168.1.60/AndroidWS/web/app_dev.php/user/new";
    static String urlGetUser = "http://192.168.1.60/AndroidWS/web/app_dev.php/user/getByEmail";


    private static Gson mGson;

    public UserDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public static void getUserByEmail(String email,final Callback callback){
        // Tag used to cancel the request

        Map<String, String> params = new HashMap<>();
        params.put("email", email);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlGetUser, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<User> userList = Arrays.asList(mGson.fromJson(response.toString(), User[].class));
                        callback.onSuccess(userList);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public interface Callback{
        void onSuccess(List<User> result);
    }



    public static void addUser(final String email, final String firstname, final String lastname, String birthdate, String bio) {

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("birthdate", "2017/11/29 23:51:54");
        params.put("bio", bio);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


        };

// Adding request to request queue
        Log.d("TAG", Arrays.toString(jsonObjReq.getBody()));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}
