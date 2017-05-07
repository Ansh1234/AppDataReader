package com.awesomedroidapps.inappstoragereader.views;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

/**
 * Created by anshul on 15/1/17.
 */

public class TextWithIconViewHolder implements View.OnClickListener {

  private final TextView itemName;
  private WhereQuerySelectListener listener;
  private View view;
  private EditText columnValue;

  public TextWithIconViewHolder(View itemView, WhereQuerySelectListener whereQuerySelectListener) {
    this.view = itemView;
    view.setOnClickListener(this);
    this.listener = whereQuerySelectListener;
    this.itemName = (TextView) itemView.findViewById(R.id.column_name);
    this.columnValue = (EditText) itemView.findViewById(R.id.column_value);
    itemName.setVisibility(View.VISIBLE);
  }

  public void updateDatabaseItem(String columnName) {
    itemName.setText(columnName);
  }

  @Override
  public void onClick(View v) {

  }
}
