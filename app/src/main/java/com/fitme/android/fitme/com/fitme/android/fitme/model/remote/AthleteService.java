package com.fitme.android.fitme.com.fitme.android.fitme.model.remote;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.fitme.android.fitme.Login.LoginPresenter;
import com.fitme.android.fitme.MemberApp.classInfoFragment.ClassFragmentPresenter;
import com.fitme.android.fitme.MemberApp.listFragment.ListFragmentPresenter;
import com.fitme.android.fitme.MemberApp.memberAppActivity.MemberAppPresenter;
import com.fitme.android.fitme.MemberApp.profileFragment.ProfileFragmentPresenter;
import com.fitme.android.fitme.MemberApp.scheduleFragment.SchedulePresenter;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.utils.Scheduler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

/**
 * Created by marciarocha on 18/12/17.
 */

public class AthleteService {

    DatabaseReference mDatabaseReference;
    String athleteID;
    Athlete currentAthlete;
    boolean registerSuccess = true;

    LoginPresenter mLoginPresenter;
    ProfileFragmentPresenter profileFragmentPresenter;
    MemberAppPresenter memberAppPresenter;
    SchedulePresenter mSchedulePresenter;
    ClassFragmentPresenter classFragmentPresenter;

    private final Scheduler scheduler = new Scheduler();


    public AthleteService() {
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference("athletes");
    }

    public void setClassFragmentPresenter(ClassFragmentPresenter classPresenter) {
      classFragmentPresenter = classPresenter;
    }

  public void setSchedulePresenter(SchedulePresenter schedulePresenter) {
    mSchedulePresenter = schedulePresenter;
  }

    public void setLoginPresenter(LoginPresenter loginPresenter){
        mLoginPresenter = loginPresenter;
    }

    public void setProfileFragmentPresenter(ProfileFragmentPresenter profilePresenter){
        profileFragmentPresenter = profilePresenter;
    }

    public void setMemberAppPresenter (MemberAppPresenter memberPresenter) {
        memberAppPresenter = memberPresenter;
    }

    public void addUser(final Athlete athlete) {

        final String email = athlete.getEmail();
        Log.i("REGISTER ->" , email.toString());

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    Log.i("Firebase" , "user already exists in the database.");
                    registerSuccess = false;


               } else {

                    Log.i("Firebase" , "adding " + athlete.toString() + "to the database.");
                    registerSuccess = true;

                    if (TextUtils.isEmpty(athleteID)) {
                        athleteID = mDatabaseReference.push().getKey();
                    }

                    mDatabaseReference.child(athleteID).setValue(athlete);
                    registerSuccess = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void login(String email, final String password) {

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);

        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(Athlete.class) != null) {
                    Log.i("Athlete Login", "email matches email in the database");
                    Log.i("class -> ",  dataSnapshot.getValue().getClass().toString());

                    for (DataSnapshot atSnapshot : dataSnapshot.getChildren() ) {
                        Athlete at = atSnapshot.getValue(Athlete.class);

                        Log.i("currentAthlete -> ", at.toString() );

                        if (at.getPassword().equals(password)) {

                            currentAthlete = at;

                            Log.i("Athlete Login", "password matches email");
                            mLoginPresenter.onLoginResult(true, currentAthlete);
                        }
                        else {
                            mLoginPresenter.onLoginResult(false, 0);
                        }
                    }


                } else {
                    mLoginPresenter.onLoginResult(false, 0);
                    Log.i("Athlete Login", "E-mail doesn't exist in the database");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR ->", databaseError.getMessage());
            }
        });


    }

    public boolean checkLogin() {

        if(currentAthlete == null) {
            return false;
        } else {
            return true;
        }
    }

    public void logout() {

        currentAthlete = null;

    }

    public boolean checkRegisterOperationResult() {
        return registerSuccess;
    }

    public void setImage(final String image, String email) {
        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {
                    tDataSnapshot.getRef().child("image").setValue(image);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updatePhone(final String phone, final String email) {
        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

                    tDataSnapshot.getRef().child("phoneNumber").setValue(phone);
                    profileFragmentPresenter.onPhoneUpdated(phone);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updatePassword(final String password, final String email) {

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

                    tDataSnapshot.getRef().child("password").setValue(password);
                    profileFragmentPresenter.onPasswordUpdated(password);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateListClass(final String email, final GymClass newGymClass) {
      Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
      findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

            Athlete at = tDataSnapshot.getValue(Athlete.class);
            List<GymClass> gymClasses = at.getSchedule();
            gymClasses.add(newGymClass);
            tDataSnapshot.getRef().child("schedule").setValue(gymClasses);
            Log.i("oiii", "service");
            classFragmentPresenter.updateAthlete();
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }
    public void removeClassfromList(final String email, final GymClass newGymClass) {
      Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
      findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

            Athlete at = tDataSnapshot.getValue(Athlete.class);

            List<GymClass> gymClasses = at.getSchedule();
            for(int i=0; i<gymClasses.size(); i++){
              if(gymClasses.get(i).getIdClass().equals(newGymClass.getIdClass())){
                gymClasses.remove(i);
                break;
              }
            }
            tDataSnapshot.getRef().child("schedule").setValue(gymClasses);
            classFragmentPresenter.updateAthlete();
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }

    public void getUser(String email, final String typeFragment, final String nameActivity) {

      Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
      findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(final DataSnapshot dataSnapshot) {
          for(DataSnapshot data : dataSnapshot.getChildren()) {
            currentAthlete = data.getValue(Athlete.class);
          }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }

    public void setMemberPresenter(MemberAppPresenter memberPresenter) {
      memberAppPresenter = memberPresenter;
    }

}
