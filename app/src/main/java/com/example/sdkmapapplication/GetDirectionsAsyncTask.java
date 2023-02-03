package com.example.sdkmapapplication;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

public class GetDirectionsAsyncTask extends
        AsyncTask<Map<String, String>, Object, ArrayList> {
    public static final String USER_CURRENT_LAT = "21.005101187931327";
    public static final String USER_CURRENT_LONG = "105.8595212270405";
    public static final String DESTINATION_LAT = "20.99660747254366 ";
    public static final String DESTINATION_LONG = "105.80810864421623";
    public static final String DIRECTIONS_MODE = "driving";
    private WeakReference<MapsActivity> activity;
    private Exception exception;

    // private ProgressDialog progressDialog;

    public GetDirectionsAsyncTask(MapsActivity activity) {
        super();
        this.activity = new WeakReference<MapsActivity>(activity);
    }

    public void onPreExecute() {
        // progressDialog = new ProgressDialog(activity.get().getActivity());
        // progressDialog.setMessage("Calculating directions");
        // progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result) {
        // progressDialog.dismiss();
        try {
            if (exception == null && result != null && result.size() > 0
                    && activity != null) {
                activity.get().handleGetDirectionsResult(result);
            } else {
                processException();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Override
    protected ArrayList doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];
        try {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap
                    .get(USER_CURRENT_LAT)), Double.valueOf(paramMap
                    .get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap
                    .get(DESTINATION_LAT)), Double.valueOf(paramMap
                    .get(DESTINATION_LONG)));
            GMapV2Direction md = new GMapV2Direction();
            Document doc = md.getDocument(fromPosition, toPosition,
                    paramMap.get(DIRECTIONS_MODE));
            ArrayList directionPoints = md.getDirection(doc);
            return directionPoints;
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
            return null;
        }
    }

    private void processException() {
        // Toast.makeText(activity,
        // activity.getString(R.string.error_when_retrieving_data), 3000)
        // .show();
    }
}