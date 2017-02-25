package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.views.DataItemViewHolder;

import java.util.ArrayList;

/**
 * Created by anshul on 15/1/17.
 */

public class TableDataListAdapter extends RecyclerView.Adapter {

  private ArrayList<ArrayList<String>> tableDataList;
  private final ArrayList<Integer> columnWidthList;


  public TableDataListAdapter(ArrayList<ArrayList<String>> tableDataList, Activity activity,
                              ArrayList<Integer> columnWidthList) {
    this.tableDataList = tableDataList;
    this.columnWidthList = columnWidthList;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.com_awesomedroidapps_inappstoragereadder_view_data_item, parent, false);
    DataItemViewHolder tableHeaderViewHolder = new DataItemViewHolder(view, columnWidthList,
        view.getContext());
    return tableHeaderViewHolder;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((DataItemViewHolder) holder).updateTableDataItem(tableDataList.get
        (position));
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return Constants.HEADER;
    }
    return Constants.ITEM;
  }

  @Override
  public int getItemCount() {
    return tableDataList.size();
  }
}
