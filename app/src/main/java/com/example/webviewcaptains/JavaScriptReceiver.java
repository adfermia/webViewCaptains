package com.example.webviewcaptains;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.function.Function;
import java.util.function.Predicate;

public class JavaScriptReceiver {

    Location location;
    double latitude;
    double longitude;
    Context mContext;


    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Posicion pos;
    LocationManager locationManager;

    boolean firstTime = true;
    boolean primeraVez = true;


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 metros

    private static final long MIN_TIME_BW_UPDATES = 1;
    // JavaScriptReceiver(Context c) {
    //    mContext =c;
    //}

    // @JavascriptInterface
    // public double getLatitud() {
    //    double latitud = 0.0;

    //    GPSTracker gps = new GPSTracker(mContext);

    //    latitud = gps.getLocation().getLatitude();

    //    return latitud;
    //}

    // Com angular no reconeix aquestes promeses, fem les operacions necessaries aci
    // i retornem un valor que si que entenga angular
    @JavascriptInterface
    public double getLatitude(){
        Promise p = new Promise();
        Double latitud = 0.0;
        GPSTracker gps = new GPSTracker(mContext);
        boolean b = gps==null;
        Log.v("GPS", Boolean.toString(b));
        latitud = gps.getLocation().getLatitude();
        p.resolve(latitud);
        p.reject("Something failed");
        new AsyncTaskLatitude().execute(p);
        return this.latitude;
    }
    @JavascriptInterface
    public double getLongitude() {
        Promise p = new Promise();
        Double longitud = 0.0;
        GPSTracker gps = new GPSTracker(mContext);
        longitud = gps.getLocation().getLongitude();
        p.resolve(longitud);
        p.reject("Something failed");
        new AsyncTaskLongitude().execute(p);


        return this.longitude;
    }




//    public Promise getPromiseLocation() {
//        // Creem la promesa
//        Promise p  = new Promise();
//
//        Double posDouble[] = {0.0, 0.0};
//        Posicion pos = new Posicion();
//        Double latitud = 0.0;
//        Double longitud = 0.0;
//
//
//        // La instancia del gpsTracker per a que ens retorne la localitzacio
//        GPSTracker gps = new GPSTracker(mContext);
//        latitud = gps.getLocation().getLatitude();
//        longitud = gps.getLocation().getLongitude();
//        posDouble[0] = latitud;
//        posDouble[1] = longitud;
//
//
//        // Assignem a la posicio els valors corresponents
//        pos.setLatitud(latitud);
//        pos.setLongitud(longitud);
//        Log.v("Posicion:",Double.toString(pos.getLatitud()));
//        Log.v("Posicion:",Double.toString(pos.getLongitud()));
//
//        p.resolve(latitud);
//        p.reject("Something failed");
//
//        return p;
//
//    }

    JavaScriptReceiver(Context c) {
        mContext =c;
    }


    // Aquestes eren les funcions que gastava abans de la que gasta la promesa
//    @JavascriptInterface
//    public double getLatitud() {
//        double latitud = 0.0;
//
//        GPSTracker gps = new GPSTracker(mContext);
//
//        latitud = gps.getLocation().getLatitude();
//
//        return latitud;
//        }

//    @JavascriptInterface
//    public double getLongitud() {
//        double longitud = 0.0;
//
//        GPSTracker gps = new GPSTracker(mContext);
//
//        longitud = gps.getLocation().getLongitude();
//
//        return longitud;
//    }
    public class AsyncTaskLongitude extends AsyncTask<Promise, Double, Double> {

        Double longitud;



    @Override
    protected void onPreExecute() {

    }
    @Override
    protected Double doInBackground(Promise... promises) {

        Promise p = promises[0];

        p.then( (res) -> {
            longitud = ((double)res);
            publishProgress(longitud);
            return true;
        });

        return longitud;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        longitude = values[0];
    }
}

    public class AsyncTaskLatitude extends AsyncTask<Promise, Double, Double> {

        Double latitud;



        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Double doInBackground(Promise... promises) {

            Promise p = promises[0];

            p.then( (res) -> {
                latitud = ((double)res);
                publishProgress(latitud);
                return true;
            });

            return latitud;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            latitude = values[0];
        }
    }

}



