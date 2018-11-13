package com.fitme.android.fitme.MemberApp.newClassFragment;


import android.util.Log;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

import java.util.List;

public class NewClassFragmentPresenter implements NewClassFragmentContract.NewClassFragmentPresenter {

    NewClassFragmentContract.NewClassFragmentView mView;
    GymClassService mGymClassService;
    TrainerService mTrainerService;

    public NewClassFragmentPresenter() {
        mGymClassService = new GymClassService();
        mTrainerService = new TrainerService();
    }

    @Override
    public void attachView(NewClassFragmentContract.NewClassFragmentView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public boolean registerClass(String modality, Trainer user, String date, String beginDate,
        String endDate, int spots, int intensity, int relaxation, int weightLoss, String description) {

        // e preciso depois fazer as validacoes
        GymClass gymClass = new GymClass(modality,user,date, beginDate, endDate, spots, intensity,
            relaxation, weightLoss, description, null);
        GymClass trainerClass = new GymClass(date,beginDate,endDate);

        List<GymClass> allTrainerClasses = user.getGymClasses();
        boolean add = true;
        for (GymClass e : allTrainerClasses){
            if (e.getDate().equals(trainerClass.getDate())){
                int startHoursOld = Integer.parseInt(e.getStartTime().split(":")[0]);
                int startMinutesOld = Integer.parseInt(e.getStartTime().split(":")[1]);
                int startHoursNew = Integer.parseInt(trainerClass.getStartTime().split(":")[0]);
                int startMinutesNew = Integer.parseInt(trainerClass.getStartTime().split(":")[1]);
                int finishHoursOld = Integer.parseInt(e.getFinishTime().split(":")[0]);
                int finishMinutesOld = Integer.parseInt(e.getFinishTime().split(":")[1]);
                int finishHoursNew = Integer.parseInt(trainerClass.getFinishTime().split(":")[0]);
                int finishMinutesNew = Integer.parseInt(trainerClass.getFinishTime().split(":")[1]);

                int startOld = startHoursOld*1000+startMinutesOld;
                int finishOld = finishHoursOld*1000+finishMinutesOld;
                int startNew = startHoursNew*1000+startMinutesNew;
                int finishNew = finishHoursNew*1000+finishMinutesNew;

                if (startNew<startOld){
                    if (finishNew >= finishOld) {
                        add=false;
                        break;
                    }
                }
                else if (startNew>startOld){
                    if (startNew<finishOld){
                        add=false;
                        break;
                    }
                }
            }
        }

        int startHoursNew = Integer.parseInt(trainerClass.getStartTime().split(":")[0]);
        int startMinutesNew = Integer.parseInt(trainerClass.getStartTime().split(":")[1]);
        int finishHoursNew = Integer.parseInt(trainerClass.getFinishTime().split(":")[0]);
        int finishMinutesNew = Integer.parseInt(trainerClass.getFinishTime().split(":")[1]);
        int startNew = startHoursNew*1000+startMinutesNew;
        int finishNew = finishHoursNew*1000+finishMinutesNew;

        if(finishNew<startNew)
        {
            add=false;
        }

        Log.d("BOOLEAN", String.valueOf(add));

        if (add) {
            mTrainerService.updateListClass(user.getEmail(), gymClass);
            mGymClassService.addGymClass(gymClass);
        }
        return add;
    }
}
