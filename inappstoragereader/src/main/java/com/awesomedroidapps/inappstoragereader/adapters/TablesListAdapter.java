package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.views.TableItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class TablesListAdapter extends RecyclerView.Adapter {

  private List<String> tablesList;
  private WeakReference<Activity> activityWeakReference;
  private final String databaseName;

  public TablesListAdapter(List tablesList, Activity activity, String databaseName) {
    this.tablesList = tablesList;
    this.activityWeakReference = new WeakReference<Activity>(activity);
    this.databaseName=databaseName;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_table_item, parent, false);
    TableItemViewHolder viewHolder = new TableItemViewHolder(activityWeakReference, view,databaseName);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof TableItemViewHolder)) {
      return;
    }
    ((TableItemViewHolder) holder).updateTableItem(tablesList.get(position));
  }

  @Override
  public int getItemCount() {
    return tablesList.size();
  }
}
