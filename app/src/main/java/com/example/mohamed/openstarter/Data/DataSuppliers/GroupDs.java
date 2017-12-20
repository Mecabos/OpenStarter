/*
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

*/
/**
 * Created by Mohamed on 12/9/2017.
 *//*


public class GroupDs {

    final String TAG = "grouppp";
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    //private final String URL_SERVER = "http://192.168.1.60/androidws/web/app_dev.php";
    private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.unaux.com/AndroidWS/web/app_dev.php";

    private final String URL_GET_BY_NAME = URL_SERVER + "/collaborationGroup/getByName";
    private final String URL_CREATE = URL_SERVER + "/collaborationGroup/new";
    private final String URL_UPDATE = URL_SERVER + "/collaborationGroup/updateName";

    private Gson mGson;

    public GroupDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    */
/*public void addGroup(final String name, final String creator, final CallbackAdd callback) {

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("birthdate", birthdate+" 00:00:00");
        params.put("bio", bio);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onError();
            }
        }) {


        };

// Adding request to request queue
        Log.d("TAG", Arrays.toString(jsonObjReq.getBody()));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }*//*


    public void updateGroup(final String oldName, final String newName, final CallbackUpdate callback) {

        Map<String, String> params = new HashMap<>();
        params.put("oldName", oldName);
        params.put("newName", newName);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_UPDATE, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onError();
            }
        }) {


        };

// Adding request to request queue
        Log.d("TAG", Arrays.toString(jsonObjReq.getBody()));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public interface CallbackAdd{
        void onSuccess();
        void onError();
    }

    public interface CallbackGet{
        void onSuccess(User result);
        void onError(VolleyError error);
    }

    public interface CallbackUpdate{
        void onSuccess();
        void onError();
    }

}
*/
