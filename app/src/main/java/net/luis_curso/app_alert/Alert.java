package net.luis_curso.app_alert;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by luis on 23/03/2018.
 */

public class Alert {
    private String message;
    private int verifications;
    private int idAlert;
    private Icons idIcon;
    private long alertTime;

    public Alert(String message, int verifications, int idAlert, Icons icon, long alertTime) {
        this.message = message;
        this.verifications = verifications;
        this.alertTime = alertTime;
        this.idIcon = icon;
        this.idAlert = idAlert;
    }
    public Alert(String message, int verifications, Icons icon, long alertTime) {
        this.message = message;
        this.verifications = verifications;
        this.alertTime = alertTime;
        this.idIcon = icon;
    }


    public String getDescription() {
        return message;
    }

    public String getVerifications(){ return "Verficado: "+String.valueOf(verifications); }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVerifications(int verifications) {
        this.verifications = verifications;
    }

    public int getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(int idAlert) {
        this.idAlert = idAlert;
    }

    public Icons getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(Icons idIcon) {
        this.idIcon = idIcon;
    }

    public long getAlertTime() {
        return alertTime;
    }

    public String getStringDate(){


        Date d = new Date(this.alertTime);

        // Create an instance of SimpleDateFormat used for formatting
// the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("HH:mm");


// Using DateFormat format method we can create a string
// representation of a date with the defined format.
        String reportDate = df.format(d);

        return reportDate;
/*
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(this.alertTime),
                TimeUnit.MILLISECONDS.toMinutes(this.alertTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.alertTime)),
                TimeUnit.MILLISECONDS.toSeconds(this.alertTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.alertTime)));
        return hms;*/
    }


    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }
}
