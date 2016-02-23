package com.rage.homework2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rage on 2/18/16.
 */
public class NetworkAsyncClass extends AsyncTask<Pokemon, Integer, JSONObject> {



    public static final String TAG = NetworkAsyncClass.class.getSimpleName();
    PokemonDetailPage detailPage;
    public NetworkAsyncClass(PokemonDetailPage detailPage) {
        this.detailPage = detailPage;
    }


    /**
     * The following methods are required for the AsyncTask implementation.
     * OnPreExecute - create a log message to say that the task has started.
     * DoInBackground - connect to the API and create a JSON object from the data at the url. this
     * method takes in a Pokemon object - the url is based off of the ID of that pokemon.
     * onProgressUpdate - sets the progress bar to run.
     * onCancelled - creates a log message to say that the task has been cancelled.
     * onPostExecute - takes in the JSONobject from DoInBackground and finds the specific fields
     * in the JSON object and calls the setText method from the DetailPage to update the view.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started Async Task!");
    }

    @Override
    protected JSONObject doInBackground(Pokemon... params) {

        if (params.length == 0) {
            return null;
        }
        Pokemon onePokemon = params[0];

        JSONObject jsonObject = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL("http://pokeapi.co/api/v2/pokemon/" + onePokemon.getId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            while (true) {

                line = reader.readLine();
                if (line == null) {
                    break;
                }
                stringBuilder.append(line);
                if (isCancelled()) {
                    return null;
                }
            }
            jsonObject = new JSONObject(stringBuilder.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    @Override
    protected void onProgressUpdate(Integer... params) {
        ProgressBar pBar = (ProgressBar) detailPage.findViewById(R.id.detail_page_progress_bar);
        pBar.animate();
    }

    @Override
    protected void onCancelled(JSONObject jsonObject) {
        super.onCancelled();
        Log.d(TAG, "AsyncTask Cancelled!");
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if (jsonObject == null) {
            Log.d(TAG, "The resulting jsonObject is null");
        } else {
            Pokemon pokemon = detailPage.getPokemon();

            try {
                String base_experience = jsonObject.getString("base_experience");
                pokemon.setBaseExperience(base_experience);

                JSONArray statsArray = jsonObject.getJSONArray("stats");

                for (int i = 0; i < statsArray.length(); i++) {
                    JSONObject stat = statsArray.getJSONObject(i);
                    String baseStat = stat.getString("base_stat");

                    JSONObject subStat = stat.getJSONObject("stat");
                    String statName = subStat.getString("name");

                    if (statName.equals("speed")) {
                        pokemon.setSpeed(baseStat);
                    } else if (statName.equals("special-defense")) {
                        pokemon.setSpecialDefense(baseStat);
                    } else if (statName.equals("special-attack")) {
                        pokemon.setSpecialAttack(baseStat);
                    } else if (statName.equals("defense")) {
                        pokemon.setDefense(baseStat);
                    } else if (statName.equals("attack")) {
                        pokemon.setAttack(baseStat);
                    } else if (statName.equals("hp")) {
                        pokemon.setHp(baseStat);
                    }
                }
                detailPage.setTextFields(pokemon);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
