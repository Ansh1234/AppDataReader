package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceFileViewHolder;
import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceHeaderViewHolder;
import com.awesomedroidapps.inappstoragereader.views.SharedPreferenceItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class SharedPreferencesFileListAdapter extends RecyclerView.Adapter {

  private List<String> sharedPreferencesFileList;
  private WeakReference<Activity> activityWeakReference;

  public SharedPreferencesFileListAdapter(List sharedPreferencesFileList, Activity activity) {
    this.sharedPreferencesFileList = sharedPreferencesFileList;
    this.activityWeakReference = new WeakReference<>(activity);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_shared_preferences_file_item, parent, false);
    SharedPreferenceFileViewHolder viewHolder =
        new SharedPreferenceFileViewHolder(activityWeakReference, view);
    return viewHolder;

  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (!(holder instanceof SharedPreferenceFileViewHolder)) {
      return;
    }
    ((SharedPreferenceFileViewHolder) holder).updateTableItem(
        sharedPreferencesFileList.get(position));
  }

  @Override
  public int getItemCount() {
    return sharedPreferencesFileList.size();
  }


}
