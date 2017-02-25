package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferenceItemViewHolder extends RecyclerView.ViewHolder {

  private final TextView sharedPreferenceKey, sharedPreferenceValue, sharedPreferenceDataType;
  private final WeakReference<Activity> activity;
  private String tableName, databaseName;

  public SharedPreferenceItemViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.activity = activity;
    this.sharedPreferenceKey = (TextView) itemView.findViewById(R.id.shared_preferences_key);
    this.sharedPreferenceValue = (TextView) itemView.findViewById(R.id.shared_preferences_value);
    this.sharedPreferenceDataType = (TextView) itemView.findViewById(R.id
        .shared_preferences_data_type);

  }

  public void updateTableItem(SharedPreferenceObject sharedPreferenceObject) {
    this.tableName = tableName;
    sharedPreferenceKey.setText(sharedPreferenceObject.getKey());
    sharedPreferenceValue.setText(sharedPreferenceObject.getValue());
    sharedPreferenceDataType.setText(sharedPreferenceObject.getSharedPreferenceDataType().name());
  }

}
