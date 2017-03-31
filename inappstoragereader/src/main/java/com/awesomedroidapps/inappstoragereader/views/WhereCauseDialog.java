package com.awesomedroidapps.inappstoragereader.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereCauseDialog extends DialogFragment {

  private String[] columns;
  private List<String> selectedColumns = new ArrayList<>();
  private ColumnSelectListener columnSelectListener;

  public static WhereCauseDialog newInstance(String[] columns,
                                             ColumnSelectListener columnSelectListener) {
    WhereCauseDialog tableColumnsDialog = new WhereCauseDialog();
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
    final ArrayAdapter<String> arrayAdapterItems = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_expandable_list_item_1, columns);

    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
    View dialogView = inflater.inflate(R.layout.com_awesomedroidapps_inappstoragereader_where_clause, null);
    builder.setView(dialogView);

    //builder.setAdapter(arrayAdapterItems, listener);
   // builder.setTitle("Click on individual column name and set the value");
//    builder.setPositiveButton(Utils.getString(getActivity(),
//        R.string.com_awesomedroidapps_inappstoragereader_dialog_positive_button),
//        dialogPositiveListener)
//        .setNegativeButton(Utils.getString(getActivity(),
//            R.string.com_awesomedroidapps_inappstoragereader_dialog_negetive_button),
//            dialogNegetiveListener);
    return builder.create();
  }

  private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {

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
            stringBuilder.append(string);
            stringBuilder.append(Constants.COMMA);
            stringBuilder.append(Constants.SPACE);
          }

          String columnString = stringBuilder.toString();
          if (columnString.endsWith(Constants.COMMA)) {
            columnString = columnString.substring(0, columnString.length() - 1);
          }
          columnSelectListener.onColumnsSelected(columnString);
        }
      };
}
