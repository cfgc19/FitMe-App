package com.fitme.android.fitme.MemberApp.athletesListFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import java.util.ArrayList;

public class ListAthletesFragment extends Fragment implements AthletesListContract.AthletesListView{

  @BindView(R.id.listAthletes) ListView listAthletesView;
  @BindView(R.id.maxNumberAthletes) TextView maxNumberView;
  @BindView(R.id.numberAthletes) TextView numberRealView;

  private static final String ARG_OBJECT = "user";

  private GymClass gymClass;
  AthletesListPresenter mPresenter;

  public ListAthletesFragment() {
    // Required empty public constructor
  }

  public static ListAthletesFragment newInstance(Parcelable object) {
    ListAthletesFragment fragment = new ListAthletesFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_OBJECT,object);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      gymClass = getArguments().getParcelable(ARG_OBJECT);
      Log.i("gymClass(AthletesFrag) " , gymClass.toString());
    }

    mPresenter = new AthletesListPresenter();
    mPresenter.attachView(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list_athletes, container, false);
    ButterKnife.bind(this, view);

    ArrayList<String> athletesList = new ArrayList<>();

    for(Athlete athlete: gymClass.getAthletes()){
      athletesList.add(athlete.getName());
    }

    numberRealView.setText(String.valueOf(athletesList.size()));
    maxNumberView.setText(String.valueOf(gymClass.getMaxAthletes()));

    ArrayAdapter<String> itemsAdapter =
        new ArrayAdapter<String>(getContext(), R.layout.mytextview, athletesList);

    listAthletesView.setAdapter(itemsAdapter);


    return view;
  }
}
