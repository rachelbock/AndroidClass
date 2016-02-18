package com.rage.anidifrancoalbumlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rage on 2/10/16.
 */
public class AniDifrancoRecyclerViewAdapter extends RecyclerView.Adapter<AniDifrancoRecyclerViewAdapter.AlbumViewHolder> {

    private final ArrayList<AniDifranco> mADAlbums;

    public AniDifrancoRecyclerViewAdapter(ArrayList<AniDifranco> albums) {
        mADAlbums = albums;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.row_difranco_album, parent, false);
        return new AlbumViewHolder(row);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {

        AniDifranco aniDifranco = mADAlbums.get(position);
        holder.name.setText(aniDifranco.getAlbumName());
        holder.year.setText(aniDifranco.getAlbumYear());



        holder.pic.setImageResource(R.drawable.ani_difranco_out_of_range);
    }

    @Override
    public int getItemCount() {
        return mADAlbums.size();
    }


    static class AlbumViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView year;
        ImageView pic;
        View fullView;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            fullView = itemView;
            name = (TextView) itemView.findViewById(R.id.activity_main_difranco_album);
            year = (TextView) itemView.findViewById(R.id.activity_main_difranco_year);
            pic = (ImageView) itemView.findViewById(R.id.activity_main_difranco_album_picture);
        }
    }

}
