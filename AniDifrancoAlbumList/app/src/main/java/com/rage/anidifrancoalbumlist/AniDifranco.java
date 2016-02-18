package com.rage.anidifrancoalbumlist;

/**
 * Created by rage on 2/10/16.
 */
public class AniDifranco {

    private String albumName;
    private String albumYear;
    private String albumPic;

    public AniDifranco(String csvStr) {
        String[] split = csvStr.trim().split(",");
        albumName = split[0];
        albumYear = split[1];
        albumPic = split[2];
    }

    public String getAlbumName() {

        return albumName;
    }
    public String getAlbumYear() {

        return albumYear;
    }
    public String getAlbumPic() {
        return albumPic;
    }

}
