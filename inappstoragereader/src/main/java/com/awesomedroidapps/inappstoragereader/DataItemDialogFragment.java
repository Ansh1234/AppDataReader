package com.awesomedroidapps.inappstoragereader;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A dialog fragment for copying the data of an individual data column.
 * Created by anshul on 26/2/17.
 */

public class DataItemDialogFragment extends DialogFragment {

  private String data;
  private AlertDialog alertDialog;
  private View alertDialogView;
  private TextView columnValue;
  private EditText editedColumnValue;

  public static DataItemDialogFragment newInstance(String data) {
    DataItemDialogFragment dataItemDialogFragment = new DataItemDialogFragment();
    dataItemDialogFragment.setData(data);
    return dataItemDialogFragment;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
    alertDialogView = layoutInflater.inflate(R.layout
        .com_awesomedroidapps_inappstoragereadder_view_modify, null);
    columnValue = (TextView) alertDialogView.findViewById(R.id.column_value);
    columnValue.setText(data);
    editedColumnValue = (EditText) alertDialogView.findViewById(R.id.edited_column_value);
    editedColumnValue.setVisibility(View.GONE);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


    builder.setView(alertDialogView);
    // builder.setMessage(data);
    builder.setPositiveButton(Constants.DIALOG_COPY, null);
    builder.setNegativeButton(Constants.DIALOG_EDIT, null);

    alertDialog = builder.create();
    alertDialog.setOnShowListener(onShowListener);
    return alertDialog;
  }


  DialogInterface.OnShowListener onShowListener = new DialogInterface.OnShowListener() {
    @Override
    public void onShow(DialogInterface dialog) {
      Button copyButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
      copyButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onCopyButtonClicked();
        }
      });

      final Button editOrDoneButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
      editOrDoneButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (Constants.DIALOG_EDIT.equals(editOrDoneButton.getText().toString())) {
            onEditButtonClicked(editOrDoneButton);
          }else{
            onDoneButtonClicked();
          }
        }
      });
    }
  };

  private void onCopyButtonClicked() {
    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context
        .CLIPBOARD_SERVICE);
    ClipData clip = ClipData.newPlainText("data", data);
    clipboard.setPrimaryClip(clip);
    alertDialog.dismiss();
  }

  private void onEditButtonClicked(Button editButton) {
    editButton.setText("DONE");
    editedColumnValue.setVisibility(View.VISIBLE);
    editedColumnValue.setText(data);
    editedColumnValue.requestFocus();
    columnValue.setVisibility(View.GONE);
  }

  private void onDoneButtonClicked() {

  }
}
