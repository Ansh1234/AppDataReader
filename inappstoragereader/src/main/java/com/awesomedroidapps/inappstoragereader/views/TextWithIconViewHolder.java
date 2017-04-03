package com.awesomedroidapps.inappstoragereader.views;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

/**
 * Created by anshul on 15/1/17.
 */

public class TextWithIconViewHolder implements View.OnClickListener {

  private final TextView itemName;
  private final ImageView itemIcon;
  private final FrameLayout itemIconContainer;
  private WhereQuerySelectListener listener;
  private View view;
  private EditText columnValue;

  public TextWithIconViewHolder(View itemView, WhereQuerySelectListener whereQuerySelectListener) {
    this.view = itemView;
    view.setOnClickListener(this);
    this.listener = whereQuerySelectListener;
    this.itemName = (TextView) itemView.findViewById(R.id.column_name);
    this.itemIconContainer = (FrameLayout) itemView.findViewById(R.id.column_select_container);
    this.itemIcon = (ImageView) itemView.findViewById(R.id.column_select);
    this.columnValue = (EditText) itemView.findViewById(R.id.column_value);
    itemIconContainer.setOnClickListener(this);
    itemName.setVisibility(View.VISIBLE);
  }

  public void updateDatabaseItem(String columnName) {
    itemName.setText(columnName);
  }

  @Override
  public void onClick(View v) {
    if (v == itemIconContainer) {
    //  showOrHideEditText();
    }
  }

  private void showOrHideEditText(){
    if(columnValue.getVisibility()==View.GONE){
      columnValue.setVisibility(View.VISIBLE);
      itemIcon.setImageResource(R.drawable.com_awesomedroidapps_inappstorage_arrow_up);
    }else{
      columnValue.setVisibility(View.GONE);
      itemIcon.setImageResource(R.drawable.com_awesomedroidapps_inappstorage_arrow_down);
    }
  }
}
