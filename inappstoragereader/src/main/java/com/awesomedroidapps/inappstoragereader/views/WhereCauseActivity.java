package com.awesomedroidapps.inappstoragereader.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;

import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereCauseActivity extends AppCompatActivity implements View.OnClickListener {

  private Button whereClauseSubmitButton;
  private LinearLayout whereClauseContainer;
  private String[] columnNames;
  private List list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_where_clause);
    whereClauseSubmitButton = (Button) findViewById(R.id.where_clause_button_done);
    whereClauseSubmitButton.setOnClickListener(this);

    Bundle bundle = getIntent().getExtras();
    String databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    String tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
    columnNames = SqliteDatabaseReader.getColumnNames(this, databaseName, tableName);
    list = SqliteDatabaseReader.getColumnTypes(this, databaseName, tableName);
    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    whereClauseContainer = (LinearLayout) findViewById(R.id.where_clause_container);
    fillWhereClauseData(columnNames, layoutInflater);
  }

  private void fillWhereClauseData(String[] columnNames, LayoutInflater layoutInflater) {
    if (columnNames == null || columnNames.length == 0) {
      return;
    }
    for (int i = 0; i < columnNames.length; i++) {
      View view = layoutInflater.inflate(R.layout
          .com_awesomedroidapps_inappstoragereader_columns_dropdown, null);
      TextView textView = (TextView) view.findViewById(R.id.column_name);
      textView.setText(columnNames[i]);
      whereClauseContainer.addView(view);
    }
  }

  @Override
  public void onClick(View v) {
    if (v == whereClauseSubmitButton) {
      getWhereClauseQuery();
    }
  }

  private void getWhereClauseQuery() {
    StringBuilder queryBuilder = new StringBuilder();
    for (int i = 0; i < whereClauseContainer.getChildCount(); i++) {
      RelativeLayout listViewItem = (RelativeLayout) whereClauseContainer.getChildAt(i);
      EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
      TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
      if (Constants.EMPTY_STRING.equals(editText.getText().toString())) {
        continue;
      }

      Integer queryDataType = (Integer) list.get(i);

      if (queryDataType == Cursor.FIELD_TYPE_INTEGER) {
        try {
          Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException exception) {
          Toast.makeText(this, "The value of " + textView.getText().toString() + " field is not " +
              "proper ", Toast.LENGTH_SHORT).show();
          return;
        }
      }else if(queryDataType == Cursor.FIELD_TYPE_FLOAT){
        try {
          Float.parseFloat(editText.getText().toString());
        } catch (NumberFormatException exception) {
          Toast.makeText(this, "The value of " + textView.getText().toString() + " field is not " +
              "proper ", Toast.LENGTH_SHORT).show();
          return;
        }
      }
      queryBuilder.append(textView.getText().toString());
      queryBuilder.append(Constants.EQUAL);
      if (Cursor.FIELD_TYPE_STRING == queryDataType) {
        queryBuilder.append(Constants.INVERTED_COMMA);
      }
      queryBuilder.append(editText.getText().toString());
      if (Cursor.FIELD_TYPE_STRING == queryDataType) {
        queryBuilder.append(Constants.INVERTED_COMMA);
      }
      queryBuilder.append(Constants.SPACE);
      queryBuilder.append(Constants.AND);
      queryBuilder.append(Constants.SPACE);
    }
    String str = queryBuilder.toString();
    if (str.endsWith(Constants.SPACE + Constants.AND + Constants.SPACE)) {
      str = str.substring(0, str.lastIndexOf(Constants.SPACE + Constants.AND + Constants.SPACE));
    }
    Intent intent = new Intent();
    intent.putExtra(Constants.BUNDLE_WHERE_CLAUSE, str);
    setResult(RESULT_OK, intent);
    finish();
  }
}
