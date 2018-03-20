package com.twotr.twotr.tutorfiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh2514 on 30/1/18.
 */

public class Schedule_upcoming_list implements Parcelable {
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
    private ArrayList<String> startli;
    private ArrayList<String> endli;

    private ArrayList<String> prename;
    private ArrayList<String> preurl;
    private ArrayList<String> preid;

    private ArrayList<String> posname;
    private ArrayList<String> posurl;
    private ArrayList<String> posid;

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

    public ArrayList<String> getPrename() {
        return prename;
    }

    public void setPrename(ArrayList<String> prename) {
        this.prename = prename;
    }

    public ArrayList<String> getPreurl() {
        return preurl;
    }

    public void setPreurl(ArrayList<String> preurl) {
        this.preurl = preurl;
    }

    public ArrayList<String> getPreid() {
        return preid;
    }

    public void setPreid(ArrayList<String> preid) {
        this.preid = preid;
    }

    public ArrayList<String> getPosname() {
        return posname;
    }

    public void setPosname(ArrayList<String> posname) {
        this.posname = posname;
    }

    public ArrayList<String> getPosurl() {
        return posurl;
    }

    public void setPosurl(ArrayList<String> posurl) {
        this.posurl = posurl;
    }

    public ArrayList<String> getPosid() {
        return posid;
    }

    public void setPosid(ArrayList<String> posid) {
        this.posid = posid;
    }

    public static Creator<Schedule_upcoming_list> getCREATOR() {
        return CREATOR;
    }

    public Schedule_upcoming_list() {
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
        this.startli = startli;
        this.endli = endli;
        this.prename = prename;
        this.preurl = preurl;
        this.preid = preid;
        this.posname = posname;
        this.posurl = posurl;
        this.posid = posid;
    }

    protected Schedule_upcoming_list(Parcel in) {
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
        startli = in.createStringArrayList();
        endli = in.createStringArrayList();
        prename = in.createStringArrayList();
        preurl = in.createStringArrayList();
        preid = in.createStringArrayList();
        posname = in.createStringArrayList();
        posurl = in.createStringArrayList();
        posid = in.createStringArrayList();
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
        dest.writeStringList(startli);
        dest.writeStringList(endli);
        dest.writeStringList(prename);
        dest.writeStringList(preurl);
        dest.writeStringList(preid);
        dest.writeStringList(posname);
        dest.writeStringList(posurl);
        dest.writeStringList(posid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Schedule_upcoming_list> CREATOR = new Creator<Schedule_upcoming_list>() {
        @Override
        public Schedule_upcoming_list createFromParcel(Parcel in) {
            return new Schedule_upcoming_list(in);
        }

        @Override
        public Schedule_upcoming_list[] newArray(int size) {
            return new Schedule_upcoming_list[size];
        }
    };
}
