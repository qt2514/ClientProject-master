package com.twotr.twotr.guestfiles;

/**
 * Created by vignesh2514 on 28/2/18.
 */

public class GuestFilters {

    private String stype;
    private Float rating;
    private int price;
    private String gradelevel;
    private String searchby;

    public GuestFilters(String stype, Float rating, int price, String gradelevel, String searchby) {
        this.stype = stype;
        this.rating = rating;
        this.price = price;
        this.gradelevel = gradelevel;
        this.searchby = searchby;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGradelevel() {
        return gradelevel;
    }

    public void setGradelevel(String gradelevel) {
        this.gradelevel = gradelevel;
    }

    public String getSearchby() {
        return searchby;
    }

    public void setSearchby(String searchby) {
        this.searchby = searchby;
    }
}
