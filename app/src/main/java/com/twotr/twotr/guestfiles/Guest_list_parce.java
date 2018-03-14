package com.twotr.twotr.guestfiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by vignesh2514 on 23/2/18.
 */

public class Guest_list_parce implements Parcelable {

    private String totalRecords;
    private String _id;
    private String subjectId;
    private String type;
    private String studentsCount;
    private String price;
    private String description;
    private String createdBy;
    private String createdAt;
    private String lat;
    private String lng;
    private Boolean isUserSubject;
    private String start;
    private String end;
    private String classId;
    private Boolean isActive;
    private String subject;
    private  String minPrice;
    private String createdByName;
    private ArrayList<String> startli;
    private ArrayList<String> endli;
private  String url;

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(String studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Boolean getUserSubject() {
        return isUserSubject;
    }

    public void setUserSubject(Boolean userSubject) {
        isUserSubject = userSubject;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public ArrayList<String> getStartli() {
        return startli;
    }

    public void setStartli(ArrayList<String> startli) {
        this.startli = startli;
    }

    public ArrayList<String> getEndli() {
        return endli;
    }

    public void setEndli(ArrayList<String> endli) {
        this.endli = endli;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Creator<Guest_list_parce> getCREATOR() {
        return CREATOR;
    }

    public Guest_list_parce() {
        this.totalRecords = totalRecords;
        this._id = _id;
        this.subjectId = subjectId;
        this.type = type;
        this.studentsCount = studentsCount;
        this.price = price;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lat = lat;
        this.lng = lng;
        this.isUserSubject = isUserSubject;
        this.start = start;
        this.end = end;
        this.classId = classId;
        this.isActive = isActive;
        this.subject = subject;
        this.minPrice = minPrice;
        this.createdByName = createdByName;
        this.startli = startli;
        this.endli = endli;
        this.url = url;
    }

    protected Guest_list_parce(Parcel in) {
        totalRecords = in.readString();
        _id = in.readString();
        subjectId = in.readString();
        type = in.readString();
        studentsCount = in.readString();
        price = in.readString();
        description = in.readString();
        createdBy = in.readString();
        createdAt = in.readString();
        lat = in.readString();
        lng = in.readString();
        byte tmpIsUserSubject = in.readByte();
        isUserSubject = tmpIsUserSubject == 0 ? null : tmpIsUserSubject == 1;
        start = in.readString();
        end = in.readString();
        classId = in.readString();
        byte tmpIsActive = in.readByte();
        isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
        subject = in.readString();
        minPrice = in.readString();
        createdByName = in.readString();
        startli = in.createStringArrayList();
        endli = in.createStringArrayList();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalRecords);
        dest.writeString(_id);
        dest.writeString(subjectId);
        dest.writeString(type);
        dest.writeString(studentsCount);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(createdBy);
        dest.writeString(createdAt);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeByte((byte) (isUserSubject == null ? 0 : isUserSubject ? 1 : 2));
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(classId);
        dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
        dest.writeString(subject);
        dest.writeString(minPrice);
        dest.writeString(createdByName);
        dest.writeStringList(startli);
        dest.writeStringList(endli);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Guest_list_parce> CREATOR = new Creator<Guest_list_parce>() {
        @Override
        public Guest_list_parce createFromParcel(Parcel in) {
            return new Guest_list_parce(in);
        }

        @Override
        public Guest_list_parce[] newArray(int size) {
            return new Guest_list_parce[size];
        }
    };
}
