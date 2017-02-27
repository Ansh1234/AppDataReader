package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.awesomedroidapps.inappstoragereader.R;

/**
 * Created by anshul on 27/2/17.
 */

public class QueryDatabaseActivity extends AppCompatActivity implements
    View.OnClickListener {

  private EditText queryDatabaseEditText;
  private Button submitQueryButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_query_database);
    queryDatabaseEditText = (EditText) findViewById(R.id
        .com_awesomedroidapps_inappstoragereader_query_editText);
    submitQueryButton = (Button) findViewById(R.id.com_awesomedroidapps_inappstoragereader_submit);
    submitQueryButton.setOnClickListener(this);
  }

  @Override
  public void onStart() {
    super.onStart();
  }


  @Override
  public void onClick(View v) {

  }
}
