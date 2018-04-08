package net.luis_curso.app_alert;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

/**
 * Created by luis on 23/03/2018.
 */
public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Alert>alert;
    private AlertList alertList;

    public Adapter(Context context, ArrayList<Alert> alert, AlertList alertList) {
        this.alertList = alertList;
        this.context = context;
        this.alert = alert;
    }


    @Override
    public int getCount() {
        return alert.size();
    }

    @Override
    public Object getItem(int i) {
        return alert.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView( final int i, View view, ViewGroup viewGroup) {

        Alert item = (Alert) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.item,null);
        ImageView imgphoto= view.findViewById(R.id.img);
        TextView title=view.findViewById(R.id.title);
        TextView descrip=view.findViewById(R.id.desc);
        TextView verif = view.findViewById(R.id.verif);
        Button button =(Button) view.findViewById(R.id.button);
        TextView time = view.findViewById(R.id.time);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVerifications(i,view);
            }
        });


        imgphoto.setImageResource(item.getIdIcon().getUrl());
        String text = this.typeAlert(item.getIdIcon().getIdIcon());
        title.setText(text);
        descrip.setText(item.getDescription());
        verif.setText(item.getVerifications());
        time.setText(item.getStringDate());

        return view;
    }


    private void setVerifications(int position,final View view){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.113:8080/Bocazas/webresources/entities.alerts/add/" + this.alert.get(position).getIdAlert();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(view.getContext(), "Verification success", Toast.LENGTH_LONG).show();
                        alertList.callFacade();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    private String typeAlert(int id){
        String res = "";
        switch (id){
            case 1:
                res = "Police Control";
                break;

            case 2:
                res = "Radar in Police Car";
                break;

            case 3:
                res = "Mobile Radar";
                break;

            default:
                res = "Helicopter";
                break;
        }
        return res;
    }
}
