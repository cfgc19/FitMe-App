package com.fitme.android.fitme.MemberApp.scheduleFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import com.fitme.android.fitme.MemberApp.profileFragment.PerfilFragment;
import com.fitme.android.fitme.MemberApp.profileFragment.ProfileFragmentPresenter;
import com.fitme.android.fitme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;

public class ScheduleFragment extends Fragment implements ScheduleContract.ScheduleViewInterface{

  private static final String ARG_OBJECT = "user";

  private Object user;

  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.sliding_tabs) TabLayout tabLayout;
  @BindView(R.id.progressBarView) ProgressBar progressBarView;

  SchedulePresenter mPresenter;
  private OnFragmentInteractionListener mListener;

  public ScheduleFragment() {
  }

  public static ScheduleFragment newInstance(Parcelable object) {
    ScheduleFragment fragment = new ScheduleFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_OBJECT,object);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      user = getArguments().getParcelable(ARG_OBJECT);
      Log.i("Got user -> " , user.toString());
    }

    mPresenter = new SchedulePresenter();
    mPresenter.attachView(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_schedule, container, false);
    ButterKnife.bind(this,view);

    viewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager(),
        getActivity(), user));
    mPresenter.currentWeekDay();
    viewPager.setVisibility(View.VISIBLE);
    progressBarView.setVisibility(View.GONE);
    return view;
  }

  @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }

    public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
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

  @Override
    public void setPagerStart(int dayOfWeek) {
        viewPager.setCurrentItem(dayOfWeek);
        tabLayout.setupWithViewPager(viewPager);
    }

    public interface OnFragmentInteractionListener {

    void onFragmentInteraction(Uri uri);
  }

}
