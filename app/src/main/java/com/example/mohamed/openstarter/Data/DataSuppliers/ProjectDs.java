package com.example.mohamed.openstarter.Data.DataSuppliers;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Bacem on 11/20/2017.
 */

public class ProjectDs {


    //**** URL STRINGS
    //private final String URL_SERVER = "http://192.168.1.60/androidws/web/app_dev.php";
    private final String URL_SERVER = "https://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String  URL_GET_ALL = URL_SERVER + "/project/getAll" ;

    //**** TAG STRINGS
    private final String  TAG = "PROJECT-WS" ;
    private final String  REQUEST_TAG_GET_ALL = "project_getAll_req" ;

    private Gson mGson;

    public ProjectDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void projectGetAll(final Callback callback){
        // Tag used to cancel the request

        JsonArrayRequest req = new JsonArrayRequest(URL_GET_ALL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Project> projectList = Arrays.asList(mGson.fromJson(response.toString(), Project[].class));
                        callback.onSuccess(projectList);
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
                //TODO: FIX IF NO CONNECTION
                /*
                String body ="";
                //get status code here
                //String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if(error.networkResponse.data!=null) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG,body);

            */}
        });


        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_ALL);
    }


    public interface Callback{
        void onSuccess(List<Project> result);
        void onFail();
    }
}
