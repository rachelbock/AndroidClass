package com.rage.homework2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ntille on 2/3/16.
 */
public class Pokemon implements Parcelable {

    private String mName;
    private String mId;
    private String mSpeciesId;
    private String mHeight;
    private String mWeight;
    private String mUrl;
    private String baseExperience;
    private String hp;
    private String speed;
    private String specialDefense;
    private String specialAttack;
    private String attack;
    private String defense;


    public Pokemon(String csvStr) {
        String[] split = csvStr.trim().split(",");

        mId = split[0];
        mName = split[1];
        mSpeciesId = split[2];
        mHeight = split[3];
        mWeight = split[4];
    }

    public String getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(String baseExperience) {
        this.baseExperience = baseExperience;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(String specialDefense) {
        this.specialDefense = specialDefense;
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(String specialAttack) {
        this.specialAttack = specialAttack;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getImageUrl() {

        mUrl = "http://img.pokemondb.net/artwork/" + getName() + ".jpg";
        return mUrl;
    }


    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getSpeciesId() {
        return mSpeciesId;
    }

    public String getHeight() {
        return mHeight;
    }

    public String getWeight() {
        return mWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mId);
        dest.writeString(mSpeciesId);
        dest.writeString(mHeight);
        dest.writeString(mWeight);
        dest.writeString(mUrl);
        dest.writeString(baseExperience);
        dest.writeString(hp);
        dest.writeString(speed);
        dest.writeString(specialAttack);
        dest.writeString(specialDefense);
        dest.writeString(attack);
        dest.writeString(defense);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public Pokemon(Parcel source) {
        mName = source.readString();
        mId = source.readString();
        mSpeciesId = source.readString();
        mHeight = source.readString();
        mWeight = source.readString();
        mUrl = source.readString();
        baseExperience = source.readString();
        hp = source.readString();
        speed = source.readString();
        specialAttack = source.readString();
        specialDefense = source.readString();
        attack = source.readString();
        defense = source.readString();
    }
}
