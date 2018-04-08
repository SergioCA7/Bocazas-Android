package net.luis_curso.app_alert;

/**
 * Created by luis on 23/03/2018.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AlertList extends Fragment implements View.OnClickListener {

    private ListView mylist;
    private Adapter adapter;
    private ArrayList<Alert> listAlerts;
    private View rootView;
    private int position;
    private Button button_reload;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.alertlist, container, false);
        callFacade();



        button_reload= (Button)rootView.findViewById(R.id.reload);
        button_reload.setOnClickListener(this);

        return rootView;
    }

    public void callFacade(){
        final AlertList myAlert = this;
        AlertFacade.getAlerts(getContext(), new ModelListener() {
            @Override
            public void onResponse(ArrayList<Alert> modelList) {
                listAlerts = modelList;
                mylist=(ListView) rootView.findViewById(R.id.mylist);
                adapter= new Adapter(getContext() ,listAlerts,myAlert);
                adapter.notifyDataSetChanged();
                mylist.setAdapter(adapter);
            }
            @Override
            public void onResponsePut() {
            }
        });
    }

    @Override
    public void onClick(View view) {
        callFacade();
    }
}
