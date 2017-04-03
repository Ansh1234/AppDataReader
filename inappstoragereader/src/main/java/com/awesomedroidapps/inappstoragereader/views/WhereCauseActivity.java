package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.TableColumnsAdapter;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereCauseActivity extends AppCompatActivity implements WhereQuerySelectListener {

  private ListView dialogRecyclerView;
  private EditText whereClauseQuery;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_where_clause);
    whereClauseQuery = (EditText) findViewById(R.id.where_clause_query);
    dialogRecyclerView  = (ListView) findViewById(R.id.dialog_recyclerview);
    Bundle bundle = getIntent().getExtras();
    String databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    String tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
    String[] columnNames = SqliteDatabaseReader.getColumnNames(this, databaseName, tableName);
    TableColumnsAdapter tableColumnsAdapter = new TableColumnsAdapter(this,0,columnNames);
    dialogRecyclerView.setAdapter(tableColumnsAdapter);
  }

  @Override
  public void onWhereClauseQuerySelected() {
    dialogRecyclerView.setVisibility(View.GONE);
    whereClauseQuery.setVisibility(View.VISIBLE);
  }
}
