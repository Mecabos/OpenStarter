package com.example.mohamed.openstarter.Data.DataSuppliers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Helpers.Util.FollowCount;
import com.example.mohamed.openstarter.Models.Follow;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bacem on 12/10/2017.
 */

public class FollowDs {

    //**** URL STRINGS
    private final String URL_SERVER = "http://172.16.247.198/androidws/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String URL_GET_BY_PROJECT_FOLLOW = URL_SERVER + "/follow/getByProject";
    private final String URL_CREATE_FOLLOW = URL_SERVER + "/follow/create";
    private final String URL_DELETE_FOLLOW = URL_SERVER + "/follow/delete";
    private final String URL_COUNT_BY_PROJECT_FOLLOW = URL_SERVER + "/follow/countByProject";

    //**** TAG STRINGS
    private final String TAG = "FOLLOW-WS";
    private final String REQUEST_TAG_GET_BY_PROJECT = "Follow_get_by_project_req";
    private final String REQUEST_TAG_CREATE_FOLLOW = "Follow_create_req";
    private final String REQUEST_TAG_DELETE_FOLLOW = "Follow_delete_req";
    private final String REQUEST_TAG_COUNT_BY_PROJECT_FOLLOW = "Follow_count_by_project_req";


    private Gson mGson;

    public FollowDs() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void followCreate(final String userEmail, final String projectId, final FollowDs.Callback callback) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email_user", userEmail);
        params.put("id_project", projectId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE_FOLLOW,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Follow createdFollow = mGson.fromJson(response.toString(), Follow.class);
                        callback.onSuccessCreate(createdFollow);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_CREATE_FOLLOW);
    }

    public void followDelete(final String userEmail, final String projectId, final FollowDs.Callback callback) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email_user", userEmail);
        params.put("id_project", projectId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_DELETE_FOLLOW,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_DELETE_FOLLOW);
    }


    public void followCount(final String userEmail, final String projectId, final FollowDs.Callback callback) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email_user", userEmail);
        params.put("id_project", projectId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_COUNT_BY_PROJECT_FOLLOW,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        FollowCount followCount = mGson.fromJson(response.toString(), FollowCount.class);
                        callback.onSuccessCount(followCount);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_COUNT_BY_PROJECT_FOLLOW);
    }


    public interface Callback {
        void onSuccessGet(ArrayList<Follow> result);

        void onSuccessCreate(Follow createdFollow);

        void onSuccessCount(FollowCount followCount);

        void onSuccess();
    }

}
