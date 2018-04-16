package com.twotr.twotr.tutorfiles;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification_global_class implements Parcelable{

    private String classId;
    private String isActive;
    private String subjectId;
    private String subject_name;
    private String stud_id;
    private String stud_firstName;
    private String stud_lastName;
    private String stud_middleName;
    private String profile_Picture;
    private String profile_url;
    private String notifi_id;
    private Boolean notifi_isConfirmed;
    private Boolean notifi_isRejected;
    private Boolean notifi_isAccepted;
    private Boolean notifi_isPending;
    private String sched_start;
    private String sched_end;
    private String notifi_status;
private String termmsg;

    public Notification_global_class() {
        this.classId = classId;
        this.isActive = isActive;
        this.subjectId = subjectId;
        this.subject_name = subject_name;
        this.stud_id = stud_id;
        this.stud_firstName = stud_firstName;
        this.stud_lastName = stud_lastName;
        this.stud_middleName = stud_middleName;
        this.profile_Picture = profile_Picture;
        this.profile_url = profile_url;
        this.notifi_id = notifi_id;
        this.notifi_isConfirmed = notifi_isConfirmed;
        this.notifi_isRejected = notifi_isRejected;
        this.notifi_isAccepted = notifi_isAccepted;
        this.notifi_isPending = notifi_isPending;
        this.sched_start = sched_start;
        this.sched_end = sched_end;
        this.notifi_status = notifi_status;
        this.termmsg = termmsg;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getStud_firstName() {
        return stud_firstName;
    }

    public void setStud_firstName(String stud_firstName) {
        this.stud_firstName = stud_firstName;
    }

    public String getStud_lastName() {
        return stud_lastName;
    }

    public void setStud_lastName(String stud_lastName) {
        this.stud_lastName = stud_lastName;
    }

    public String getStud_middleName() {
        return stud_middleName;
    }

    public void setStud_middleName(String stud_middleName) {
        this.stud_middleName = stud_middleName;
    }

    public String getProfile_Picture() {
        return profile_Picture;
    }

    public void setProfile_Picture(String profile_Picture) {
        this.profile_Picture = profile_Picture;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getNotifi_id() {
        return notifi_id;
    }

    public void setNotifi_id(String notifi_id) {
        this.notifi_id = notifi_id;
    }

    public Boolean getNotifi_isConfirmed() {
        return notifi_isConfirmed;
    }

    public void setNotifi_isConfirmed(Boolean notifi_isConfirmed) {
        this.notifi_isConfirmed = notifi_isConfirmed;
    }

    public Boolean getNotifi_isRejected() {
        return notifi_isRejected;
    }

    public void setNotifi_isRejected(Boolean notifi_isRejected) {
        this.notifi_isRejected = notifi_isRejected;
    }

    public Boolean getNotifi_isAccepted() {
        return notifi_isAccepted;
    }

    public void setNotifi_isAccepted(Boolean notifi_isAccepted) {
        this.notifi_isAccepted = notifi_isAccepted;
    }

    public Boolean getNotifi_isPending() {
        return notifi_isPending;
    }

    public void setNotifi_isPending(Boolean notifi_isPending) {
        this.notifi_isPending = notifi_isPending;
    }

    public String getSched_start() {
        return sched_start;
    }

    public void setSched_start(String sched_start) {
        this.sched_start = sched_start;
    }

    public String getSched_end() {
        return sched_end;
    }

    public void setSched_end(String sched_end) {
        this.sched_end = sched_end;
    }

    public String getNotifi_status() {
        return notifi_status;
    }

    public void setNotifi_status(String notifi_status) {
        this.notifi_status = notifi_status;
    }

    public String getTermmsg() {
        return termmsg;
    }

    public void setTermmsg(String termmsg) {
        this.termmsg = termmsg;
    }

    public static Creator<Notification_global_class> getCREATOR() {
        return CREATOR;
    }

    protected Notification_global_class(Parcel in) {
        classId = in.readString();
        isActive = in.readString();
        subjectId = in.readString();
        subject_name = in.readString();
        stud_id = in.readString();
        stud_firstName = in.readString();
        stud_lastName = in.readString();
        stud_middleName = in.readString();
        profile_Picture = in.readString();
        profile_url = in.readString();
        notifi_id = in.readString();
        byte tmpNotifi_isConfirmed = in.readByte();
        notifi_isConfirmed = tmpNotifi_isConfirmed == 0 ? null : tmpNotifi_isConfirmed == 1;
        byte tmpNotifi_isRejected = in.readByte();
        notifi_isRejected = tmpNotifi_isRejected == 0 ? null : tmpNotifi_isRejected == 1;
        byte tmpNotifi_isAccepted = in.readByte();
        notifi_isAccepted = tmpNotifi_isAccepted == 0 ? null : tmpNotifi_isAccepted == 1;
        byte tmpNotifi_isPending = in.readByte();
        notifi_isPending = tmpNotifi_isPending == 0 ? null : tmpNotifi_isPending == 1;
        sched_start = in.readString();
        sched_end = in.readString();
        notifi_status = in.readString();
        termmsg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(classId);
        dest.writeString(isActive);
        dest.writeString(subjectId);
        dest.writeString(subject_name);
        dest.writeString(stud_id);
        dest.writeString(stud_firstName);
        dest.writeString(stud_lastName);
        dest.writeString(stud_middleName);
        dest.writeString(profile_Picture);
        dest.writeString(profile_url);
        dest.writeString(notifi_id);
        dest.writeByte((byte) (notifi_isConfirmed == null ? 0 : notifi_isConfirmed ? 1 : 2));
        dest.writeByte((byte) (notifi_isRejected == null ? 0 : notifi_isRejected ? 1 : 2));
        dest.writeByte((byte) (notifi_isAccepted == null ? 0 : notifi_isAccepted ? 1 : 2));
        dest.writeByte((byte) (notifi_isPending == null ? 0 : notifi_isPending ? 1 : 2));
        dest.writeString(sched_start);
        dest.writeString(sched_end);
        dest.writeString(notifi_status);
        dest.writeString(termmsg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notification_global_class> CREATOR = new Creator<Notification_global_class>() {
        @Override
        public Notification_global_class createFromParcel(Parcel in) {
            return new Notification_global_class(in);
        }

        @Override
        public Notification_global_class[] newArray(int size) {
            return new Notification_global_class[size];
        }
    };
}
