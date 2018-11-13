package com.fitme.android.fitme.com.fitme.android.fitme.model.remote;

import android.text.TextUtils;
import android.util.Log;

import com.fitme.android.fitme.MemberApp.listFragment.ListFragmentPresenter;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by marciarocha on 22/12/17.
 */

public class GymClassService {
    DatabaseReference mDatabaseReference;
    String trainerID;
    String gymClassID;
    ArrayList<GymClass> gymClasses = new ArrayList<>();
    ListFragmentPresenter mListFragmentPresenter;
    private final Scheduler scheduler = new Scheduler();

    public GymClassService() {
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference("Gym_Classes");
    }

    public void setListFragmentPresenter(ListFragmentPresenter listPresenter) {
        mListFragmentPresenter = listPresenter;
    }

    public void addGymClass(GymClass gymClass) {

        if (TextUtils.isEmpty(gymClassID)) {
            gymClassID = mDatabaseReference.push().getKey();
        }
        mDatabaseReference.child(gymClassID).setValue(gymClass);
    }

    public void updateListAthlets(final Athlete athlete, final GymClass gymClass) {
        Query findQuery = mDatabaseReference.orderByChild("idClass").equalTo(gymClass.getIdClass());
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

                    GymClass at = tDataSnapshot.getValue(GymClass.class);

                    List<Athlete> athletes = at.getAthletes();
                    athletes.add(athlete);
                    tDataSnapshot.getRef().child("athletes").setValue(athletes);

                    //ClassFragmentPresenter.onListClassesdUpdated(gymClasses);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeAthletfromList(final Athlete athlete, final GymClass gymClass) {
      Query findQuery = mDatabaseReference.orderByChild("idClass").equalTo(gymClass.getIdClass());
      findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot tDataSnapshot : dataSnapshot.getChildren()) {

            GymClass at = tDataSnapshot.getValue(GymClass.class);

            List<Athlete> athletes = at.getAthletes();
            for(int i=0; i<athletes.size(); i++){
              if(athletes.get(i).getEmail().equals(athlete.getEmail())){
                athletes.remove(i);
                break;
              }
            }
            tDataSnapshot.getRef().child("athletes").setValue(athletes);

            //ClassFragmentPresenter.onListClassesdUpdated(gymClasses);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }

    public void getClasses(String dateToFind) {
        Log.i("DateToSearch", dateToFind);
        gymClasses.clear();

        Query findQuery = mDatabaseReference.orderByChild("date").equalTo(dateToFind);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
              scheduler.runOnBackground(new Runnable() {
                @Override public void run() {
                  for(DataSnapshot data : dataSnapshot.getChildren()) {
                    gymClasses.add(data.getValue(GymClass.class));
                  }
                  scheduler.runOnUiThread(new Runnable() {
                    @Override public void run() {
                      mListFragmentPresenter.onSearchComplete(gymClasses);
                    }
                  });
                }
              });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
