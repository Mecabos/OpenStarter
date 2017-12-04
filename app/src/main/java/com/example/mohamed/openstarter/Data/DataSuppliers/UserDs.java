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
import java.util.Map;

/**
 * Created by Mohamed on 11/27/2017.
 */

public class UserDs {

    final String TAG = "adduserrr";
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    private final String URL_SERVER = "http://192.168.1.5/androidws/web/app_dev.php";
    //private final String URL_SERVER = "https://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";

    private final String URL_GET_BY_EMAIL = URL_SERVER + "/user/getByEmail";
    private final String URL_CREATE = URL_SERVER + "/user/new";

    private Gson mGson;

    public UserDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void getUserByEmail(String email,final Callback callback){
        // Tag used to cancel the request

        Map<String, String> params = new HashMap<>();
        params.put("email", email);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_GET_BY_EMAIL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User createdUser = mGson.fromJson(response.toString(), User.class);
                        callback.onSuccess(createdUser);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }





    public void addUser(final String email, final String firstname, final String lastname, String birthdate, String bio) {

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("birthdate", "2017/11/29 23:51:54");
        params.put("bio", bio);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
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

    public interface Callback{
        void onSuccess(User result);
    }

}
