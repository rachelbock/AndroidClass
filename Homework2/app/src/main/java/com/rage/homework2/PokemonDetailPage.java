package com.rage.homework2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PokemonDetailPage extends AppCompatActivity {

    public static final String ARG_POKEMON = "ArgPokemon";
    public static final double MAX_BAR_SIZE = 200;
    public static final int BAR_SIZE_SCALE = 1000;
    private Pokemon pokemon;
    NetworkAsyncClass asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail_page);

        pokemon = getIntent().getParcelableExtra(ARG_POKEMON);

        //Set static fields
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
            //check to see if one of the data elements received in the async task is null. If not,
            //get the text fields from the pokemon object and set them. If it is null, execute the
            //async task on the pokemon.
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

    /**
     * Method to set the stat Image View width. Used in the setTextFields method to update the
     * detail page.
     */
    public void setStatBar (String pokeStatStr, int imageViewID) {
        int pokeStat = Integer.parseInt(pokeStatStr);
        ImageView statBar = (ImageView) findViewById(imageViewID);
        double barSize = (pokeStat/MAX_BAR_SIZE)*BAR_SIZE_SCALE;
        statBar.getLayoutParams().width = (int) barSize;
        statBar.requestLayout();

    }

    /**
     * This function takes care of setting the text fields for base Experience and stats. It is
     * called in the async task to set the text fields if they have not been populated before. If the
     * text fields have already been populated, it sets them to the page. It also takes care of removing
     * the progress bar once the async task is completed and setting the fields visible.
     */

    public void setTextFields (Pokemon pokemon) {

        ProgressBar pBar = (ProgressBar) findViewById(R.id.detail_page_progress_bar);
        pBar.setVisibility(View.INVISIBLE);

        CardView statsCardView = (CardView) findViewById(R.id.detail_page_card_view);
        statsCardView.setVisibility(View.VISIBLE);

        TextView baseExperience = (TextView) findViewById(R.id.detail_page_base_experience_text);
        baseExperience.setText(getString(R.string.base_experience, pokemon.getBaseExperience()));

        TextView speed = (TextView) findViewById(R.id.detail_page_speed_text);
        speed.setText(getString(R.string.speed, pokemon.getSpeed()));
        setStatBar(pokemon.getSpeed(), R.id.detail_page_speed_bar);

        TextView specialDefense = (TextView) findViewById(R.id.detail_page_special_defense_text);
        specialDefense.setText(getString(R.string.special_defense, pokemon.getSpecialDefense()));
        setStatBar(pokemon.getSpecialDefense(), R.id.detail_page_special_defense_bar);

        TextView specialAttack = (TextView) findViewById(R.id.detail_page_special_attack_text);
        specialAttack.setText(getString(R.string.special_attack, pokemon.getSpecialAttack()));
        setStatBar(pokemon.getSpecialAttack(), R.id.detail_page_special_attack_bar);

        TextView defense = (TextView) findViewById(R.id.detail_page_defense_text);
        defense.setText(getString(R.string.defense, pokemon.getDefense()));
        setStatBar(pokemon.getDefense(), R.id.detail_page_defense_bar);

        TextView attack = (TextView) findViewById(R.id.detail_page_attack_text);
        attack.setText(getString(R.string.attack, pokemon.getAttack()));
        setStatBar(pokemon.getAttack(), R.id.detail_page_attack_bar);

        TextView hp = (TextView) findViewById(R.id.detail_page_hp_text);
        hp.setText(getString(R.string.hp, pokemon.getHp()));
        setStatBar(pokemon.getHp(), R.id.detail_page_hp_bar);
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
