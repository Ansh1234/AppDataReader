package com.awesomedroidapps.inappstoragereader.views;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.entities.TableInfo;
import com.awesomedroidapps.inappstoragereader.utilities.AppDatabaseHelper;

import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class ClauseActivity extends BaseActivity implements View.OnClickListener {

  private Button clauseSubmitButton;
  private LinearLayout whereClauseContainer;
  private TableInfo tableInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_clause);
    clauseSubmitButton = (Button) findViewById(R.id.where_clause_button_done);
    clauseSubmitButton.setOnClickListener(this);
    whereClauseContainer = (LinearLayout) findViewById(R.id.select_where_clause_container);

    Bundle bundle = getIntent().getExtras();
    tableInfo = (TableInfo) bundle.getSerializable(Constants.BUNDLE_TABLE_INFO);

    // Here wer make sure that the tableInfo has all the important variable members like
    // tableName, databaseName, columnNames and columnTypes.
    if (tableInfo == null || tableInfo.isNotProper()) {
      String toastMessage = Utils.getString(this, R.string
          .com_awesomedroidapps_inappstoragereader_generic_error);
      Utils.showLongToast(this, toastMessage);
      finish();
      return;
    }

    fillWhereClauseData(tableInfo.getTableColumnNames());
  }

  private void fillWhereClauseData(List<String> columnNames) {
    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    for (int i = 0; i < columnNames.size(); i++) {
      View view = layoutInflater.inflate(R.layout
          .com_awesomedroidapps_inappstoragereader_columns_dropdown, null);
      WhereClauseItemViewHolder viewHolder = new WhereClauseItemViewHolder(view);
      viewHolder.update(columnNames.get(i));
      whereClauseContainer.addView(view);
    }
  }

  @Override
  public void onClick(View v) {
    if (v == clauseSubmitButton) {
      handleClauseSubmitButton();
    }
  }

  private void handleClauseSubmitButton() {
    int requestCode = getIntent().getExtras().getInt(Constants.BUNDLE_REQUEST_CODE);
    if (requestCode == Constants.REQUEST_CODE_SET_CLAUSE) {
      getContentValuesForUpdateQuery();
      return;
    } else if (requestCode == Constants.REQUEST_CODE_VALUES_CLAUSE) {
      getContentValuesForInsertQuery();
      return;
    } else if (requestCode == Constants.REQUEST_CODE_WHERE_CLAUSE) {
      getWhereClauseQuery();
      return;
    }
  }

  /**
   * This method returns the Content Values for the insert Query. It has returns a string to be
   * shown on the UI.
   *
   * @return
   */
  private ContentValues getContentValuesForInsertQuery() {

    ContentValues contentValues = new ContentValues();
    StringBuilder columnNamesBuilder = new StringBuilder(Constants.OPENING_BRACKET);
    StringBuilder columnValuesBuilder = new StringBuilder(Constants.OPENING_BRACKET);

    for (int i = 0; i < whereClauseContainer.getChildCount(); i++) {
      RelativeLayout listViewItem = (RelativeLayout) whereClauseContainer.getChildAt(i);
      EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
      TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
      String columnNewValue = editText.getText().toString();
      if (Constants.EMPTY_STRING.equals(columnNewValue)) {
        continue;
      }

      columnNewValue = columnNewValue.trim();

      List<DatabaseColumnType> columnTypes = tableInfo.getTableColumnTypes();
      DatabaseColumnType databaseColumnType = columnTypes.get(i);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
        try {
          Integer.parseInt(columnNewValue);
        } catch (NumberFormatException exception) {
          if (columnNewValue.equalsIgnoreCase(Constants.IS_NULL)) {
            contentValues.putNull(textView.getText().toString());
            continue;
          }
          String toastMessage = Utils.getString(this,
              R.string.com_awesomedroidapps_inappstoragereader_table_wrong_arguments,
              textView.getText().toString());
          Utils.showLongToast(this, toastMessage);
          return contentValues;
        }
      } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT) {
        try {
          Float.parseFloat(columnNewValue);
        } catch (NumberFormatException exception) {
          if (columnNewValue.equals("is null")) {
            contentValues.putNull(textView.getText().toString());
            continue;
          }
          String toastMessage = Utils.getString(this,
              R.string.com_awesomedroidapps_inappstoragereader_table_wrong_arguments,
              textView.getText().toString());
          Utils.showLongToast(this, toastMessage);
          return contentValues;
        }
      }

      contentValues = AppDatabaseHelper.getContentValues(tableInfo.getTableColumnNames(),
          tableInfo.getTableColumnTypes(), i, columnNewValue, contentValues);

      columnNamesBuilder.append(textView.getText().toString());
      columnNamesBuilder.append(Constants.COMMA);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT) {
        columnValuesBuilder.append(Constants.INVERTED_COMMA);
      }
      columnValuesBuilder.append(columnNewValue);
      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT) {
        columnValuesBuilder.append(Constants.INVERTED_COMMA);
      }
      columnValuesBuilder.append(Constants.COMMA);
    }

    String columnNames = columnNamesBuilder.toString();
    String columnValues = columnValuesBuilder.toString();

    if (columnNames.endsWith(Constants.COMMA)) {
      columnNames = columnNames.substring(0, columnNames.lastIndexOf(Constants.COMMA));
    }
    if (columnValues.endsWith(Constants.COMMA)) {
      columnValues = columnValues.substring(0, columnValues.lastIndexOf(Constants.COMMA));
    }

    columnNames = columnNames + Constants.CLOSING_BRACKET;
    columnValues = columnValues + Constants.CLOSING_BRACKET;

    StringBuilder insertQuery = new StringBuilder();
    insertQuery.append(columnNames);
    insertQuery.append(Constants.SPACE);
    insertQuery.append(Constants.VALUES);
    insertQuery.append(Constants.SPACE);
    insertQuery.append(columnValues);

    Intent intent = new Intent();
    intent.putExtra(Constants.BUNDLE_CONTENT_VALUES, contentValues);
    intent.putExtra(Constants.INSERT_CLAUSE, insertQuery.toString());
    setResult(RESULT_OK, intent);
    finish();
    return contentValues;
  }

  private ContentValues getContentValuesForUpdateQuery() {

    ContentValues contentValues = new ContentValues();
    StringBuilder queryBuilder = new StringBuilder();


    List<DatabaseColumnType> columnTypes = tableInfo.getTableColumnTypes();
    for (int i = 0; i < whereClauseContainer.getChildCount(); i++) {
      RelativeLayout listViewItem = (RelativeLayout) whereClauseContainer.getChildAt(i);
      EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
      TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
      String columnValue = editText.getText().toString();
      if (Constants.EMPTY_STRING.equals(columnValue)) {
        continue;
      }

      DatabaseColumnType databaseColumnType = columnTypes.get(i);

      String columnName = textView.getText().toString();
      try {
        if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
          Integer.parseInt(columnValue);
        } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT) {
          Float.parseFloat(columnValue);
        }
      } catch (Exception e) {
        if (!columnValue.equalsIgnoreCase(Constants.IS_NULL)) {
          String toastMessage = Utils.getString(this,
              R.string.com_awesomedroidapps_inappstoragereader_table_wrong_arguments,
              columnName);
          Utils.showLongToast(this, toastMessage);
          return contentValues;
        }
      }

      contentValues = AppDatabaseHelper.getContentValues(tableInfo.getTableColumnNames(), tableInfo
          .getTableColumnTypes(), i, columnValue, contentValues);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT &&
          editText.isEnabled()) {
        columnValue = Constants.INVERTED_COMMA + columnValue + Constants.INVERTED_COMMA;
      }

      queryBuilder.append(columnName);
      queryBuilder.append(Constants.EQUAL);
      queryBuilder.append(columnValue);
      queryBuilder.append(Constants.SPACE);
      queryBuilder.append(Constants.AND);
      queryBuilder.append(Constants.SPACE);
    }

    String str = queryBuilder.toString();
    if (str.endsWith(Constants.SPACE + Constants.AND + Constants.SPACE)) {
      str = str.substring(0, str.lastIndexOf(Constants.SPACE + Constants.AND + Constants.SPACE));
    }
    Intent intent = new Intent();
    intent.putExtra(Constants.BUNDLE_CONTENT_VALUES, contentValues);
    intent.putExtra(Constants.SET_CLAUSE, str);
    setResult(RESULT_OK, intent);
    finish();
    return contentValues;
  }

  /**
   * A helper method for returning the where Clause query.
   */
  private void getWhereClauseQuery() {
    String str = null;
    try {
      str = getWhereClauseQueryString();
    } catch (Exception e) {
      String toastMessage = Utils.getString(this, R.string
          .com_awesomedroidapps_inappstoragereader_generic_error);
      Utils.showLongToast(this, toastMessage);
      e.printStackTrace();
      finish();
    }

    if (Constants.INVALID_STRING.equalsIgnoreCase(str)) {
      return;
    }

    if (Utils.isEmpty(str)) {
      finish();
      return;
    }

    Intent intent = new Intent();
    intent.putExtra(Constants.BUNDLE_WHERE_CLAUSE, str);
    setResult(RESULT_OK, intent);
    finish();
  }

  @Nullable
  private String getWhereClauseQueryString() throws Exception {

    StringBuilder whereClauseQueryBuilder = new StringBuilder();
    List<DatabaseColumnType> columnTypes = tableInfo.getTableColumnTypes();

    for (int i = 0; i < whereClauseContainer.getChildCount(); i++) {
      RelativeLayout listViewItem = (RelativeLayout) whereClauseContainer.getChildAt(i);
      EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
      TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
      String columnValue = editText.getText().toString();
      if (Constants.EMPTY_STRING.equals(columnValue)) {
        continue;
      }

      DatabaseColumnType databaseColumnType = columnTypes.get(i);

      String columnName = textView.getText().toString();

      try {
        if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
          Integer.parseInt(columnValue);
        } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT) {
          Float.parseFloat(columnValue);
        } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT &&
            editText.isEnabled()) {
          columnValue = Constants.INVERTED_COMMA + columnValue + Constants.INVERTED_COMMA;
        }
      } catch (NumberFormatException e) {
        if (!columnValue.equalsIgnoreCase(Constants.IS_NULL)) {
          String toastMessage = Utils.getString(this,
              R.string.com_awesomedroidapps_inappstoragereader_table_wrong_arguments,
              columnName);
          Utils.showLongToast(this, toastMessage);
          return Constants.INVALID_STRING;
        }
      }

      whereClauseQueryBuilder.append(columnName);

      if (columnValue.equalsIgnoreCase(Constants.IS_NULL)) {
        whereClauseQueryBuilder.append(Constants.SPACE);
      } else {
        whereClauseQueryBuilder.append(Constants.EQUAL);
      }

      whereClauseQueryBuilder.append(columnValue);

      whereClauseQueryBuilder.append(Constants.SPACE);
      whereClauseQueryBuilder.append(Constants.AND);
      whereClauseQueryBuilder.append(Constants.SPACE);
    }
    String str = whereClauseQueryBuilder.toString();
    if (str.endsWith(Constants.SPACE + Constants.AND + Constants.SPACE)) {
      str = str.substring(0, str.lastIndexOf(Constants.SPACE + Constants.AND + Constants.SPACE));
    }
    return str;
  }
}
