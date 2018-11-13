package com.fitme.android.fitme.com.fitme.android.fitme.model.remote;

import android.text.TextUtils;
import android.util.Log;

import com.fitme.android.fitme.Login.LoginPresenter;
import com.fitme.android.fitme.MemberApp.memberAppActivity.MemberAppPresenter;
import com.fitme.android.fitme.MemberApp.listFragment.ListFragmentPresenter;
import com.fitme.android.fitme.MemberApp.profileFragment.ProfileFragmentPresenter;
import com.fitme.android.fitme.MemberApp.scheduleFragment.ScheduleFragment;
import com.fitme.android.fitme.MemberApp.scheduleFragment.SchedulePresenter;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

/**
 * Created by marciarocha on 22/12/17.
 */

public class TrainerService {

    DatabaseReference mDatabaseReference;
    String trainerID;
    Trainer currentTrainer;
    LoginPresenter mLoginPresenter;
    ProfileFragmentPresenter mProfileFragmentPresenter;
    MemberAppPresenter mMemberPresenter;
    ListFragmentPresenter mListFragmentPresenter;
    SchedulePresenter mSchedulePresenter;

    boolean success = true;

    public TrainerService() {
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference("trainers");
    }

    public void setSchedulePresenter(SchedulePresenter schedulePresenter) {
      mSchedulePresenter = schedulePresenter;
    }

    public void setListFragmentPresenter(ListFragmentPresenter listPresenter) {
    mListFragmentPresenter = listPresenter;
  }

    public void addTrainer(final Trainer trainer) {

        String email = trainer.getEmail();

        Log.i("REGISTER TRAINER ->" , email.toString());

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    success = false;
                    Log.i("Firebase" , "user already exists in the database.");

                } else {
                    success = true;

                    Log.i("Firebase" , "adding " + trainer.toString() + "to the database.");

                    if (TextUtils.isEmpty(trainerID)) {
                        trainerID = mDatabaseReference.push().getKey();
                    }

                    mDatabaseReference.child(trainerID).setValue(trainer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DatabaseError" , databaseError.getMessage().toString());
                success = false;
            }
        });
    }

    public void login(String email, final String password) {

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);

        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(Trainer.class) != null) {

                    for (DataSnapshot tSnapshot : dataSnapshot.getChildren()) {
                        Trainer tr = tSnapshot.getValue(Trainer.class);

                        Log.i("Trainer Login", "e-mail matches email in the database");

                        if (tr.getPassword().equals(password)) {
                            currentTrainer = tr;

                            mLoginPresenter.onLoginResult(true, currentTrainer);
                            Log.i("Trainer Login", "password matches email");
                            Log.i("Trainer", tr.toString());
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

        if (currentTrainer == null) {
            return false;

        } else {
            return true;
        }
    }

    public Trainer getTrainer() {
        return currentTrainer;
    }

    public void logout() {
        currentTrainer = null;
    }

    public boolean checkRegisterOperationResult() {
        return success;

    }

    public void setLoginPresenter(LoginPresenter loginPresenter) {
        mLoginPresenter = loginPresenter;
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

                    tDataSnapshot.getRef().child("phone").setValue(phone);
                    mProfileFragmentPresenter.onPhoneUpdated(phone);

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
                    mProfileFragmentPresenter.onPasswordUpdated(password);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProfilePresenter(ProfileFragmentPresenter profilePresenter) {
        mProfileFragmentPresenter = profilePresenter;
    }

    public void setMemberPresenter(MemberAppPresenter memberPresenter) {
      mMemberPresenter = memberPresenter;
    }
    //addClasstoTrainer

    //check if trainer doesnt have simultaneous classes

    public void getTrainer(String email, final String typeFragment, final String nameActivity) {

        Query findQuery = mDatabaseReference.orderByChild("email").equalTo(email);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot atSnapshot : dataSnapshot.getChildren() ) {

                    Trainer trainer = atSnapshot.getValue(Trainer.class);

                    if(nameActivity.equals("memberActivity")){
                      //mMemberPresenter.onGetUser(trainer, typeFragment);
                    }
                    else{
                      //mSchedulePresenter.onGetUser(trainer);
                    }
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

                    Trainer at = tDataSnapshot.getValue(Trainer.class);
                    List<GymClass> gymClasses = at.getGymClasses();
                    gymClasses.add(newGymClass);
                    tDataSnapshot.getRef().child("gymClasses").setValue(gymClasses);

                    //ClassFragmentPresenter.onListClassesdUpdated(gymClasses);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
