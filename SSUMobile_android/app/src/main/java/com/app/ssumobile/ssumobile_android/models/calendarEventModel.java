package com.app.ssumobile.ssumobile_android.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by ben on 10/26/15.
 */
public class calendarEventModel implements Parcelable{


    public String getStartsOn() {
        return start_date;
    }

    public void setStartsOn(String startsOn) {
        start_date = startsOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public String getTitle() { return title; }

    public void setTitle(String Title) {
        title = Title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String Created) {
        created = Created;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String Location) {
        location = Location;
    }

    public String getId() {
        return id;
    }

    public void setId(String ID) {
        id = ID;
    }

    public String getEndsOn() {
        return end_date;
    }

    public void setEndsOn(String endsOn) {
        end_date = endsOn;
    }

    private String start_date;
    private String description;
    private String title;
    private String created;
    private String location;
    private String id;
    private String end_date;

    public calendarEventModel(Parcel in) {
        readFromParcel(in);
    }
    public calendarEventModel() {  }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStartsOn());
        dest.writeString(getDescription());
        dest.writeString(getTitle());
        dest.writeString(getLocation());
        dest.writeString(getId());
        dest.writeString(getEndsOn());
    }

    private void readFromParcel(Parcel in) {
        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        start_date = in.readString();
        description = in.readString();
        title = in.readString();
        created = in.readString();
        location = in.readString();
        id = in.readString();
        end_date = in.readString();
    }

    public static final Parcelable.Creator<calendarEventModel> CREATOR = new Parcelable.Creator<calendarEventModel>() {

        public calendarEventModel createFromParcel(Parcel in) {
            return new calendarEventModel();
        }

        public calendarEventModel[] newArray(int size) {
            return new calendarEventModel[size];
        }
    };

    public static Comparator<calendarEventModel> COMPARE_BY_START = new Comparator<calendarEventModel>() {
        public int compare(calendarEventModel one, calendarEventModel other) {
            return one.start_date.compareTo(other.start_date);
        }
    };

}
