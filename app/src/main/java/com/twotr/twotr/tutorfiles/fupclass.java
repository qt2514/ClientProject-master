package com.twotr.twotr.tutorfiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by vignesh2514 on 18/3/18.
 */

public class fupclass implements Parcelable {


    String title;
    File file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static Creator<fupclass> getCREATOR() {
        return CREATOR;
    }

    public fupclass(String title, File file) {
        this.title = title;
        this.file = file;
    }

    protected fupclass(Parcel in) {
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<fupclass> CREATOR = new Creator<fupclass>() {
        @Override
        public fupclass createFromParcel(Parcel in) {
            return new fupclass(in);
        }

        @Override
        public fupclass[] newArray(int size) {
            return new fupclass[size];
        }
    };
}
