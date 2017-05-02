package com.awesomedroidapps.inappstoragereader.views;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.entities.TableInfo;
import com.awesomedroidapps.inappstoragereader.utilities.AppDatabaseHelper;

import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereClauseActivity extends AppCompatActivity implements View.OnClickListener {

  private Button whereClauseSubmitButton;
  private LinearLayout whereClauseContainer;
  private List list, columnNames;
  private List<DatabaseColumnType> columnTypes;
  private TableInfo tableInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_where_clause);
    whereClauseSubmitButton = (Button) findViewById(R.id.where_clause_button_done);
    whereClauseSubmitButton.setOnClickListener(this);

    Bundle bundle = getIntent().getExtras();
    tableInfo = (TableInfo) bundle.getSerializable(Constants.BUNDLE_TABLE_INFO);

    String databaseName = tableInfo.getDatabaseName();
    String tableName = tableInfo.getTableName();
    columnNames = tableInfo.getTableColumnNames();
    columnTypes = tableInfo.getTableColumnTypes();

    list = SqliteDatabaseReader.getColumnTypes(this, databaseName, tableName);
    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    whereClauseContainer = (LinearLayout) findViewById(R.id.where_clause_container);
    fillWhereClauseData(columnNames, layoutInflater);
  }

  private void fillWhereClauseData(List<String> columnNames, LayoutInflater layoutInflater) {
    if (columnNames == null || columnNames.size() == 0) {
      return;
    }
    for (int i = 0; i < columnNames.size(); i++) {
      View view = layoutInflater.inflate(R.layout
          .com_awesomedroidapps_inappstoragereader_columns_dropdown, null);
      TextView textView = (TextView) view.findViewById(R.id.column_name);
      textView.setText(columnNames.get(i));
      whereClauseContainer.addView(view);
    }
  }

  @Override
  public void onClick(View v) {
    if (v == whereClauseSubmitButton) {
      int requestCode = getIntent().getExtras().getInt(Constants.BUNDLE_REQUEST_CODE);
      if (requestCode == Constants.REQUEST_CODE_SET_CLAUSE) {
        getContentValues();
        return;
      } else {
        getWhereClauseQuery();
      }
    }
  }

  private ContentValues getContentValues() {

    ContentValues contentValues = new ContentValues();
    StringBuilder queryBuilder = new StringBuilder();


    for (int i = 0; i < whereClauseContainer.getChildCount(); i++) {
      RelativeLayout listViewItem = (RelativeLayout) whereClauseContainer.getChildAt(i);
      EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
      TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
      if (Constants.EMPTY_STRING.equals(editText.getText().toString())) {
        continue;
      }

      DatabaseColumnType databaseColumnType = columnTypes.get(i);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
        try {
          Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException exception) {
          String toastMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_table_wrong_arguments, textView.getText()
              .toString());
          Utils.showLongToast(this, toastMessage);
          return contentValues;
        }
      } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT) {
        try {
          Float.parseFloat(editText.getText().toString());
        } catch (NumberFormatException exception) {
          String toastMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_table_wrong_arguments, textView.getText()
              .toString());
          Utils.showLongToast(this, toastMessage);
          return contentValues;
        }
      }

      contentValues = AppDatabaseHelper.getContentValues(tableInfo.getTableColumnNames(), tableInfo
          .getTableColumnTypes(), i, editText.getText().toString(), contentValues);
      queryBuilder.append(textView.getText().toString());
      queryBuilder.append(Constants.EQUAL);
      if (DatabaseColumnType.FIELD_TYPE_TEXT == databaseColumnType) {
        queryBuilder.append(Constants.INVERTED_COMMA);
      }
      queryBuilder.append(editText.getText().toString());
      if (DatabaseColumnType.FIELD_TYPE_TEXT == databaseColumnType) {
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
    intent.putExtra(Constants.BUNDLE_CONTENT_VALUES, contentValues);
    intent.putExtra(Constants.SET_CLAUSE, str);
    setResult(RESULT_OK, intent);
    finish();
    return contentValues;
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

      DatabaseColumnType databaseColumnType = columnTypes.get(i);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
        try {
          Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException exception) {
          String toastMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_table_wrong_arguments, textView.getText()
              .toString());
          Utils.showLongToast(this, toastMessage);
          return;
        }
      } else if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT) {
        try {
          Float.parseFloat(editText.getText().toString());
        } catch (NumberFormatException exception) {
          String toastMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_table_wrong_arguments, textView.getText()
              .toString());
          Utils.showLongToast(this, toastMessage);
          return;
        }
      }
      queryBuilder.append(textView.getText().toString());
      queryBuilder.append(Constants.EQUAL);
      if (DatabaseColumnType.FIELD_TYPE_TEXT == databaseColumnType) {
        queryBuilder.append(Constants.INVERTED_COMMA);
      }
      queryBuilder.append(editText.getText().toString());
      if (DatabaseColumnType.FIELD_TYPE_TEXT == databaseColumnType) {
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
