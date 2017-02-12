package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.views.AppDataItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class AppDataListAdapter extends RecyclerView.Adapter {

  private List<AppDataStorageItem> databaseList;
  private WeakReference<Activity> activityWeakReference;

  public AppDataListAdapter(List databaseList, Activity activity) {
    this.databaseList = databaseList;
    this.activityWeakReference = new WeakReference<>(activity);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_database_item, parent, false);
    AppDataItemViewHolder viewHolder = new AppDataItemViewHolder(activityWeakReference, view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof AppDataItemViewHolder)) {
      return;
    }
    ((AppDataItemViewHolder) holder).updateDatabaseItem(databaseList.get(position));
  }

  @Override
  public int getItemCount() {
    return databaseList.size();
  }
}
