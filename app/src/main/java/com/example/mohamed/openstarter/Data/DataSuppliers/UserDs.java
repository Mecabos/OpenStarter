package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.app.AppController;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed on 11/27/2017.
 */

public class UserDs {

    static final String TAG = "adduserrr";
    // Tag used to cancel the request
    static String tag_json_obj = "json_obj_req";

    static String url = "http://172.19.17.193/AndroidWS/web/app_dev.php/user/new";

    /*ProgressDialog pDialog = new ProgressDialog(this);
pDialog.setMessage("Loading...");
pDialog.show();*/
    //StringRequest strreq = new StringRequest()
        public static void addUser (final String email, final String firstname, final String lastname){

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("firstname", firstname);
                params.put("lastname", lastname);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            //pDialog.hide();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {/*
                    String body ="";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
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
            }) {
            };
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
}
