package com.awesomedroidapps.inappstoragereader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.TableColumnsAdapter;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereCauseActivity extends AppCompatActivity implements WhereQuerySelectListener,
    View.OnClickListener {

  private ScrollView whereClauseListView;
  private EditText whereClauseQuery;
  private Button whereClauseButton;
  private LinearLayout child;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_where_clause);
    // whereClauseQuery = (EditText) findViewById(R.id.where_clause_query);
    whereClauseListView = (ScrollView) findViewById(R.id.dialog_recyclerview);
    whereClauseButton = (Button) findViewById(R.id.where_clause_button_done);

    whereClauseButton.setOnClickListener(this);
    Bundle bundle = getIntent().getExtras();
    String databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    String tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
    String[] columnNames = SqliteDatabaseReader.getColumnNames(this, databaseName, tableName);
    TableColumnsAdapter tableColumnsAdapter = new TableColumnsAdapter(this, 0, columnNames);
    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    child = (LinearLayout) findViewById(R.id.child);
    for (int i = 0; i < columnNames.length; i++) {
      View itemView = layoutInflater.inflate(R.layout
          .com_awesomedroidapps_inappstoragereader_columns_dropdown, null);
      TextView itemName = (TextView) itemView.findViewById(R.id.column_name);
      itemName.setText(columnNames[i]);
      child.addView(itemView);
    }

  }

  @Override
  public void onWhereClauseQuerySelected() {
    whereClauseListView.setVisibility(View.GONE);
    whereClauseQuery.setVisibility(View.VISIBLE);
  }

  @Override
  public void onClick(View v) {
    if (v == whereClauseButton) {
      StringBuilder queryBuilder = new StringBuilder();
      for (int i = 0; i < child.getChildCount(); i++) {
        RelativeLayout listViewItem = (RelativeLayout) child.getChildAt(i);
        EditText editText = (EditText) listViewItem.findViewById(R.id.column_value);
        TextView textView = (TextView) listViewItem.findViewById(R.id.column_name);
        if(Constants.EMPTY_STRING.equals(editText.getText().toString())){
          continue;
        }
        queryBuilder.append(textView.getText().toString());
        queryBuilder.append("='");
        queryBuilder.append(editText.getText().toString());
        queryBuilder.append("'");
        queryBuilder.append(" AND ");
      }
      String str = queryBuilder.toString();
      if(str.endsWith(" AND ")){
        str = str.substring(0,str.lastIndexOf(" AND "));
      }
      System.out.println("str is "+str);
      Intent intent = new Intent();
      intent.putExtra("whereClause",str);
      setResult(RESULT_OK,intent);
      finish();
    }
  }
}
