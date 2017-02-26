package com.awesomedroidapps.inappstoragereader.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.views.DataItemViewHolder;

import java.util.ArrayList;

/**
 * Created by anshul on 15/1/17.
 */

public class TableDataListAdapter extends RecyclerView.Adapter {

  private ArrayList<ArrayList<String>> tableDataList;
  private final ArrayList<Integer> columnWidthList;
  private final DataItemClickListener dataItemClickListener;


  public TableDataListAdapter(ArrayList<ArrayList<String>> tableDataList, Activity activity,
                              ArrayList<Integer> columnWidthList,
                              DataItemClickListener dataItemClickListener) {
    this.tableDataList = tableDataList;
    this.columnWidthList = columnWidthList;
    this.dataItemClickListener = dataItemClickListener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.com_awesomedroidapps_inappstoragereadder_view_item_row_container, parent,
            false);
    DataItemViewHolder tableHeaderViewHolder = new DataItemViewHolder(view, columnWidthList,
        view.getContext(), dataItemClickListener);
    return tableHeaderViewHolder;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((DataItemViewHolder) holder).updateTableDataItem(tableDataList.get
        (position), position == 0);
  }

  @Override
  public int getItemCount() {
    return tableDataList.size();
  }


}
