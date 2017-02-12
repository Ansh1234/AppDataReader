package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;
import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceItemViewHolder;
import com.awesomedroidapps.inappstoragereader.views.TableItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferencesListAdapter extends RecyclerView.Adapter {

  private List<SharedPreferenceObject> sharedPreferencesList;
  private WeakReference<Activity> activityWeakReference;

  public SharedPreferencesListAdapter(List sharedPreferencesList, Activity activity) {
    this.sharedPreferencesList = sharedPreferencesList;
    this.activityWeakReference = new WeakReference<Activity>(activity);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_shared_preference_item, parent, false);
    SharedPreferenceItemViewHolder viewHolder =
        new SharedPreferenceItemViewHolder(activityWeakReference, view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof SharedPreferenceItemViewHolder)) {
      return;
    }
    ((SharedPreferenceItemViewHolder) holder).updateTableItem(sharedPreferencesList.get(position));
  }

  @Override
  public int getItemCount() {
    return sharedPreferencesList.size();
  }
}
