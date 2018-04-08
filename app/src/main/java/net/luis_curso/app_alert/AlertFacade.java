package net.luis_curso.app_alert;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiocuenca on 3/4/18.
 */

interface ModelListener{
    public void onResponse(ArrayList<Alert> modelList);
    public void onResponsePut();

}

public class AlertFacade {

    public static ArrayList<Alert> list;

    public static void getAlerts(final Context context, final ModelListener model){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.1.113:8080/Bocazas/webresources/entities.alerts";
        list  = new ArrayList<>();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("json",response);
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                long alertTime = jsonobject.getLong("alertTime");
                                int idAlert = jsonobject.getInt("idAlert");
                                String message = jsonobject.getString("message");
                                int verifications = jsonobject.getInt("verifications");
                                JSONObject icons = jsonobject.getJSONObject("idIcon");
                                int idIcon = icons.getInt("idIcon");
                                Icons icon = new Icons(idIcon);
                                Alert newAlert = new Alert(message,verifications,idAlert,icon,alertTime);
                                newAlert.getIdIcon().setUrl(setUrlIcon(newAlert.getIdIcon().getIdIcon()));
                                list.add(newAlert);
                            }
                            model.onResponse(list);
                        }
                        catch (JSONException e) {
                            Log.d("error json",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);
    }

    public static void sendAlert(Context context, Alert newAlert, final ModelListener model) {
        Icons icon = setIconId(newAlert.getIdIcon());
        newAlert.setIdIcon(icon);
        Gson gson = new Gson();

        String jsonString = gson.toJson(newAlert);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(jsonString);
            jsonObject.put("idAlert",null);

        }catch (JSONException e){
            Log.d("error", "error in json parsing");
        }
        Log.d("json",jsonObject.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.113:8080/Bocazas/webresources/entities.alerts";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // do action
                        model.onResponsePut();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //manage error
                        Log.d("error volley",volleyError.getMessage());
                    }
                }
        ) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                try {
                    String json = new String(
                            response.data,
                            "UTF-8"
                    );

                    if (json.length() == 0) {
                        return Response.success(
                                null,
                                HttpHeaderParser.parseCacheHeaders(response)
                        );
                    }
                    else {
                        return super.parseNetworkResponse(response);
                    }
                }
                catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }


            }
        };
        queue.add(jsonObjectRequest);
    }


    private static Icons setIconId(Icons icon){
        int res = 1;

        switch(icon.getIdIcon()){
            case R.id.buttonStaticControl:
                res = 1;
                break;

            case R.id.buttonMovilRadar:
                res = 2;
                break;

            case R.id.buttonRadar:
                res = 3;
                break;

            default:
                res = 4;
                break;
        }

        icon.setIdIcon(res);
        return icon;
    }


    private  static int setUrlIcon(int id){
        int res;
        switch (id){
            case 1:
                res = 2131492870;
                break;

            case 2:
                res = 2131492872;
                break;

            case 3:
                res = 2131492868;
                break;

            default:
                res = 2131492869;
                break;
        }
        return res;
    }
}

