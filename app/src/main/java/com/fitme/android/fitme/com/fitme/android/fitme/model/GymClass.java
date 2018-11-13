package com.fitme.android.fitme.com.fitme.android.fitme.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by marciarocha on 18/12/17.
 */

public class GymClass implements Parcelable {
    private String idClass;
    private Trainer trainer;
    private String type;
    private String date;
    private String startTime;
    private String finishTime ;
    private int intensity;
    private int relaxation;
    private int weightLoss;
    private String description;
    private List<Athlete> athletes = new ArrayList<Athlete>();
    private int maxAthletes;

    public GymClass() {
    }

    public GymClass(String type, Trainer trainer, String date, String startTime, String finishTime,
        int maxAthletes, int intensity, int relaxation, int weightLoss, String description, List<Athlete> athletes) {
        this.idClass = trainer.getName()+ date + startTime;
        this.type = type;
        this.trainer = trainer;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.maxAthletes = maxAthletes;
        this.relaxation = relaxation;
        this.intensity = intensity;
        this.weightLoss = weightLoss;
        this.description = description;
        this.athletes = athletes;
    }

    public GymClass(String date, String startTime, String finishTime) {
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

  public String getIdClass() {
    return idClass;
  }

  public void setIdClass(String idClass) {
    this.idClass = idClass;
  }

  public int getIntensity() {return intensity;}

    public int getWeightLoss() {return weightLoss;}

    public int getRelaxation() {return relaxation;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public void setIntensity(int intensity) {this.intensity = intensity;}

    public void setRelaxation(int relaxation) {this.relaxation = relaxation;}

    public void setWeightLoss(int weightLoss) {this.weightLoss = weightLoss;}

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public int getMaxAthletes() {
        return maxAthletes;
    }

    public void setMaxAthletes(int maxAthletes) {
        this.maxAthletes = maxAthletes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "GymClass{" +
            " id=" + idClass +
            ", trainer=" + trainer +
            ", type='" + type + '\'' +
            ", date='" + date + '\'' +
            ", startTime='" + startTime + '\'' +
            ", finishTime='" + finishTime + '\'' +
            ", athletes=" + athletes +
            ", max athletes=" + maxAthletes+
            ", intensity=" + intensity +
            ", relaxation=" + relaxation +
            ", weight loss=" + weightLoss+
            ", description=" + description+
            '}';
    }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(idClass);
    dest.writeString(date);
    dest.writeString(startTime);
    dest.writeString(finishTime);
    dest.writeString(description);
    dest.writeString(type);
    dest.writeInt(intensity);
    dest.writeInt(relaxation);
    dest.writeInt(weightLoss);
    dest.writeInt(maxAthletes);
    dest.writeTypedObject(trainer, 1);
    dest.writeTypedList(athletes);
  }

  @Override public int describeContents() {
    return 0;
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<GymClass> CREATOR = new Parcelable.Creator<GymClass>() {
    @Override
    public GymClass createFromParcel(Parcel in) {
      return new GymClass(in);
    }

    @Override
    public GymClass[] newArray(int size) {
      return new GymClass[size];
    }
  };




  protected GymClass(Parcel in) {
    idClass = in.readString();
    date = in.readString();
    startTime = in.readString();
    finishTime = in.readString();
    description = in.readString();
    type = in.readString();
    intensity = in.readInt();
    weightLoss = in.readInt();
    relaxation = in.readInt();
    maxAthletes = in.readInt();
    trainer = in.readTypedObject(Trainer.CREATOR);
    in.readTypedList(athletes,Athlete.CREATOR);
  }
}
