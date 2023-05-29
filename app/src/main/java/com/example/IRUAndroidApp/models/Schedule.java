package com.example.IRUAndroidApp.models;

import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("ردیف")
    private String row;
    @SerializedName("نوع اعلام")
    private String type;
    @SerializedName("جلسه جبرانی/کنسلی/فوق العاده")
    private String date;
    @SerializedName("شماره کلاس جبرانی")
    private String classNumber;
    @SerializedName("ساعت")
    private String time;
    @SerializedName("نام استاد")
    private String professorName;
    @SerializedName("کد درس")
    private String courseCode;
    @SerializedName("نام درس")
    private String courseName;
    @SerializedName("مکان برگزاری")
    private String location;
    @SerializedName("تاریخ اعلام")
    private String announcementDate;
    @SerializedName("توضیحات")
    private String notes;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
