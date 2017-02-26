package com.awesomedroidapps.inappstoragereader.views;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.ArrayList;

/**
 * Created by anshul on 15/1/17.
 */

public class DataItemViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final LinearLayout rowDataContainer;
  private final ArrayList<Integer> columnWidthList;


  public DataItemViewHolder(View itemView, ArrayList<Integer> columnWidth, Context context) {
    super(itemView);
    this.rowDataContainer = (LinearLayout) itemView.findViewById(R.id.table_data_row_container);
    this.columnWidthList = columnWidth;

    for (int i = 0; i < columnWidthList.size(); i++) {
      LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
          .LAYOUT_INFLATER_SERVICE);
      RelativeLayout view =
          (RelativeLayout) layoutInflater.inflate(R.layout
                  .com_awesomedroidapps_inappstoragereader_view_individual_data_item, null);
      view.setOnClickListener(this);
      LinearLayout.LayoutParams layoutParams1 =
          new LinearLayout.LayoutParams(columnWidthList.get
              (i), ViewGroup.LayoutParams.MATCH_PARENT);
      view.setLayoutParams(layoutParams1);
      rowDataContainer.addView(view);
    }
  }

  public void updateTableDataItem(ArrayList<String> rowData, boolean isHeader) {
    for (int i = 0; i < rowData.size(); i++) {
      RelativeLayout relativeLayout = (RelativeLayout) rowDataContainer.getChildAt(i);
      TextView textView = (TextView) relativeLayout.getChildAt(0);
      textView.setText(rowData.get(i));
      if (isHeader) {
        Utils.setTextAppearance(textView, R.style.AppItemHeaderTextViewStyle);
      } else {
        Utils.setTextAppearance(textView, R.style.AppItemTextViewStyle);
      }
    }
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.column_data) {
      RelativeLayout relativeLayout = (RelativeLayout) v;
      TextView textView = (TextView) relativeLayout.getChildAt(0);
      System.out.println(textView.getText().toString());

    }
  }
}
