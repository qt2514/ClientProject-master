package com.twotr.twotr.guestfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vignesh2514 on 28/2/18.
 */

public class GuestData {
    private List<GuestFilters> mList = new ArrayList<>();

    public GuestData(List<GuestFilters> mList) {
        this.mList = mList;
    }

    public List<GuestFilters> getAllMovies() {
        return mList;
    }

    public void setmList(List<GuestFilters> mList) {
        this.mList = mList;
    }

    public List<GuestFilters> getStypeFilteredMovies(List<String> stype, List<GuestFilters> mList) {
        List<GuestFilters> tempList = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            for (String g : stype) {
                if (guestfilt.getStype().equalsIgnoreCase(g)) {
                    tempList.add(guestfilt);
                }
            }

        }
        return tempList;
    }

    public List<GuestFilters> getPriceFilteredMovies(List<String> price, List<GuestFilters> mList) {
        List<GuestFilters> tempList = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            for (String y : price) {
                if (guestfilt.getPrice() == Integer.parseInt(y)) {
                    tempList.add(guestfilt);
                }
            }
        }
        return tempList;
    }

    public List<GuestFilters> getGradeFilteredMovies(List<String> grade, List<GuestFilters> mList) {
        List<GuestFilters> tempList = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            for (String q : grade) {
                if (guestfilt.getGradelevel().equalsIgnoreCase(q)) {
                    tempList.add(guestfilt);
                }
            }
        }
        return tempList;
    }

    public List<GuestFilters> getRatingFilteredMovies(List<String> rating, List<GuestFilters> mList) {
        List<GuestFilters> tempList = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            for (String r : rating) {
                if (guestfilt.getRating() >= Float.parseFloat(r.replace(">",""))) {
                    tempList.add(guestfilt);
                }
            }
        }
        return tempList;
    }

    public List<String> getUniqueStypeKeys() {
        List<String> stype = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            if (!stype.contains(guestfilt.getStype())) {
                stype.add(guestfilt.getStype());
            }
        }
        Collections.sort(stype);
        return stype;
    }

    public List<String> getUniquePriceKeys() {
        List<String> price = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            if (!price.contains(guestfilt.getPrice() + "")) {
                price.add(guestfilt.getPrice() + "");
            }
        }
        Collections.sort(price);
        return price;
    }

    public List<String> getUniqueGradeKeys() {
        List<String> grade = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            if (!grade.contains(guestfilt.getGradelevel())) {
                grade.add(guestfilt.getGradelevel());
            }
        }
        Collections.sort(grade);
        return grade;
    }

    public List<String> getUniqueRatingKeys() {
        List<String> ratings = new ArrayList<>();
        for (GuestFilters guestfilt : mList) {
            int rating = (int) Math.floor(guestfilt.getRating());
            String rate = "> " + rating;
            if (!ratings.contains(rate)) {
                ratings.add(rate);
            }
        }
        Collections.sort(ratings);
        return ratings;
    }




}
