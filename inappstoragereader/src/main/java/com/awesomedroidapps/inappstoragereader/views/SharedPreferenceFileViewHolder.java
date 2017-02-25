package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferenceFileViewHolder extends RecyclerView.ViewHolder {

  private final TextView sharedPreferenceFileName;

  public SharedPreferenceFileViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.sharedPreferenceFileName = (TextView) itemView.findViewById(R.id.text_shared_preference_file_name);
  }

  public void updateTableItem(String fileName) {
   sharedPreferenceFileName.setText(fileName);
  }

}
