package com.rprata.comicapp.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rprata on 07/01/17.
 */

public class Comic implements Parcelable {

    public static String MESSAGE_COMIC = "COMIC";

    private String title;
    private String picture_url;
    private String short_description;
    private double price;
    private String date_of_publication;
    private int edition;

    public Comic() {

    }

    public Comic(Parcel in) {
        title = in.readString();
        picture_url = in.readString();
        short_description = in.readString();
        price = in.readDouble();
        date_of_publication = in.readString();
        edition = in.readInt();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate_of_publication() {
        return date_of_publication;
    }

    public void setDate_of_publication(String date_of_publication) {
        this.date_of_publication = date_of_publication;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(picture_url);
        parcel.writeString(short_description);
        parcel.writeDouble(price);
        parcel.writeString(date_of_publication);
        parcel.writeInt(edition);
    }
}
