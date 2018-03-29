package com.esprit.pixelCells.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.pixelCells.openstarter.Helpers.Util.ServerConnection;
import com.esprit.pixelCells.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed on 12/13/2017.
 */

public class NotificationServer extends ServerConnection {

    final String TAG = "User";
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    //private final String URL_SERVER = "http://172.16.247.198/androidws/web/app_dev.php";
    //private final String URL_SERVER = "http://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";

    //private final String URL_GET_BY_EMAIL = URL_SERVER + "/user/getByEmail";
    private final String URL_CREATE = URL_SERVER + "/notification/new";
    //private final String URL_UPDATE = URL_SERVER + "/user/update";

    private Gson mGson;

    public NotificationServer(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }


    public void addNotification(final JSONArray tokens, final String title, final String body, final CallbackSend callback) {

        Map<String, Object> params = new HashMap<>();
        //Map<String, String> tokenParams = new HashMap<>();
        //JSONObject tokenJson = new JSONObject();

        //String jsonToken = mGson.toJ(tokens);
        //tokens.toString();

        /*tokenParams.put("tokens",jsonToken);
        tokenJson.put(jsonToken);*/
        //JSONArray jsonArray = new JSONArray();


        Log.d("notiff","tokens : " + tokens.toString());
        Log.d("notiff","value of tokens : " + String.valueOf(tokens));

        params.put("token", tokens);
        params.put("title", title);
        params.put("body", body);

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
    }

    public interface CallbackSend{
        void onSuccess();
        void onError();
    }
}
