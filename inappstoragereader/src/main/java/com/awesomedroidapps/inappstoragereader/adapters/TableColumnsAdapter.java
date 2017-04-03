package com.awesomedroidapps.inappstoragereader.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;
import com.awesomedroidapps.inappstoragereader.views.TextWithIconViewHolder;

/**
 * Created by anshul on 30/03/17.
 */


public class TableColumnsAdapter extends ArrayAdapter {

  private String[] columnNames;
  private Context context;
  private WhereQuerySelectListener listener;

  public TableColumnsAdapter(@NonNull Context context, @LayoutRes int resource,
                             @NonNull String[] columnNames) {
    super(context, resource, columnNames);
    this.columnNames=columnNames;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    TextWithIconViewHolder holder = null;
    if(row==null) {
      row = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.com_awesomedroidapps_inappstoragereader_columns_dropdown, parent,
              false);
      holder = new TextWithIconViewHolder(row,listener);
      row.setTag(holder);
    }
    else{
      holder = (TextWithIconViewHolder) row.getTag();
    }
    holder.updateDatabaseItem(columnNames[position]);
    return row;
  }
}
