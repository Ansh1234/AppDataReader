package com.awesomedroidapps.inappstoragereader.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

/**
 * Created by anshul on 15/1/17.
 */

public class TextWithIconViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView itemName;
  private final ImageView itemIcon;
  private WhereQuerySelectListener listener;
  private View view;
  private EditText columnValue;

  public TextWithIconViewHolder(View itemView, WhereQuerySelectListener whereQuerySelectListener) {
    super(itemView);
    this.view = itemView;
    view.setOnClickListener(this);
    this.listener = whereQuerySelectListener;
    this.itemName = (TextView) itemView.findViewById(R.id.column_name);
    this.itemIcon = (ImageView) itemView.findViewById(R.id.column_selected);
    this.columnValue = (EditText) itemView.findViewById(R.id.column_value);

    itemIcon.setOnClickListener(this);
  }

  public void updateDatabaseItem(String columnName) {
    itemName.setText(columnName);
  }

  @Override
  public void onClick(View v) {
    if (v == itemIcon) {
      showOrHideEditText();
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
