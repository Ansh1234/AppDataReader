package com.awesomedroidapps.inappstoragereader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;
import com.awesomedroidapps.inappstoragereader.views.TextWithIconViewHolder;

/**
 * Created by anshul on 30/03/17.
 */


public class TableColumnsAdapter extends RecyclerView.Adapter {

  private String[] columnNames;
  private Context context;
  private WhereQuerySelectListener listener;

  public TableColumnsAdapter(String[] columnNames, WhereQuerySelectListener listener) {
    this.columnNames = columnNames;
    this.listener=listener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.com_awesomedroidapps_inappstoragereader_columns_dropdown, parent,
            false);
    TextWithIconViewHolder viewHolder = new TextWithIconViewHolder(view,listener);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    TextWithIconViewHolder textWithIconViewHolder= (TextWithIconViewHolder) holder;
    textWithIconViewHolder.updateDatabaseItem(columnNames[position]);
  }

  @Override
  public int getItemCount() {
    return columnNames.length;
  }
}
