package com.awesomedroidapps.inappstoragereader.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class TableColumnsDialog extends DialogFragment {

  private String[] columns;
  private List<String> selectedColumns = new ArrayList<>();
  private ColumnSelectListener columnSelectListener;

  public static TableColumnsDialog newInstance(String[] columns,
                                               ColumnSelectListener columnSelectListener) {
    TableColumnsDialog tableColumnsDialog = new TableColumnsDialog();
    tableColumnsDialog.setColumns(columns);
    tableColumnsDialog.setColumnSelectListener(columnSelectListener);
    return tableColumnsDialog;
  }

  public void setColumns(String[] columns) {
    this.columns = columns;
  }

  public void setColumnSelectListener(
      ColumnSelectListener columnSelectListener) {
    this.columnSelectListener = columnSelectListener;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Set the dialog title
    builder.setTitle(Utils.getString(getActivity(),
        R.string.com_awesomedroidapps_inappstoragereader_select_column_title));
    builder.setMultiChoiceItems(columns, null, listener)
        // Set the action buttons
        .setPositiveButton(Utils.getString(getActivity(),
            R.string.com_awesomedroidapps_inappstoragereader_dialog_positive_button),
            dialogPositiveListener)
        .setNegativeButton(Utils.getString(getActivity(),
            R.string.com_awesomedroidapps_inappstoragereader_dialog_negetive_button),
            dialogNegetiveListener);
    return builder.create();
  }

  private DialogInterface.OnMultiChoiceClickListener listener =
      new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
          if (isChecked) {
            selectedColumns.add(columns[which]);
          } else if (selectedColumns.contains(which)) {
            selectedColumns.remove(Integer.valueOf(which));
          }
        }
      };

  private DialogInterface.OnClickListener dialogNegetiveListener =
      new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
      };

  private DialogInterface.OnClickListener dialogPositiveListener =
      new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          if (Utils.isEmpty(selectedColumns)) {
            return;
          }
          StringBuilder stringBuilder = new StringBuilder();
          for (String string : selectedColumns) {
            stringBuilder.append(string + ",");
          }

          String columnString = stringBuilder.toString();
          if (columnString.endsWith(",")) {
            columnString = columnString.substring(0, columnString.length() - 1);
          }
          columnSelectListener.onColumnsSelected(columnString);
        }
      };
}
