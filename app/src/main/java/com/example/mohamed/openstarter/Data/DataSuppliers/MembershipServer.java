package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Helpers.Util.ServerConnection;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.app.AppController;
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
 * Created by Mohamed on 12/23/2017.
 */

public class MembershipServer extends ServerConnection {
    //**** URL STRINGS
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String URL_GET_BY_GROUP_NAME_MEMBERSHIP = URL_SERVER + "/membership/getByGroupName";
    private final String URL_CREATE = URL_SERVER + "/membership/addMember";
    private final String URL_DELETE = URL_SERVER + "/membership/deleteMember";

    //**** TAG STRINGS
    private final String TAG = "COLLABORATIONGROUP-WS";
    private final String REQUEST_TAG_GET_BY_USER = "collaboration_group_get_by_admmin_user_req";
    String tag_json_obj = "json_obj_req";

    private Gson mGson;

    public MembershipServer() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void userGetByGroupName(final String name, final MembershipServer.CallbackGetByName callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_GROUP_NAME_MEMBERSHIP,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> userList = new ArrayList<>();
                        userList.addAll(Arrays.asList(mGson.fromJson(response.toString(), User[].class)));
                        callback.onSuccessGet(userList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        }) {
            @Override
            public byte[] getBody() {
                String body = "{\"groupName\": \"" + name + "\"}";
                return body.getBytes();
            }
        };

        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_BY_USER);
    }


    public void addMembership(final String name, final String creatorEmail, int isAdmin, final MembershipServer.CallbackAdd callback) {

        Map<String, String> params = new HashMap<>();
        params.put("groupName", name);
        params.put("email", creatorEmail);
        params.put("isAdmin", Integer.toString(isAdmin));

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

        Log.d("TAG", Arrays.toString(jsonObjReq.getBody()));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    public void deleteMembership(final String groupName, final int user_id, final MembershipServer.CallbackDelete callback) {

        Map<String, String> params = new HashMap<>();
        params.put("groupName", groupName);
        params.put("id_user", Integer.toString(user_id));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_DELETE, new JSONObject(params),
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


    public interface CallbackAdd {
        void onSuccess();

        void onFail();
    }

    public interface CallbackGetByName {
        void onSuccessGet(List<User> result);

        void onFail();
    }

    public interface CallbackDelete {
        void onSuccess();

        void onFail();
    }


}
