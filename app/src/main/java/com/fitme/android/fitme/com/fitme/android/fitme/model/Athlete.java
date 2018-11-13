package com.fitme.android.fitme.com.fitme.android.fitme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marciarocha on 18/12/17.
 */

public class Athlete implements Parcelable {

    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String birthDate;
    String image;

    private List<GymClass> schedule = new ArrayList<GymClass>();


    public Athlete() {
    }

    public Athlete(String email, String name, String phoneNumber, String password, String birthDate,
        List<GymClass> schedule) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDate = birthDate;
        this.schedule = schedule;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GymClass> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<GymClass> schedule) {
        this.schedule = schedule;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password=" + (password) +
                ", schedule=" + schedule +
                ", birth Date=" + birthDate +
                '}';
    }

    protected Athlete(Parcel in) {
        email = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        birthDate = in.readString();
        in.readTypedList(schedule,GymClass.CREATOR);
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(birthDate);
        dest.writeTypedList(schedule);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Athlete> CREATOR = new Parcelable.Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };
}