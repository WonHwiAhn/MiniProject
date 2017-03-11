package com.example.ahn.StudyBoard;

/**
 * Created by Ahn on 2017-02-25.
 */

public class BoardMainData {
    String studyTitle, studyMember, studyStartDate, studyEndDate, studyStartTime, studyEndTime, mobileNumber, studyAddress, studyDetail;
    double longitude, latitude;
    BoardMainData(){}

    BoardMainData(String studyTitle, String studyMember, String studyStartDate, String studyEndDate, String studyStartTime, String studyEndTime, String mobileNumber, String studyAddress, String studyDetail, double longitude, double latitude){
        this.studyTitle = studyTitle;
        this.studyMember = studyMember;
        this.studyStartDate = studyStartDate;
        this.studyEndDate = studyEndDate;
        this.studyStartTime = studyStartTime;
        this.studyEndTime = studyEndTime;
        this.mobileNumber = mobileNumber;
        this.studyAddress = studyAddress;
        this.studyDetail = studyDetail;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public String getStudyMember() {
        return studyMember;
    }

    public void setStudyMember(String studyMember) {
        this.studyMember = studyMember;
    }

    public String getStudyStartDate() {
        return studyStartDate;
    }

    public void setStudyStartDate(String studyStartDate) {
        this.studyStartDate = studyStartDate;
    }

    public String getStudyEndDate() {
        return studyEndDate;
    }

    public void setStudyEndDate(String studyEndDate) {
        this.studyEndDate = studyEndDate;
    }

    public String getStudyStartTime() {
        return studyStartTime;
    }

    public void setStudyStartTime(String studyStartTime) {
        this.studyStartTime = studyStartTime;
    }

    public String getStudyEndTime() {
        return studyEndTime;
    }

    public void setStudyEndTime(String studyEndTime) {
        this.studyEndTime = studyEndTime;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStudyAddress() {
        return studyAddress;
    }

    public void setStudyAddress(String studyAddress) {
        this.studyAddress = studyAddress;
    }

    public String getStudyDetail() {
        return studyDetail;
    }

    public void setStudyDetail(String studyDetail) {
        this.studyDetail = studyDetail;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
