package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Models.Project;
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

public class ProjectDs {


    //**** URL STRINGS
    //private final String URL_SERVER = "http://192.168.1.5/androidws/web/app_dev.php";
    private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.unaux.com/AndroidWS/web/app_dev.php";
    private final String  URL_GET_ALL_PROJECT = URL_SERVER + "/project/getAll" ;
    private final String  URL_CREATE_PROJECT = URL_SERVER + "/project/create" ;

    //**** TAG STRINGS
    private final String  TAG = "PROJECT-WS" ;
    private final String  REQUEST_TAG_GET_ALL = "project_getAll_req" ;
    private final String  REQUEST_TAG_CREATE_PROJECT = "project_create_req";

    private Gson mGson;

    public ProjectDs(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void projectGetAll(final Callback callback){
        // Tag used to cancel the request

        JsonArrayRequest req = new JsonArrayRequest(URL_GET_ALL_PROJECT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Project> projectList = Arrays.asList(mGson.fromJson(response.toString(), Project[].class));
                        callback.onSuccessGet(projectList);
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
//TODO: make name of category unique
    //TODO: group dynamic
    //TODO: add equipement list and services list
    public void projectCreate(final String name,
                              final String startDate,
                              final String finishDate,
                              final String description,
                              final String shortDescription,
                              final String budget,
                              final String userEmail,
                              final String category,
                              final ProjectDs.Callback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("startDate", startDate);
        params.put("finishDate", finishDate);
        params.put("description", description);
        params.put("shortDescription", shortDescription);
        params.put("budget", budget);
        params.put("equipementsList", "ss");
        params.put("servicesList", "ss");
        params.put("id_category", "2");
        params.put("id_group", "2");

        Log.d("zab", params.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE_PROJECT,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Project createdProject = mGson.fromJson(response.toString(), Project.class);
                        callback.onSuccessCreate(createdProject);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_CREATE_PROJECT);
    }


    public interface Callback{
        void onSuccessGet(List<Project> result);
        void onSuccessCreate(Project createdProject);
        void onFail();
    }
}
