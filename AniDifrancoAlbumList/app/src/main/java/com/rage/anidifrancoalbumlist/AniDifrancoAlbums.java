package com.rage.anidifrancoalbumlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rage on 2/10/16.
 */
public class AniDifrancoAlbums {
    String [] albumStrs = new String[] {
            "Ani Difranco, 1990,@drawable/ani_difranco_ani_difranco.png",
            "Not So Soft, 1991,@drawable/ani_difranco_not_so_soft.jpg",
            "Imperfectly, 1992, @drawable/ani_difranco_imperfectly.jpg",
            "Puddle Dive, 1993, @drawable/ani_difranco_puddle_dive.jpg",
            "Out of Range, 1994, @drawable/ani_difranco_out_of_range.jpg",
            "Not a Pretty Girl, 1995, @drawable/ani_difranco_not_a_pretty_girl.jpg",
            "Dilate, 1996, @drawable/ani_difranco_dilate.jpeg",
            "Little Plastic Castle, 1998, @drawable/ani_difranco_little_plastic_castle.jpeg",
            "Up Up Up Up Up Up, 1999, @drawable/ani_difranco_up_up_up.jpg",
            "To The Teeth, 1999, @drawable/ani_difranco_to_the_teeth.jpeg",
            "Revelling/Reckoning, 2001, @drawable/ani_difranco_revelling_reckoning.jpg",
            "Evolve, 2003, @drawable/ani_difranco_evolve.jpg",
            "Educated Guess, 2004, @drawable/ani_difranco_educated_guess.jpg",
            "Knuckle Down, 2005, @drawable/ani_difranco_knuckle_down.jpeg",
            "Reprieve, 2006, @drawable/ani_difranco_reprieve.jpeg",
            "Red Letter Year, 2008, @drawable/ani_difranco_red_letter_year.jpg",
            "Which Side Are You On?, 2012, @drawable/ani_difranco_which_side_are_you_on.jpg",
            "Allergic to Water, 2014, @drawable/ani_difranco_allergic_to_water.jpg"
    };
    private ArrayList<AniDifranco> mAlbums;

    public AniDifrancoAlbums() {
        mAlbums = new ArrayList<>();
        for (String str : albumStrs) {
            mAlbums.add(new AniDifranco(str));
        }
    }

    public List<AniDifranco> getAlbums(int count) {
        if (count >= mAlbums.size()) {
            count = mAlbums.size()-1;
        }
        return mAlbums.subList(0, count);
    }

    public ArrayList<AniDifranco> getAlbums() {
        return mAlbums;
    }
}
