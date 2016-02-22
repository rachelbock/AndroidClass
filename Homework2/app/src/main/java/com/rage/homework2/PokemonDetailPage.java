package com.rage.homework2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PokemonDetailPage extends AppCompatActivity {

    /**
     * Instance Variables
     */
    public static final String ARG_POKEMON = "ArgPokemon";

    private Pokemon pokemon;
    NetworkAsyncClass asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail_page);


        pokemon = getIntent().getParcelableExtra(ARG_POKEMON);

        //Set title and image of pokemon.
        TextView title = (TextView) findViewById(R.id.detail_page_title_text);
        title.setText(pokemon.getName());
        ImageView image = (ImageView) findViewById(R.id.detail_page_image_view);
        Picasso.with(this).load(pokemon.getImageUrl()).fit().centerInside().into(image);
        TextView heightText = (TextView) findViewById(R.id.detail_page_height_text);
        heightText.setText(getString(R.string.row_pokemon_height_text, pokemon.getHeight()));
        TextView weightText = (TextView) findViewById(R.id.detail_page_weight_text);
        weightText.setText(getString(R.string.row_pokemon_weight_text, pokemon.getWeight()));
        TextView idText = (TextView) findViewById(R.id.detail_page_id_text);
        idText.setText(getString(R.string.detail_page_id, pokemon.getId()));

        //If there is an internet connection - run the tasks in the asyncTask. If not, throw an error.
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            if (pokemon.getBaseExperience() != null) {
                setTextFields(pokemon);
            }
            else {
                asyncTask = new NetworkAsyncClass(this);
                asyncTask.execute(pokemon);
            }
        }
        else {
            Toast.makeText(this, R.string.no_internet_connection_error_message, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * gets a pokemon object. This is used in async task.
     */
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setTextFields (Pokemon pokemon) {

        ProgressBar pBar = (ProgressBar) findViewById(R.id.detail_page_progress_bar);
        pBar.setVisibility(View.INVISIBLE);

        TextView baseExperience = (TextView) findViewById(R.id.detail_page_base_experience_text);
        baseExperience.setText(pokemon.getBaseExperience());

        TextView speed = (TextView) findViewById(R.id.detail_page_speed_text);
        speed.setText(pokemon.getSpeed());

        TextView specialDefense = (TextView) findViewById(R.id.detail_page_special_defense_text);
        specialDefense.setText(pokemon.getSpecialDefense());

        TextView specialAttack = (TextView) findViewById(R.id.detail_page_special_attack_text);
        specialAttack.setText(pokemon.getSpecialAttack());

        TextView defense = (TextView) findViewById(R.id.detail_page_defense_text);
        defense.setText(pokemon.getDefense());

        TextView attack = (TextView) findViewById(R.id.detail_page_attack_text);
        attack.setText(pokemon.getAttack());

        TextView hp = (TextView) findViewById(R.id.detail_page_hp_text);
        hp.setText(pokemon.getHp());
    }

    /**
     * This method indicates what action should be taken when the back button is pressed. In this case, it is creating an intent
     * and passing in an argument (defined as a constant) and the pokemon object. It sets a result to RESULT_OK for that pokemon
     * object.
     */

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(ARG_POKEMON, pokemon);
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        setResult(RESULT_OK, intent);
        finish();

    }
}
