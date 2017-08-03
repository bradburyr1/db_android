package com.example.ryan.places;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

//import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ryan on 7/20/2017.
 */

public class GetAllMarkers extends ListActivity {
    public String rst = "";//will contain the json string


/*
    //For progress dialog
    /*private ProgressDialog ProgDial;

    //Create a JSON Parser
    JSONParser JPars = new JSONParser();

    ArrayList<HashMap<String, String>> markerList;
*/

public void starter(){
    Log.d("HELLO*************", "Starter");
    fetch();
}

public void fetch() {
    Log.d("HELLO*************", "Fetch");
    FetchMarkersTask fmt = new FetchMarkersTask();
    fmt.execute();
}

    private class FetchMarkersTask extends AsyncTask<Void, Void, String> {

        //////////////////////////////////
        final String ip_address = "192.168.1.21";
        final String project = "android_connect";
        final String file = "get_all_locations.php";

        String builtUri = "http://" + ip_address + "/" + project + "/" + file;

        //@Override
        protected void onPostExecute(String result){
            MapsActivity ma = new MapsActivity();
            ma.getLong(result);
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            String response = "";
            Log.d("HELLO*************", "doInBackground: " + builtUri);
            try {
                URL url = new URL(builtUri);
                urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8192);
                    String jsonResp = null;
                    while ((jsonResp = input.readLine()) != null) {
                        response = response.concat(jsonResp);
                        Log.d("HELLO*************", "While Loop");
                        Log.d("HELLO*************", "Response: " + response);
                        Log.d("HELLO*************", "jsonResp: " + jsonResp);
                    }
                    input.close();
                }
            } catch (IOException e) {
                Log.d("HELLO*************", "HERES YOUR DANG STRING", e);
            }

            ////////////
            Log.d("JSON Line", response);
            rst = response;//store the json string
            ///////////
            return response;
        }

        //return response;
    }
    //////////////////////////////////
}
