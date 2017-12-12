package com.example.mohamed.openstarter.Data.DataSuppliers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bacem on 12/9/2017.
 */

public class CollaborationGroupDs {

    //**** URL STRINGS
    private final String URL_SERVER = "http://192.168.1.3/androidws/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String URL_GET_BY_USER_COLLABORATION_GROUP = URL_SERVER + "/collaborationGroup/getByAdminUser";

    //**** TAG STRINGS
    private final String TAG = "COLLABORATIONGROUP-WS";
    private final String REQUEST_TAG_GET_BY_USER = "collaboration_group_get_by_admmin_user_req";

    private Gson mGson;

    public CollaborationGroupDs() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void collaborationGroupGetByAdminUser(final String email, final CollaborationGroupDs.Callback callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_USER_COLLABORATION_GROUP,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<CollaborationGroup> groupsList = new ArrayList<>() ;
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

    public interface Callback{
        void onSuccessGet(List<CollaborationGroup> result);
        void onSuccessCreate(CollaborationGroup createdGroup);
        void onFail();
    }
}
