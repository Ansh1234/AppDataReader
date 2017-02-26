package com.awesomedroidapps.inappstoragereader;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by anshul on 26/2/17.
 */

public class DataItemDialogFragment extends DialogFragment
    implements DialogInterface.OnClickListener {

  private String data;

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

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    String positiveButtonText = getContext().getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_dialog_copy);

    builder.setMessage(data);
    builder.setPositiveButton(positiveButtonText, this);
    return builder.create();
  }

  @Override
  public void onClick(DialogInterface dialogInterface, int i) {

  }
}
