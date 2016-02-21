package com.rage.homework2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

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
public class NetworkAsyncClass extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = NetworkAsyncClass.class.getSimpleName();
    private Pokemon pokemon;
    PokemonDetailPage detailPage;

    public NetworkAsyncClass(PokemonDetailPage detailPage) {
        this.detailPage = detailPage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started Async Task!");
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String pokemonId = params[0];

        JSONObject jsonObject = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL("http://pokeapi.co/api/v2/pokemon/" + pokemonId);
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
    protected void onProgressUpdate(Integer...params) {
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
        }
        else {
            TextView experience = (TextView) detailPage.findViewById(R.id.detail_page_base_experience_text);
            TextView speed = (TextView) detailPage.findViewById(R.id.detail_page_speed_text);
            TextView specialDefense = (TextView) detailPage.findViewById(R.id.detail_page_special_defense_text);
            TextView specialAttack = (TextView) detailPage.findViewById(R.id.detail_page_special_attack_text);
            TextView defense = (TextView) detailPage.findViewById(R.id.detail_page_defense_text);
            TextView attack = (TextView) detailPage.findViewById(R.id.detail_page_attack_text);
            TextView hp = (TextView) detailPage.findViewById(R.id.detail_page_hp_text);
            try {
                int base_experience = jsonObject.getInt("base_experience");
                experience.setText(detailPage.getString(R.string.base_experience, base_experience));

                JSONArray statsArray = jsonObject.getJSONArray("stats");

                for (int i = 0; i < statsArray.length(); i++) {
                    JSONObject stat = statsArray.getJSONObject(i);
                    String baseStat = stat.getString("base_stat");
                    JSONObject subStat = stat.getJSONObject("stat");
                    String statName = subStat.getString("name");

                    if (statName.equals("speed")) {
                        speed.setText(detailPage.getString(R.string.speed, baseStat));
                    }
                    else if (statName.equals("special-defense")) {
                        specialDefense.setText(detailPage.getString(R.string.special_defense, baseStat));
                    }
                    else if (statName.equals("special-attack")) {
                        specialAttack.setText(detailPage.getString(R.string.special_attack, baseStat));
                    }
                    else if (statName.equals("defense")) {
                        defense.setText(detailPage.getString(R.string.defense, baseStat));
                    }
                    else if (statName.equals("attack")) {
                        attack.setText(detailPage.getString(R.string.attack, baseStat));
                    }
                    else if (statName.equals("hp")) {
                        hp.setText(detailPage.getString(R.string.hp, baseStat));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
