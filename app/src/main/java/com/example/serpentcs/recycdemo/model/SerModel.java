package com.example.serpentcs.recycdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by serpentcs on 29/11/16.
 */

public class SerModel implements Parcelable {

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public SerModel createFromParcel(Parcel in) {
            return new SerModel(in);
        }

        public SerModel[] newArray(int size) {
            return new SerModel[size];
        }
    };
    String Name;
    String id;

    public SerModel(String name, String id) {
        Name = name;
        this.id = id;
    }

    public SerModel(Parcel parcel) {
        Name = parcel.readString();
        id = parcel.readString();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(id);


    }
}
