package com.fitme.android.fitme.MemberApp.scheduleFragment;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import com.fitme.android.fitme.MemberApp.listFragment.ListFragment;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import java.util.HashMap;
import java.util.Map;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

  final int PAGE_COUNT = 7;

  private String tabTitles[] = new String[] { "D", "2ª", "3ª", "4ª", "5ª", "6ª", "S"};
  private Context context;
  private Object user;

  public SampleFragmentPagerAdapter(FragmentManager fm, Context context, Object user) {
    super(fm);
    this.user = user;
    this.context = context;
  }

  @Override
  public int getCount() {
    return PAGE_COUNT;
  }

  @Override
  public Fragment getItem(int position) {
    return ListFragment.newInstance(toString().valueOf(position), (Parcelable) user);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return tabTitles[position];
  }
}