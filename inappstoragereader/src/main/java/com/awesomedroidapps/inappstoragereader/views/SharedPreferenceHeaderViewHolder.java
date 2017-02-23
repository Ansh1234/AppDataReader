package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferenceHeaderViewHolder extends RecyclerView.ViewHolder {

  private final TextView sharedPreferenceKey, sharedPreferenceValue, sharedPreferenceDataType;
  private final WeakReference<Activity> activity;
  private String tableName, databaseName;

  public SharedPreferenceHeaderViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.activity = activity;
    this.sharedPreferenceKey = (TextView) itemView.findViewById(R.id.shared_preferences_key);
    this.sharedPreferenceValue = (TextView) itemView.findViewById(R.id.shared_preferences_value);
    this.sharedPreferenceDataType = (TextView) itemView.findViewById(R.id
        .shared_preferences_data_type);

  }

  public void updateTableItem(SharedPreferenceObject sharedPreferenceObject) {
  }
}
