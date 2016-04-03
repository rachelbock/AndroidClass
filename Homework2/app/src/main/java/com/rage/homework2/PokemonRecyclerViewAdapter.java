package com.rage.homework2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rage on 2/18/16.
 * This class creates the adapter for the Pokemon Recycler View.
 */
public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.PokemonViewHolder>{

    /**
     * Instance Variables
     */

    private final OnPokemonRowClickListener Listener;
    private final ArrayList<Pokemon> Pokemons;

    /**
     *Adapter constructor which takes in the arrayList of Pokemon and the OnClickListener `and sets them.
     */
    public PokemonRecyclerViewAdapter(ArrayList<Pokemon> pokemons, OnPokemonRowClickListener listener) {
        Pokemons = pokemons;
        Listener = listener;
    }


    /**
     * Creates the interface for the pokemon row click listener. Used in the onBindViewHolder method
     * for handling clicks on a specific row of pokemon.
     */
    public interface OnPokemonRowClickListener {
        void onPokemonRowClick(Pokemon pokemon);
    }

    /**
     * Three methods for Recycler View Adapter implementation.
     * onCreate inflates and returns the viewHolder - row
     * onBind sets the data at each row to the specific data at each position
     * getItemCount returns the size of the Pokemon arrayList.
     */
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View pokemonRow = inflater.inflate(R.layout.pokemon_row, parent, false);
        return new PokemonViewHolder(pokemonRow);
    }

    @Override
    public void onBindViewHolder(final PokemonViewHolder holder, int position) {
        Pokemon onePokemon = Pokemons.get(position);

        //Gets the weight and height of each pokemon to fill in the %s
        Context context = holder.name.getContext();
        String weight = context.getString(R.string.row_pokemon_weight_text, onePokemon.getWeight());
        String height = context.getString(R.string.row_pokemon_height_text, onePokemon.getHeight());

        //Sets the text at each field to be the specific pokemon at each location.
        holder.name.setText(onePokemon.getName());
        holder.id.setText(onePokemon.getId());
        holder.weight.setText(weight);
        holder.height.setText(height);
        Picasso.with(context).load(onePokemon.getImageUrl()).fit().centerInside().into(holder.image);


        //Sets the on click listener to each row.
        holder.fullView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Listener != null) {
                    Listener.onPokemonRowClick(Pokemons.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Pokemons.size();
    }

    /**
     * ViewHolder class to store the TextViews and views in our pokemon_row layout.
     */
    static class PokemonViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView id;
        TextView weight;
        TextView height;
        ImageView image;
        View fullView;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;
            name = (TextView) itemView.findViewById(R.id.homework2_pokemon_name);
            id = (TextView) itemView.findViewById(R.id.homework2_pokemon_id);
            weight = (TextView) itemView.findViewById(R.id.homework2_pokemon_weight);
            height = (TextView) itemView.findViewById(R.id.homework2_pokemon_height);
            image = (ImageView) itemView.findViewById(R.id.homework2_pokemon_image);
        }
    }

}
