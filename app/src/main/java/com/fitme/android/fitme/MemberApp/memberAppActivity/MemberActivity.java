package com.fitme.android.fitme.MemberApp.memberAppActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.OnClick;
import com.fitme.android.fitme.MemberApp.classInfoFragment.ClassFragment;
import com.fitme.android.fitme.MemberApp.newClassFragment.NewClassFragment;
import com.fitme.android.fitme.MemberApp.profileFragment.PerfilFragment;
import com.fitme.android.fitme.MemberApp.scheduleFragment.ScheduleFragment;
import com.fitme.android.fitme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;

import static com.fitme.android.fitme.utils.Codes.USER_KEY;

public class MemberActivity extends AppCompatActivity implements MemberAppContract.MemberAppView, NavigationView.OnNavigationItemSelectedListener, ScheduleFragment.OnFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener,
    ClassFragment.OnFragmentInteractionListener, NewClassFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.add_new_class)
    FloatingActionButton fab_add_new_class;

    MemberAppContract.MemberAppPresenter mPresenter;

    Object user;
    String current_user_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);

        mPresenter = new MemberAppPresenter();
        mPresenter.attachView(this);
        setSupportActionBar(toolbar);

        drawer.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Intent intentFromLogin = getIntent();
        user = intentFromLogin.getParcelableExtra(USER_KEY);

        current_user_name = mPresenter.getCurrentUser(user);

        showNameMemberInNav();

    }

    public void showNameMemberInNav(){
      View headerView = navigationView.getHeaderView(0);
      TextView navUsername = headerView.findViewById(R.id.helloMemberView);
      navUsername.setText("Hello " + current_user_name + " !");
    }


  @OnClick(R.id.add_new_class)
  public void fab_action(View view){
      fab_add_new_class.setVisibility(View.GONE);
      NewClassFragment newClassFragment = NewClassFragment.newInstance((Parcelable) user);
      getSupportFragmentManager().beginTransaction().replace(R.id.content_member, newClassFragment).addToBackStack("DrawerMenu").commit();}


    @Override
    public void onBackPressed() {

        drawer.setScrimColor(Color.TRANSPARENT);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        String itemSelected = "";

        if (id == R.id.perfil) {
            itemSelected = "profile";

        } else if (id == R.id.schedule_item) {
            itemSelected = "schedule";

        } else if (id == R.id.logout) {
            itemSelected = "logout";
        }

        mPresenter.onNavegationItemClick(itemSelected);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void makeFabVisible() {
        fab_add_new_class.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProfile() {

      PerfilFragment profileFragment = PerfilFragment.newInstance((Parcelable) user);
      getSupportFragmentManager().beginTransaction().replace(R.id.content_member, profileFragment).addToBackStack("DrawerMenu").commit();
    }

    @Override
    public void showSchedule() {
      ScheduleFragment scheduleFragment = ScheduleFragment.newInstance((Parcelable) user);
      getSupportFragmentManager().beginTransaction().replace(R.id.content_member, scheduleFragment).commit();

  }

  @Override public void logOut() {
        finish();
    }

  @Override public void onClassFragmentInteraction(Parcelable object) {
    user = object;
    Log.i("GymYo 11", ((Athlete) user).getSchedule().toString());
  }

  @Override public void onProfileFragmentInteraction(Parcelable object) {
    user = object;
  }

  @Override public void onNewClassFragmentInteraction(Parcelable object) {
    user = object;
    Log.i("NEW CLASS", "ACTIVITY");
    fab_add_new_class.setVisibility(View.VISIBLE);
  }
}
