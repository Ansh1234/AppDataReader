package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.views.DataItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A adapter for showing Shared Preferences.
 * Created by anshul on 15/1/17.
 */

public class SharedPreferencesListAdapter extends RecyclerView.Adapter {

  private List<Object> sharedPreferencesList;
  private WeakReference<Activity> activityWeakReference;
  private final ArrayList<Integer> columnWidthList;
  private final DataItemClickListener dataItemClickListener;

  public SharedPreferencesListAdapter(List sharedPreferencesList, Activity activity,
                                      ArrayList<Integer> columnWidthList,
                                      DataItemClickListener dataItemClickListener) {
    this.sharedPreferencesList = sharedPreferencesList;
    this.activityWeakReference = new WeakReference<>(activity);
    this.columnWidthList = columnWidthList;
    this.dataItemClickListener = dataItemClickListener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.com_awesomedroidapps_inappstoragereadder_view_item_row_container, parent,
            false);
    DataItemViewHolder viewHolder =
        new DataItemViewHolder(view, columnWidthList, activityWeakReference.get(),
            dataItemClickListener);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof DataItemViewHolder)) {
      return;
    }
    ArrayList arrayList = null;
    if (sharedPreferencesList.get(position) instanceof SharedPreferenceObject) {
      arrayList = ((SharedPreferenceObject) sharedPreferencesList.get(position)).getAsList();
    } else if (sharedPreferencesList.get(position) instanceof List) {
      arrayList = (ArrayList) sharedPreferencesList.get(position);
    }
    ((DataItemViewHolder) holder).updateTableDataItem(arrayList, position == 0);
  }

  @Override
  public int getItemCount() {
    return sharedPreferencesList.size();
  }
}
