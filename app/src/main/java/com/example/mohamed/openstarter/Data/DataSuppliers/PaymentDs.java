package com.example.mohamed.openstarter.Data.DataSuppliers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamed.openstarter.Models.Payment;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bacem on 12/10/2017.
 */

public class PaymentDs extends ConnectionDs {


    private final String URL_CREATE_PAYMENT = URL_SERVER + "/payment/create";
    private final String URL_COUNT_BY_USER = URL_SERVER + "/follow/countByProject";

    //**** TAG STRINGS
    private final String TAG = "FOLLOW-WS";
    //private final String REQUEST_TAG_GET_BY_PROJECT = "Follow_get_by_project_req";
    private final String REQUEST_TAG_CREATE_PAYMENT = "Payment_create_req";
    //private final String REQUEST_TAG_DELETE_FOLLOW = "Follow_delete_req";
    private final String REQUEST_TAG_COUNT_BY_PROJECT_FOLLOW = "Follow_count_by_project_req";


    private Gson mGson;

    public PaymentDs() {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void paymentCreate(final String userEmail, final String projectId, float amount, final PaymentDs.CallbackAdd callback) {

        Map<String, String> params = new HashMap<>();
        params.put("email_user", userEmail);
        params.put("id_project", projectId);
        params.put("amount", String.valueOf(amount));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_CREATE_PAYMENT,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Payment createdPayment = mGson.fromJson(response.toString(), Payment.class);
                        callback.onSuccessCreate(createdPayment);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, REQUEST_TAG_CREATE_PAYMENT);
    }




    /*public void followCount(final String userEmail, final String projectId, final PaymentDs.Callback callback) {

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
    }*/


    /*public interface Callback {
        void onSuccessGet(ArrayList<Payment> result);

        void onSuccessCreate(Payment createdPayment);

        void onSuccessCount(FollowCount followCount);

        void onSuccess();
    }*/

    public interface CallbackAdd {

        void onSuccessCreate(Payment createdPayment);

        void onFail();
    }

}
