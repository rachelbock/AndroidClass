package com.rage.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements PokemonRecyclerViewAdapter.OnPokemonRowClickListener {

    public static final int RESPONSE_CODE_POKEMON = 0;
    private Pokedex pokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.homework2_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pokedex = new Pokedex();
        PokemonRecyclerViewAdapter adapter = new PokemonRecyclerViewAdapter(pokedex.getPokemons(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * When a row is clicked - go to the detail page and start that activity.
     */
    @Override
    public void onPokemonRowClick(Pokemon pokemon) {
        Intent intent = new Intent(MainActivity.this, PokemonDetailPage.class);
        intent.putExtra(PokemonDetailPage.ARG_POKEMON, pokemon);
        startActivityForResult(intent, RESPONSE_CODE_POKEMON);
        //entering id and exiting id
//        overridePendingTransition(android.R.anim.slideleft, slideright);
        //create a new resource director - anim - new animation resource file
        //example - slide in from left/right <translate from x delta 100% to x delta 0% for left do 0 to -100%
        //also give it a duration
    }

    /**
     * When the row has bee clicked and the detail page opened. As long as the activity opened
     * appropriately - find the pokemon that was clicked on and set the data collected from the async
     * task to the pokemon object. This will take care of storing that data in case the same pokemon
     * row is cliked on again.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESPONSE_CODE_POKEMON && resultCode == RESULT_OK) {
            Pokemon aPokemon = data.getParcelableExtra(PokemonDetailPage.ARG_POKEMON);
            //loop over all of the pokemon - if the pokemon at i has same id as apokemon than swap out that index.
            for (int i = 0; i < pokedex.getPokemons().size(); i++) {
                if (pokedex.getPokemons().get(i).getId().equals(aPokemon.getId())) {
                    pokedex.getPokemons().set(i, aPokemon);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
