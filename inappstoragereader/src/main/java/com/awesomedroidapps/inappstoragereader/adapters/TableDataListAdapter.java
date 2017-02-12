package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.views.TableDataItemViewHolder;
import com.awesomedroidapps.inappstoragereader.views.TableHeaderViewHolder;
import com.awesomedroidapps.inappstoragereader.views.TableItemViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 15/1/17.
 */

public class TableDataListAdapter extends RecyclerView.Adapter {

  private ArrayList<ArrayList<String>> tableDataList;
  private WeakReference<Activity> activityWeakReference;
  private final int columnCount;
  private static final int HEADER = 0;
  private static final int ITEM = 1;

  public TableDataListAdapter(ArrayList<ArrayList<String>> tableDataList, Activity activity, int
      columnCount) {
    this.tableDataList = tableDataList;
    this.activityWeakReference = new WeakReference<Activity>(activity);
    this.columnCount = columnCount;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;
    switch (viewType) {
      case HEADER:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_table_header_item, parent, false);
        TableHeaderViewHolder tableHeaderViewHolder = new TableHeaderViewHolder(view, columnCount,
            view.getContext());
        return tableHeaderViewHolder;
      case ITEM:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_table_data_item, parent, false);
        TableDataItemViewHolder tableDataItemViewHolder =
            new TableDataItemViewHolder(view, columnCount, view.getContext());
        return tableDataItemViewHolder;
    }
    return null;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (position == 0) {
      ((TableHeaderViewHolder) holder).updateTableDataItem(position,
          tableDataList.get(position));
    } else {
      ((TableDataItemViewHolder) holder).updateTableDataItem(position, tableDataList.get
          (position));
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return HEADER;
    }
    return ITEM;
  }

  @Override
  public int getItemCount() {
    return tableDataList.size();
  }
}
