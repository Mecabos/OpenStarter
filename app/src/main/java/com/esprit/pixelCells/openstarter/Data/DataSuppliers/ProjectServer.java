package com.esprit.pixelCells.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.pixelCells.openstarter.Data.CustomClasses.ContributionsWithUsers;
import com.esprit.pixelCells.openstarter.Data.CustomClasses.ProjectWithFollowCount;
import com.esprit.pixelCells.openstarter.Helpers.Util.ServerConnection;
import com.esprit.pixelCells.openstarter.Models.Project;
import com.esprit.pixelCells.openstarter.Models.User;
import com.esprit.pixelCells.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bacem on 11/20/2017.
 */

public class ProjectServer extends ServerConnection {


    //**** URL STRINGS

    private final String URL_GET_ALL_PROJECT = URL_SERVER + "/project/getAll";
    private final String URL_CREATE_PROJECT = URL_SERVER + "/project/create";
    private final String URL_GET_MEMBERS = URL_SERVER + "/project/getMembers";
    private final String URL_GET_CONTRIBUTIONS = URL_SERVER + "/project/getContributionsByProject";

    //**** TAG STRINGS
    private final String TAG = "PROJECT-WS";
    private final String REQUEST_TAG_GET_ALL = "project_getAll_req";
    private final String REQUEST_TAG_GET_MEMBERS = "project_getMembers_req";
    private final String REQUEST_TAG_GET_CONTRIBUTIONS = "project_getContributions_req";
    private final String REQUEST_TAG_CREATE_PROJECT = "project_create_req";

    private Gson mGson;

    public ProjectServer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void projectGetAll(final Callback callback) {
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

            */
            }
        });
        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_ALL);
    }

    public void projectWithCountGetAll(final Callback callback) {
        // Tag used to cancel the request

        JsonArrayRequest req = new JsonArrayRequest(URL_GET_ALL_PROJECT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<ProjectWithFollowCount> projectList = Arrays.asList(mGson.fromJson(response.toString(), ProjectWithFollowCount[].class));
                        callback.onSuccessGetWithFollowCount(projectList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        });
        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_ALL);
    }

    //TODO: make name of category unique
    //TODO: add equipement list and services list
    public void projectCreate(final String name,
                              final String startDate,
                              final String finishDate,
                              final String description,
                              final String shortDescription,
                              final String budget,
                              final String group,
                              final String category,
                              final ProjectServer.Callback callback) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("startDate", startDate);
        params.put("finishDate", finishDate);
        params.put("description", description);
        params.put("shortDescription", shortDescription);
        params.put("budget", budget);
        params.put("equipementsList", "");
        params.put("servicesList", "");
        params.put("id_category", category);
        params.put("id_group", group);
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
            public void onErrorResponse(VolleyError error) {
            }
        }) {
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_CREATE_PROJECT);
    }

    public void projectGetGroupMembersAll(final String projectId, final ProjectServer.CallbackGet callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_MEMBERS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> userList = new ArrayList<>() ;
                        userList.addAll(Arrays.asList(mGson.fromJson(response.toString(), User[].class)));
                        callback.onSuccessGet(userList);
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
                String body = "{\"projectId\":" + projectId + "}";
                return body.getBytes();
            }
        };


        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_MEMBERS);
    }




    public void projectGetContributionsWithUsers(final String projectId, final ProjectServer.CallbackGetContributions callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_CONTRIBUTIONS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<ContributionsWithUsers> contributionsList = new ArrayList<>() ;
                        contributionsList.addAll(Arrays.asList(mGson.fromJson(response.toString(), ContributionsWithUsers[].class)));
                        callback.onSuccessGet(contributionsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";

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
                String body = "{\"projectId\":" + projectId + "}";
                return body.getBytes();
            }
        };


        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_CONTRIBUTIONS);
    }



    public interface Callback {
        void onSuccessGet(List<Project> result);
        void onSuccessGetWithFollowCount(List<ProjectWithFollowCount> result);
        void onSuccessCreate(Project createdProject);
        void onFail();
    }

    public interface CallbackGet {
        void onSuccessGet(List<User> result);
        void onFail();
    }

    public interface CallbackGetContributions {
        void onSuccessGet(List<ContributionsWithUsers> result);
        void onFail();
    }
}
