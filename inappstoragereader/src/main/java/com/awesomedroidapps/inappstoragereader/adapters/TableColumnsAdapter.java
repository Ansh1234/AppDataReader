package com.awesomedroidapps.inappstoragereader.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.TableColumnsViewHolder;

import java.util.List;

/**
 * Created by anshul on 30/03/17.
 */


public class TableColumnsAdapter extends ArrayAdapter {

  private String[] columnNames;
  private Context context;

  public TableColumnsAdapter(@NonNull Context context, @LayoutRes int resource,
                             @NonNull String[] objects) {
    super(context, resource, objects);
    this.columnNames = objects;
    this.context = context;
  }


  @Override
  public View getDropDownView(int position, View convertView,
                              ViewGroup parent) {
    return getCustomView(position, convertView, parent);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return getCustomView(position, convertView, parent);
  }

  public View getCustomView(final int position, View convertView,
                            ViewGroup parent) {
    LayoutInflater layoutInflator = LayoutInflater.from(context);
    convertView =
        layoutInflator.inflate(R.layout.com_awesomedroidapps_inappstoragereader_columns_dropdown,
            null);
    TableColumnsViewHolder viewHolder = new TableColumnsViewHolder(convertView);
    viewHolder.update(columnNames[position]);
    return convertView;
  }


}
