package com.awesomedroidapps.inappstoragereader;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by anshul on 30/03/17.
 */

public class TableColumnsViewHolder extends RecyclerView.ViewHolder {

  private TextView tableNameTv;
  private CheckBox isSelected;

  public TableColumnsViewHolder(View itemView) {
    super(itemView);
    tableNameTv = (TextView) itemView.findViewById(R.id.column_name);
    isSelected = (CheckBox) itemView.findViewById(R.id.column_selected);
  }

  public void update(String tableName){
    tableNameTv.setText(tableName);
  }
}
