package com.fitme.android.fitme.MemberApp.classInfoFragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClassFragment extends Fragment implements AdapterView.OnClickListener ,
    ClassFragmentContract.ClassFragmentView{

  @BindView(R.id.class_btn_subscribe) Button button_subscribe;
  @BindView(R.id.class_name) TextView classNameView;
  @BindView(R.id.class_availability) TextView availabilityView;
  @BindView(R.id.class_name_monitor) TextView monitorView;
  @BindView(R.id.class_duration) TextView durationView;
  @BindView(R.id.class_intensity) ProgressBar intensityView;
  @BindView(R.id.class_relaxation) ProgressBar relaxationView;
  @BindView(R.id.class_weight_loss) ProgressBar weightLossView;
  @BindView(R.id.class_description) TextView descriptionView;
  @BindView(R.id.class_image) ImageView imageView;

  private static final String ARG_OBJECT_1 = "gymClass";
  private static final String ARG_OBJECT_2 = "athlete";

  private GymClass gymClass;
  private Athlete athlete;
  private GymClass gymClassToRemove;
  ClassFragmentPresenter mPresenter;

  private OnFragmentInteractionListener mListener;

  public ClassFragment() {
    // Required empty public constructor
  }

  public static ClassFragment newInstance(Parcelable object1, Parcelable object2) {
    ClassFragment fragment = new ClassFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_OBJECT_1,object1);
    args.putParcelable(ARG_OBJECT_2,object2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      gymClass = getArguments().getParcelable(ARG_OBJECT_1);
      athlete = getArguments().getParcelable(ARG_OBJECT_2);
      Log.i("Got user -> " , athlete.toString());
      Log.i("Got gymClass -> " , gymClass.toString());
    }

    mPresenter = new ClassFragmentPresenter();
    mPresenter.attachView(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_class, container, false);
    ButterKnife.bind(this, view);

    showInfoClass();
    return view;
  }

  public void showInfoClass(){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
    try {
      Date beginClass = simpleDateFormat.parse(gymClass.getStartTime());
      Date endClass = simpleDateFormat.parse(gymClass.getFinishTime());

      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();

      c1.setTime(beginClass);
      c2.setTime(endClass);

      long ms1 = c1.getTimeInMillis();
      long ms2 = c2.getTimeInMillis();

      long diff = ms2 - ms1;  //get difference in milli seconds
      int duration = (int) (diff/ (1000*60)); // pass milli seconds to minutes
      durationView.setText(Integer.toString(duration) + " min");

    }
    catch (Exception e){
      Log.i("exception", e.toString());
    }
    descriptionView.setText(gymClass.getDescription());
    intensityView.setProgress(gymClass.getIntensity());
    relaxationView.setProgress(gymClass.getRelaxation());
    weightLossView.setProgress(gymClass.getWeightLoss());
    int maxAthlets = gymClass.getMaxAthletes();
    List<Athlete> athleteList = gymClass.getAthletes();

    if(maxAthlets> athleteList.size()){
      availabilityView.setText("Available");
    }
    else{
      availabilityView.setText("Unavailable");
      availabilityView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
      button_subscribe.setEnabled(false);
      button_subscribe.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkTransparent));
    }
    classNameView.setText(gymClass.getType());
    monitorView.setText(gymClass.getTrainer().getName());

    for(GymClass gymClass1 : athlete.getSchedule()){
      if(gymClass1.getIdClass().equals(gymClass.getIdClass())){
        gymClassToRemove = gymClass1;
        button_subscribe.setEnabled(true);
        button_subscribe.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryDark));
        button_subscribe.setText("Unsubscribe");
        break;
      }
    }
    String[] modalities = getContext().getResources().getStringArray(R.array.modalities);
    TypedArray imagesModalities = getContext().getResources().obtainTypedArray(R.array.modalities_images);

    for(int i=0; i<modalities.length; i++){
      if (modalities[i].equals(gymClass.getType())){
        imageView.setImageResource(imagesModalities.getResourceId(i,-1));
        break;
      }
    }
    imagesModalities.recycle();

  }

  @OnClick(R.id.class_btn_subscribe)
  public void subscribe(){
    if(button_subscribe.getText().equals("Subscribe")){
      mPresenter.subscribeClass(gymClass, athlete);
      List<GymClass> schedule = athlete.getSchedule();
      schedule.add(gymClass);
      athlete.setSchedule(schedule);
      mListener.onClassFragmentInteraction(athlete);
    }
    else{ // Unsubscribe
      mPresenter.unsubscribeClass(gymClass,athlete);
      List<GymClass> schedule = athlete.getSchedule();
      schedule.remove(gymClassToRemove);
      athlete.setSchedule(schedule);

      mListener.onClassFragmentInteraction(athlete);
    }
  }


  @Override public void onClick(View v) {

  }

  public void onButtonPressed(Parcelable object) {
    if (mListener != null) {
      mListener.onClassFragmentInteraction(object);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.dettachView();
  }

  public void killFragment() {
    getActivity().getSupportFragmentManager().popBackStackImmediate();
  }

  @Override public void checkedupdateAthlete() {
    killFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;

    } else {
      throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnFragmentInteractionListener {
    void onClassFragmentInteraction(Parcelable object);
  }
}