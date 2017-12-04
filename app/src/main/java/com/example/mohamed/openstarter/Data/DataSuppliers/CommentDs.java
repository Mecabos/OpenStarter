package com.example.mohamed.openstarter.Data.DataSuppliers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohamed.openstarter.Helpers.SubContentDeserializer;
import com.example.mohamed.openstarter.Models.Comment;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.app.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bacem on 12/3/2017.
 */

public class CommentDs {

    //**** URL STRINGS
    private final String URL_SERVER = "http://192.168.1.5/androidws/web/app_dev.php";
    //private final String URL_SERVER = "https://openstarter.000webhostapp.com/AndroidWS/web/app_dev.php";
    private final String  URL_GET_BY_PROJECT = URL_SERVER + "/comment/getByProject" ;

    //**** TAG STRINGS
    private final String  TAG = "COMMENT-WS" ;
    private final String  REQUEST_TAG_GET_BY_PROJECT = "Comment_get_by_project_req" ;

    private Gson mGson;

    public CommentDs(){
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .setDateFormat("yyyy/MM/dd HH:mm:ss");
        mGson = gsonBuilder.create();
    }

    public void commentGetByProject(final String projectId, final CommentDs.Callback callback){
        // Tag used to cancel the request


        

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                URL_GET_BY_PROJECT,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Comment> commentsList = Arrays.asList(mGson.fromJson(response.toString(), Comment[].class));
                        callback.onSuccess(commentsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

            }
        }){
            @Override
            public byte[] getBody() {
                String body="{\"project_id\":"+ projectId +"}";
                return body.getBytes();
            }
        } ;


        AppController.getInstance().addToRequestQueue(req, REQUEST_TAG_GET_BY_PROJECT);
    }


    public interface Callback{
        void onSuccess(List<Comment> result);
    }
}