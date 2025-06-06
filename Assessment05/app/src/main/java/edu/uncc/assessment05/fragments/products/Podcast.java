package edu.uncc.assessment05.fragments.products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Podcast {
    private String collectionName;
    private String artistName;
    private String artworkUrl100;
    private String releaseDate;

    private String genres;

    public Podcast() {
    }

    public Podcast(JSONObject jsonObject) throws JSONException {
        this.collectionName = jsonObject.getString("collectionName");
        this.artistName = jsonObject.getString("artistName");
        this.artworkUrl100 = jsonObject.getString("artworkUrl100");
        this.releaseDate = jsonObject.getString("releaseDate");
        this.genres = jsonObject.getString("genres");

    }
    public String getCollectionName() {
        return collectionName;
    }
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getArtworkUrl100() {
        return artworkUrl100;
    }
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }


}
