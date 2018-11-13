package com.fitme.android.fitme.MemberApp.athletesListFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;

public interface AthletesListContract {

  interface AthletesListView extends BaseView<AthletesListPresenter> {
  }

  interface AthletesListPresenter extends BasePresenter<AthletesListView> {

  }
}
