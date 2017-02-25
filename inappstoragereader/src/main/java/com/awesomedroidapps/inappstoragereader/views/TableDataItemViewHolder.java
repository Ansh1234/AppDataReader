package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by anshul on 15/1/17.
 */

public class TableDataItemViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TableLayout tableDataRowContainer;
  private final ArrayList<Integer> columnWidthList;


  public TableDataItemViewHolder(View itemView, ArrayList<Integer> columnWidth, Context context) {
    super(itemView);
    this.tableDataRowContainer = (TableLayout) itemView.findViewById(R.id.table_data_row_container);
    this.columnWidthList = columnWidth;


    TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams
        .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    TableRow tableRow = new TableRow(context);
    tableRow.setLayoutParams(layoutParams);

    for (int i = 0; i < columnWidthList.size(); i++) {
      LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
          .LAYOUT_INFLATER_SERVICE);
      LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.view_individual_database_item,
          null);
      LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(columnWidthList.get
          (i), ViewGroup.LayoutParams.MATCH_PARENT);
      view.getChildAt(0).setLayoutParams(layoutParams1);
      tableRow.addView(view);
    }
    tableDataRowContainer.addView(tableRow);

  }

  public void updateTableDataItem(int position, ArrayList<String> rowData) {
    TableRow tableRow = (TableRow) tableDataRowContainer.getChildAt(0);
    for (int i = 0; i < rowData.size(); i++) {
      if (tableRow.getChildCount() == 0) {
        continue;
      }
      LinearLayout linearLayout = (LinearLayout) tableRow.getChildAt(i);
      TextView textView = (TextView) linearLayout.getChildAt(0);
      textView.setText(rowData.get(i));
    }
  }

  @Override
  public void onClick(View v) {
  }
}
