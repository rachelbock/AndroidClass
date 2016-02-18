package com.rage.networkapplication;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rage on 2/16/16.
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, JSONObject> {


public static final String TAG = NetworkAsyncTask.class.getSimpleName();


    @Override
    protected String doInBackground(String... params) {

        //create url for our strings
        if (params.length == 0) {
            return null;
        }
        String userId = params[0];

        try {
            String URL = new URL("https://api.github.com/users/" + userId);
            HttpURLConnection
        } catch (IOException e) {
            Log.e(Tag, e.getLocalizedMessage() )
        }

        return null;
    }
}
//what you return from doInBackground gets spit into the onPostExecute
