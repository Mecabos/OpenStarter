package com.esprit.pixelCells.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.pixelCells.openstarter.Helpers.Util.ServerConnection;
import com.esprit.pixelCells.openstarter.Models.CollaborationGroup;
import com.esprit.pixelCells.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bacem on 12/9/2017.
 */

public class CollaborationGroupServer extends ServerConnection {

    //**** URL STRINGS
    //private final String URL_SERVER = "http://172.16.247.198/androidws/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String URL_GET_BY_ADMIN_USER_COLLABORATION_GROUP = URL_SERVER + "/collaborationGroup/getByAdminUser";
    private final String URL_GET_BY_USER_COLLABORATION_GROUP = URL_SERVER + "/collaborationGroup/getByUser";
    private final String URL_UPDATE = URL_SERVER + "/collaborationGroup/updateName";
    private final String URL_CREATE = URL_SERVER + "/collaborationGroup/new";

    //**** TAG STRINGS
    private final String TAG = "COLLABORATIONGROUP-WS";
    private final String REQUEST_TAG_GET_BY_USER = "collaboration_group_get_by_admmin_user_req";
    String tag_json_obj = "json_obj_req";

    private Gson mGson;

    public CollaborationGroupServer() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void collaborationGroupGetByAdminUser(final String email, final CollaborationGroupServer.Callback callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_ADMIN_USER_COLLABORATION_GROUP,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<CollaborationGroup> groupsList = new ArrayList<>();
                        groupsList.addAll(Arrays.asList(mGson.fromJson(response.toString(), CollaborationGroup[].class)));
                        callback.onSuccessGet(groupsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        }) {
            @Override
            public byte[] getBody() {
                String body = "{\"email\": \"" + email + "\"}";
                return body.getBytes();
            }
        };

        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_BY_USER);
    }

    public void collaborationGroupGetByUser(final String email, final CollaborationGroupServer.CallbackGetByUser callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_USER_COLLABORATION_GROUP,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<CollaborationGroup> groupsList = new ArrayList<>();
                        groupsList.addAll(Arrays.asList(mGson.fromJson(response.toString(), CollaborationGroup[].class)));
                        callback.onSuccessGet(groupsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        }) {
            @Override
            public byte[] getBody() {
                String body = "{\"email\": \"" + email + "\"}";
                return body.getBytes();
            }
        };

        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_BY_USER);
    }


    public void updateGroup(final String oldName, final String newName, final CollaborationGroupServer.CallbackUpdate callback) {

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



    public void addGroup(final String name, final String creatorEmail, final CallbackAdd callback) {

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("creatorEmail", creatorEmail);

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
                callback.onFail();
            }
        }) {


        };

// Adding request to request queue
        Log.d("TAG", Arrays.toString(jsonObjReq.getBody()));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    public interface Callback {
        void onSuccessGet(List<CollaborationGroup> result);

        void onSuccessCreate(CollaborationGroup createdGroup);

        void onFail();
    }

    public interface CallbackAdd {
        void onSuccess();

        void onFail();
    }

    public interface CallbackGetByUser {
        void onSuccessGet(List<CollaborationGroup> result);

        void onFail();
    }

    public interface CallbackUpdate {
        void onSuccess();

        void onError();
    }
}
