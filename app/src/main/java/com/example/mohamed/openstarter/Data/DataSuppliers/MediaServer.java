package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Helpers.Util.ServerConnection;
import com.example.mohamed.openstarter.Models.ProjectMedia;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bacem on 12/3/2017.
 */

public class MediaServer extends ServerConnection {

    //**** URL STRINGS
    //private final String URL_SERVER = "http://172.16.247.198/androidws/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String URL_GET_BY_PROJECT_PROJECT_MEDIA = URL_SERVER + "/media/project/get";
    private final String URL_CREATE_PROJECT_MEDIA = URL_SERVER + "/media/project/create";

    //**** TAG STRINGS
    private final String TAG = "MEDIA-WS";
    private final String REQUEST_TAG_GET_BY_PROJECT = "project_media_get_by_project_req";
    private final String REQUEST_TAG_CREATE_PROJECT_MEDIA = "project_media_create_req";

    private Gson mGson;

    public MediaServer() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }
    //TODO:FIX
    public void projectMediaGet(final String projectId, final MediaServer.Callback callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_PROJECT_PROJECT_MEDIA,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<ProjectMedia> mediasList = new ArrayList<>() ;
                        mediasList.addAll(Arrays.asList(mGson.fromJson(response.toString(), ProjectMedia[].class)));
                        callback.onSuccessProjectMediaGet(mediasList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                //get status code here
                //String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, body);

            }
        }) {
            @Override
            public byte[] getBody() {
                String body = "{\"project_id\":" + projectId + "}";
                return body.getBytes();
            }
        };


        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_BY_PROJECT);
    }

    public void projectMediaCreate(final String mediaName, final String isPrimary, final String mediaType, final String media, final String projectId, final MediaServer.Callback callback) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("media_name", mediaName);
        params.put("is_primary", isPrimary);
        params.put("media_type", mediaType);
        params.put("media", media);
        params.put("id_project", projectId);

        Log.e("eeeee",params.toString() );

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE_PROJECT_MEDIA,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProjectMedia createdProjectMedia = mGson.fromJson(response.toString(), ProjectMedia.class);
                        callback.onSuccessProjectMediaCreate(createdProjectMedia);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("eeeee","ezerer" );
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_CREATE_PROJECT_MEDIA);
    }


    public interface Callback {
        void onSuccessProjectMediaGet(ArrayList<ProjectMedia> result);

        void onSuccessProjectMediaCreate(ProjectMedia createdProjectMedia);
    }
}
