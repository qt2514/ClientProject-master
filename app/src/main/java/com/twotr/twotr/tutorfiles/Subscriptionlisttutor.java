package com.twotr.twotr.tutorfiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by vignesh2514 on 14/3/18.
 */

public class Subscriptionlisttutor implements Parcelable {

    private String _id;
    private String title;
    private String description;
    private String price;
    private String paymentType;
    private String start;
    private String end;
    private String createdBy;
    private String lastModifiedAt;
    private String astModifiedBy;
    private String url;
    private String createdAt;
    private ArrayList<String> gradeLevel;


    protected Subscriptionlisttutor(Parcel in) {
        _id = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readString();
        paymentType = in.readString();
        start = in.readString();
        end = in.readString();
        createdBy = in.readString();
        lastModifiedAt = in.readString();
        astModifiedBy = in.readString();
        url = in.readString();
        createdAt = in.readString();
        gradeLevel = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(paymentType);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(createdBy);
        dest.writeString(lastModifiedAt);
        dest.writeString(astModifiedBy);
        dest.writeString(url);
        dest.writeString(createdAt);
        dest.writeStringList(gradeLevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subscriptionlisttutor> CREATOR = new Creator<Subscriptionlisttutor>() {
        @Override
        public Subscriptionlisttutor createFromParcel(Parcel in) {
            return new Subscriptionlisttutor(in);
        }

        @Override
        public Subscriptionlisttutor[] newArray(int size) {
            return new Subscriptionlisttutor[size];
        }
    };

    public Subscriptionlisttutor() {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.paymentType = paymentType;
        this.start = start;
        this.end = end;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.astModifiedBy = astModifiedBy;
        this.url = url;
        this.createdAt = createdAt;
        this.gradeLevel = gradeLevel;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getAstModifiedBy() {
        return astModifiedBy;
    }

    public void setAstModifiedBy(String astModifiedBy) {
        this.astModifiedBy = astModifiedBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<String> getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(ArrayList<String> gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public static Creator<Subscriptionlisttutor> getCREATOR() {
        return CREATOR;
    }
}
