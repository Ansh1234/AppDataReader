package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferenceFileViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView sharedPreferenceFileName;
  private final RelativeLayout sharedPreferenceFileContainer;
  private final WeakReference<Activity> activityWeakReference;
  private String fileName;

  public SharedPreferenceFileViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.sharedPreferenceFileName =
        (TextView) itemView.findViewById(R.id.text_shared_preference_file_name);
    this.sharedPreferenceFileContainer = (RelativeLayout) itemView.findViewById(R.id
        .shared_preference_file_container);
    sharedPreferenceFileContainer.setOnClickListener(this);
    activityWeakReference = activity;
  }

  public void updateTableItem(String fileName) {
    this.fileName = fileName;
    sharedPreferenceFileName.setText(fileName);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.shared_preference_file_container) {
      Intent intent = new Intent(activityWeakReference.get(), SharedPreferencesActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString(Constants.BUNDLE_FILE_NAME, fileName);
      intent.putExtras(bundle);
      activityWeakReference.get().startActivity(intent);
    }
  }
}
