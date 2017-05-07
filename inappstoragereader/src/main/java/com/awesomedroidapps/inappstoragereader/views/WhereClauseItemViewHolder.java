package com.awesomedroidapps.inappstoragereader.views;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;

/**
 * Created by anshul on 15/1/17.
 */

public class WhereClauseItemViewHolder implements View.OnClickListener {

  private final TextView columnName;
  private final CheckBox fieldIsNull;
  private final EditText columnValue;

  public WhereClauseItemViewHolder(View itemView) {
    this.columnName = (TextView) itemView.findViewById(R.id.column_name);
    this.columnValue = (EditText) itemView.findViewById(R.id.column_value);
    this.fieldIsNull = (CheckBox) itemView.findViewById(R.id.field_is_null);
    fieldIsNull.setOnClickListener(this);
  }

  public void update(String columnNameText) {
    columnName.setText(columnNameText);
  }

  @Override
  public void onClick(View v) {
    if (v == fieldIsNull) {
      if (fieldIsNull.isChecked()) {
        String string = Utils.getString(v.getContext(), R.string
            .com_awesomedroidapps_inappstoragereader_column_is_null);
        columnValue.setText(string);
        columnValue.setEnabled(false);
      } else {
        columnValue.setEnabled(true);
        columnValue.setText(Constants.EMPTY_STRING);
      }
    }
  }
}
