package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

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

    for (int i = 0; i < columnCount; i++) {
      TableRow tableRow = new TableRow(context);
      TextView textView = new TextView(context);
      tableRow.addView(textView);
      tableDataRowContainer.addView(tableRow);
    }
  }

  public void updateTableDataItem(int position, String value) {
    TableRow tableRow = (TableRow) tableDataRowContainer.getChildAt(position);
    TextView textView = (TextView) tableRow.getChildAt(0);
    textView.setText(value);
  }

  @Override
  public void onClick(View v) {
  }
}
