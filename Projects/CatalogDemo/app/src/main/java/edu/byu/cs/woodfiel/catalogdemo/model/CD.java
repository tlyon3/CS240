package edu.byu.cs.woodfiel.catalogdemo.model;

/**
 * Created by Scott Woodfield on 2/2/2016.
 */
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CD {
    private long id;
    private String title;
    private String artist;
    private String country;
    private String company;
    private float price;
    private int year;

    public CD(JSONObject cdObject) throws JSONException {
        title = cdObject.getString("TITLE");
        artist = cdObject.getString("ARTIST");
        country = cdObject.getString("COUNTRY");
        company = cdObject.getString("COMPANY");
        price = (float)cdObject.getDouble("PRICE");
        year = cdObject.getInt("YEAR");

        Log.i("JsonDomParserExample", title + ", " + artist);
    }

    public CD(int id, String title, String artist, String country, String company, float price, int year) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.country = country;
        this.company = company;
        this.price = price;
        this.year = year;

        Log.i("JsonDomParserExample",
                title + ", " + artist + ", " + country + ", " +
                        company + ", " + price + ", " + year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CD)) return false;

        CD cd = (CD) o;

        if (getId() != cd.getId()) return false;
        if (Float.compare(cd.getPrice(), getPrice()) != 0) return false;
        if (getYear() != cd.getYear()) return false;
        if (getTitle() != null ? !getTitle().equals(cd.getTitle()) : cd.getTitle() != null)
            return false;
        if (getArtist() != null ? !getArtist().equals(cd.getArtist()) : cd.getArtist() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(cd.getCountry()) : cd.getCountry() != null)
            return false;
        return !(getCompany() != null ? !getCompany().equals(cd.getCompany()) : cd.getCompany() != null);

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    public long getId() {return id;}
    public String getTitle() {return title;}
    public String getArtist() {return artist;}
    public String getCountry() {return country;}
    public String getCompany() {return company;}
    public float getPrice() {return price;}
    public int getYear() {return year;}

    public void setId(long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public void setCountry(String country) {this.country = country;}
    public void setCompany(String company) {this.company = company;}
    public void setPrice(float price) {this.price = price;}
    public void setYear(int year) {
        this.year = year;
    }
}

