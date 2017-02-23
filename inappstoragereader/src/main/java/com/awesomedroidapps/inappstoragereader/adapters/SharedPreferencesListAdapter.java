package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;
import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceHeaderViewHolder;
import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceItemViewHolder;
import com.awesomedroidapps.inappstoragereader.views.TableDataItemViewHolder;
import com.awesomedroidapps.inappstoragereader.views.TableHeaderViewHolder;
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

    View view;
    switch (viewType) {
      case Constants.ITEM:
         view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_shared_preference_item, parent, false);
        SharedPreferenceItemViewHolder viewHolder =
            new SharedPreferenceItemViewHolder(activityWeakReference, view);
        return viewHolder;
      case Constants.HEADER:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_shared_preference_header, parent, false);
        SharedPreferenceHeaderViewHolder sharedPreferenceHeaderViewHolder = new
            SharedPreferenceHeaderViewHolder(activityWeakReference,view);
        return sharedPreferenceHeaderViewHolder;
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof SharedPreferenceItemViewHolder)) {
      return;
    }
    if (position == 0) {
      ((SharedPreferenceHeaderViewHolder) holder).updateTableItem(sharedPreferencesList.get(position));

    } else {
      ((SharedPreferenceItemViewHolder) holder).updateTableItem(sharedPreferencesList.get(position));

    }
  }

  @Override
  public int getItemCount() {
    return sharedPreferencesList.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return Constants.HEADER;
    }
    return Constants.ITEM;
  }

}
