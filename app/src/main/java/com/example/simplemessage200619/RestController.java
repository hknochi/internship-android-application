package com.example.simplemessage200619;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RestController {
    private static final String TAG = "RestController";


    public static void getBooks(Context context, final Consumer<String> f) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        String url = "https://floating-garden-54811.herokuapp.com/api/v1/book";

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("_embedded");
                            JSONArray jsonArray = jsonObject.getJSONArray("book");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject book = jsonArray.getJSONObject(i);

                                System.out.println(book.toString());



                                /*String firstName = employee.getString("firstname");
                                int age = employee.getInt("age");
                                String mail = employee.getString("mail");*/

                                //mTextViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");

                                //f.accept(book.getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        RestRequest.getInstance(context).addToRequestQueue(request);
    }

    public static void registerUser (Context context, UserProfile user, final Consumer<ResponseStatus> f) {

        String url = "https://floating-garden-54811.herokuapp.com/user/register";
        //String url = "http://dummy.restapiexample.com/api/v1/employees";

        Gson gson = new Gson();
        String json = gson.toJson(user);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject=new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        Log.d(TAG, json );
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());
                        ResponseStatus responseStatus = generateResponseStatus(response.toString());
                        f.accept(responseStatus);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        RestRequest.getInstance(context).addToRequestQueue(request);
    }

    public static void loginUser (Context context, UserLogin user, final Consumer<ResponseStatus> f) {

        String url = "https://floating-garden-54811.herokuapp.com/user/login";
        //String url = "http://dummy.restapiexample.com/api/v1/employees";

        Gson gson = new Gson();
        String json2 = gson.toJson(user);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject=new JSONObject(json2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
      //  Log.d(TAG, json2 );
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());

                        ResponseStatus responseStatus = generateResponseStatus(response.toString());

                        f.accept(responseStatus);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }); // The request queue will automatically handle the request as soon as it can.
        RestRequest.getInstance(context).addToRequestQueue(request);
    }

    private static ResponseStatus generateResponseStatus(String response) {
        Gson gson = new Gson();

        ResponseStatus responseStatus = gson.fromJson(response.toString(), ResponseStatus.class);
       // Log.d("Status", Integer.toString(responseStatus.getStatus()));
      //  Log.d("Message", responseStatus.getMessage());

        return responseStatus;
    }


    public static void message (Context context, Message message, final Consumer<ResponseStatus> f) {

        String url = "https://floating-garden-54811.herokuapp.com/message/registerMessage";
        Gson gson = new Gson();
        String json3 = gson.toJson(message);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject=new JSONObject(json3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        ResponseStatus responseStatus = generateResponseStatus(response.toString());
                        f.accept(responseStatus);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        RestRequest.getInstance(context).addToRequestQueue(request);
    }

    public static void messages (Context context, final Consumer<List<Message>> f) {

        String url = "https://floating-garden-54811.herokuapp.com/message";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        List<Message> messagesList = Arrays.asList(new Gson().fromJson(response.toString(), Message[].class));
                        f.accept(messagesList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        RestRequest.getInstance(context).addToRequestQueue(request);
    }
}


