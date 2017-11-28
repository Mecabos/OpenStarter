package com.example.mohamed.openstarter.Data.DataSuppliers;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
    //private final String URL_SERVER = "http://172.16.132.98/androidws/web/app_dev.php";
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
        String req_tag = REQUEST_TAG_GET_ALL;

        String url = URL_GET_ALL ;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Project> projectList = Arrays.asList(mGson.fromJson(response.toString(), Project[].class));
                        callback.onSuccess(projectList);
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });


        AppController.getInstance().addToRequestQueue(req, req_tag);
    }


    public interface Callback{
        void onSuccess(List<Project> result);
    }
}
