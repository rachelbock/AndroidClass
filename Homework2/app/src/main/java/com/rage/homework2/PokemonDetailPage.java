package com.rage.homework2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

        //If there is an internet connection - run the tasks in the asyncTask. If not, throw an error.
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            asyncTask = new NetworkAsyncClass(this);
            asyncTask.execute(pokemon.getId());
        }
        else {
            Toast.makeText(this, R.string.no_internet_connection_error_message, Toast.LENGTH_LONG).show();
        }

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
        setResult(RESULT_OK, intent);
        finish();

    }
}
