package com.rage.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements PokemonRecyclerViewAdapter.OnPokemonRowClickListener {

    /**
     *Instance Variables
     */
    private RecyclerView recyclerView;
    public static final int CODE_POKEMON = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.homework2_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Pokedex pokedex = new Pokedex();
        PokemonRecyclerViewAdapter adapter = new PokemonRecyclerViewAdapter(pokedex.getPokemons(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onPokemonRowClick(Pokemon pokemon) {
        Intent intent = new Intent(MainActivity.this, PokemonDetailPage.class);
        intent.putExtra(PokemonDetailPage.ARG_POKEMON, pokemon);
        startActivityForResult(intent, CODE_POKEMON);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_POKEMON && resultCode == RESULT_OK) {
            Pokemon aPokemon = data.getParcelableExtra(PokemonDetailPage.ARG_POKEMON);
            Snackbar.make(recyclerView, "I saw " + aPokemon.getName(), Snackbar.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
