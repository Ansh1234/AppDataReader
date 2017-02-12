package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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
  private final int columnCount;

  public TableDataItemViewHolder(View itemView, int columnCount, Context context) {
    super(itemView);
    this.tableDataRowContainer = (TableLayout) itemView.findViewById(R.id.table_data_row_container);
    this.columnCount = columnCount;

    TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams
        .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    TableRow tableRow = new TableRow(context);
    tableRow.setLayoutParams(layoutParams);

    for (int i = 0; i < columnCount; i++) {
      TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
          .WRAP_CONTENT);
      TextView textView = new TextView(context);
      textView.setLayoutParams(params);
      tableRow.addView(textView);
    }
    tableDataRowContainer.addView(tableRow);

  }

  public void updateTableDataItem(int position, ArrayList<String> rowData) {
    TableRow tableRow = (TableRow) tableDataRowContainer.getChildAt(0);
    for (int i = 0; i < rowData.size(); i++) {
      TextView textView = (TextView) tableRow.getChildAt(i);
      textView.setText(rowData.get(i));
    }

  }

  @Override
  public void onClick(View v) {
  }
}
