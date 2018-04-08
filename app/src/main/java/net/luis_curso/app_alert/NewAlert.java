package net.luis_curso.app_alert;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dono on 23/03/2018.
 */

public class NewAlert extends Fragment implements View.OnClickListener {

        View rootView;

        ImageButton selectedButton;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.newalert, container, false);
            Button send = (Button) rootView.findViewById(R.id.buttonAddNewAlert);
            send.setEnabled(false);
            send.setOnClickListener(this);

            ImageButton hercules = (ImageButton) rootView.findViewById(R.id.buttonHercules);
            hercules.setOnClickListener(this);

            ImageButton movilRadar = (ImageButton) rootView.findViewById(R.id.buttonMovilRadar);
            movilRadar.setOnClickListener(this);

            ImageButton staticControl = (ImageButton) rootView.findViewById(R.id.buttonStaticControl);
            staticControl.setOnClickListener(this);

            ImageButton radar = (ImageButton) rootView.findViewById(R.id.buttonRadar);
            radar.setOnClickListener(this);
            return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() != R.id.buttonAddNewAlert) {
            clear();

            view.setBackgroundColor(Color.parseColor("#85C1E9"));

            switch (view.getId()) {
                case R.id.buttonHercules:
                    this.selectedButton = view.findViewById(R.id.buttonHercules);
                    break;

                case R.id.buttonMovilRadar:
                    this.selectedButton = view.findViewById(R.id.buttonMovilRadar);
                    break;

                case R.id.buttonRadar:
                    this.selectedButton = view.findViewById(R.id.buttonRadar);
                    break;

                case R.id.buttonStaticControl:
                    this.selectedButton = view.findViewById(R.id.buttonStaticControl);
                    break;
            }

            rootView.findViewById(R.id.buttonAddNewAlert).setEnabled(true);
        } else {
            final EditText editText =(EditText) rootView.findViewById(R.id.textNewAlert);
            Editable text = editText.getText();

            if(!text.toString().equals("")){
                int verifications = 0;

                Alert newAlert = new Alert(text.toString(),verifications,new Icons(this.selectedButton.getId()),System.currentTimeMillis());
                AlertFacade.sendAlert(this.getContext(), newAlert, new ModelListener() {
                    @Override
                    public void onResponse(ArrayList<Alert> modelList) {

                    }

                    @Override
                    public void onResponsePut() {
                        Toast.makeText(getActivity(), "Alert created successfully", Toast.LENGTH_LONG).show();
                        editText.setText("");
                        clear();
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Text can not be empty", Toast.LENGTH_LONG).show();
            }


        }
    }

    private void clear(){
        rootView.findViewById(R.id.buttonMovilRadar).setBackgroundColor(Color.TRANSPARENT);
        rootView.findViewById(R.id.buttonHercules).setBackgroundColor(Color.TRANSPARENT);
        rootView.findViewById(R.id.buttonStaticControl).setBackgroundColor(Color.TRANSPARENT);
        rootView.findViewById(R.id.buttonRadar).setBackgroundColor(Color.TRANSPARENT);
        Button send = (Button) rootView.findViewById(R.id.buttonAddNewAlert);
        send.setEnabled(false);
    }



}
