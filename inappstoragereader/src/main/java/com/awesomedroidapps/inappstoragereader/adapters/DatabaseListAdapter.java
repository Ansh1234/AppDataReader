package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.views.DatabaseItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class DatabaseListAdapter extends RecyclerView.Adapter {

  private List<String> databaseList;
  private WeakReference<Activity> activityWeakReference;

  public DatabaseListAdapter(List databaseList, Activity activity) {
    this.databaseList = databaseList;
    this.activityWeakReference = new WeakReference<Activity>(activity);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_database_item, parent, false);
    DatabaseItemViewHolder viewHolder = new DatabaseItemViewHolder(activityWeakReference, view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof DatabaseItemViewHolder)) {
      return;
    }
    ((DatabaseItemViewHolder) holder).updateDatabaseItem(databaseList.get(position));
  }

  @Override
  public int getItemCount() {
    return databaseList.size();
  }
}
