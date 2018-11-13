package com.fitme.android.fitme.com.fitme.android.fitme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marciarocha on 18/12/17.
 */
public class Trainer implements Parcelable {

    private String credential;
    private String name;
    private String email;
    private String password;
    private List<GymClass> gymClasses = new ArrayList<GymClass>();
    private String phone;
    private String image;
    private String birthDate;

    public Trainer() {
    }

    public Trainer(String credential, String name, String email, String password, String birthDate, List<GymClass> gymClasses) {
        this.credential = credential;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gymClasses = gymClasses;
        this.birthDate = birthDate;
        this.gymClasses = gymClasses;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCredential() {
        return credential;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public List<GymClass> getGymClasses() {
        return gymClasses;
    }

    public void setGymClasses(List<GymClass> gymClasses) {
        this.gymClasses = gymClasses;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "credential='" + credential + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + (password) +
                ", birth Date=" + (birthDate) +
                ", gymClasses=" + gymClasses +
                '}';
    }

    protected Trainer(Parcel in) {
        credential = in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        in.readTypedList(gymClasses,GymClass.CREATOR);
        phone = in.readString();
        image = in.readString();
        birthDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(credential);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeTypedList(gymClasses);
        dest.writeString(phone);
        dest.writeString(image);
        dest.writeString(birthDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trainer> CREATOR = new Parcelable.Creator<Trainer>() {
        @Override
        public Trainer createFromParcel(Parcel in) {
            return new Trainer(in);
        }

        @Override
        public Trainer[] newArray(int size) {
            return new Trainer[size];
        }
    };
}