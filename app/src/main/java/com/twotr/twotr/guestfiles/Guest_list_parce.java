package com.twotr.twotr.guestfiles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 23/2/18.
 */

public class Guest_list_parce implements Parcelable {

    String _id;
    String minPrice;
    String studentsCount;
    String subjectId;
    String description;
    String type;
    String price;
    String isActive;
    String post;
    String pre;
    String lat;
    String lng;
    String groupKey;
    String availableCount;
    String slotPrice;
    String gradeLevel;
    String isUserSubject;
    String createdByName;
    String subject;
String start;
String end;

    public Guest_list_parce() {
        this._id = _id;
        this.minPrice = minPrice;
        this.studentsCount = studentsCount;
        this.subjectId = subjectId;
        this.description = description;
        this.type = type;
        this.price = price;
        this.isActive = isActive;
        this.post = post;
        this.pre = pre;
        this.lat = lat;
        this.lng = lng;
        this.groupKey = groupKey;
        this.availableCount = availableCount;
        this.slotPrice = slotPrice;
        this.gradeLevel = gradeLevel;
        this.isUserSubject = isUserSubject;
        this.createdByName = createdByName;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(String studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
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

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(String availableCount) {
        this.availableCount = availableCount;
    }

    public String getSlotPrice() {
        return slotPrice;
    }

    public void setSlotPrice(String slotPrice) {
        this.slotPrice = slotPrice;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getIsUserSubject() {
        return isUserSubject;
    }

    public void setIsUserSubject(String isUserSubject) {
        this.isUserSubject = isUserSubject;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public static Creator<Guest_list_parce> getCREATOR() {
        return CREATOR;
    }

    protected Guest_list_parce(Parcel in) {
        _id = in.readString();
        minPrice = in.readString();
        studentsCount = in.readString();
        subjectId = in.readString();
        description = in.readString();
        type = in.readString();
        price = in.readString();
        isActive = in.readString();
        post = in.readString();
        pre = in.readString();
        lat = in.readString();
        lng = in.readString();
        groupKey = in.readString();
        availableCount = in.readString();
        slotPrice = in.readString();
        gradeLevel = in.readString();
        isUserSubject = in.readString();
        createdByName = in.readString();
        subject = in.readString();
        start = in.readString();
        end = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(minPrice);
        dest.writeString(studentsCount);
        dest.writeString(subjectId);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(price);
        dest.writeString(isActive);
        dest.writeString(post);
        dest.writeString(pre);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(groupKey);
        dest.writeString(availableCount);
        dest.writeString(slotPrice);
        dest.writeString(gradeLevel);
        dest.writeString(isUserSubject);
        dest.writeString(createdByName);
        dest.writeString(subject);
        dest.writeString(start);
        dest.writeString(end);
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
