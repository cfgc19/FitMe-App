
package com.fitme.android.fitme.MemberApp.listFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.ProgressBar;
import com.fitme.android.fitme.MemberApp.athletesListFragment.ListAthletesFragment;
import com.fitme.android.fitme.MemberApp.classInfoFragment.ClassFragment;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, ListContract.ListFragmentView{

    private static final String VIEWPAGER_DAY = "VIEWPAGER_DAY";
    private static final String ARG_OBJECT = "user";
    private String dayOfWeek;
    private Object user;
    ListFragmentPresenter mPresenter;
    private static int initial = 1;

    @BindView(R.id.list) ListView listView;
    @BindView(R.id.determinateBar) ProgressBar progressBar;

    public ListFragment() {

    }

    public static ListFragment newInstance(String daySelected, Parcelable object) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(VIEWPAGER_DAY, daySelected);
        args.putParcelable(ARG_OBJECT, object);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayOfWeek = getArguments().getString(VIEWPAGER_DAY);
            user = getArguments().getParcelable(ARG_OBJECT);
        }
        mPresenter = new ListFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_list, container, false);
        ButterKnife.bind(this, view);

        mPresenter.onWeekDayClick(dayOfWeek);
        return view;
    }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("cliquei",Integer.toString(position));
    }

    @Override
    public void onScheduleFound(ArrayList<GymClass> gymClasses) {
        ClassGymAdapter adapter = new ClassGymAdapter(getActivity(), gymClasses, user);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

              GymClass gymClass = (GymClass) listView.getItemAtPosition(position);
              if(user instanceof Athlete){
                ClassFragment classFragment = ClassFragment.newInstance((Parcelable) gymClass, (Parcelable) user);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_member, classFragment).addToBackStack("DrawerMenu").commit();
              }
              else{
                ListAthletesFragment listAthletesFragment = ListAthletesFragment.newInstance((Parcelable) gymClass);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_member, listAthletesFragment).addToBackStack("DrawerMenu").commit();
              }
            }
        });

    }
}