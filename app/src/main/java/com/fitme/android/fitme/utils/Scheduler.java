package com.fitme.android.fitme.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Scheduler {

  public void runOnUiThread(Runnable runnable) {
    Handler mainHandler = new Handler(Looper.getMainLooper());
    mainHandler.post(runnable);
  }

  public void runOnBackground(Runnable runnable) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(runnable);
  }
}