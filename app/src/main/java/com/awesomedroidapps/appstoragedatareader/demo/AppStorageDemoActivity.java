package com.awesomedroidapps.appstoragedatareader.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.awesomedroidapps.appstoragedatareader.demo.entity.PersonInfo;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppStorageDemoActivity extends AppCompatActivity {

  //Views in the database
  @BindView(R.id.database_container)
  LinearLayout databaseContainer;
  @BindView(R.id.first_name)
  EditText firstName;
  @BindView(R.id.last_name)
  EditText lastName;
  @BindView(R.id.address)
  EditText address;
  @BindView(R.id.submit)
  Button submit;

  //Views in the shared preference.
  @BindView(R.id.shared_preference_container)
  LinearLayout sharedPreferenceContainer;
  @BindView(R.id.shared_preference_key)
  EditText sharedPreferenceKey;
  @BindView(R.id.shared_preference_value)
  EditText sharedPreferenceValue;
  @BindView(R.id.shared_preference_spinner)
  Spinner sharedPreferenceSpinner;

  private int currentView = Constants.DATABASE_VIEW;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appstorage_demo);
    ButterKnife.bind(this);

    //Initially show Table View.
    showTableView();

    fillDefaultValuesInTable();
    fillDefaultValuesInSharedPreferences();

    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
        getSpinnerArray());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sharedPreferenceSpinner.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.appstorage_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.menu_table:
        showTableView();
        break;

      case R.id.menu_shared_preference:
        showSharedPreferencesView();
        break;

      case R.id.menu_refresh:
        refreshUI();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showTableView() {
    currentView = Constants.DATABASE_VIEW;
    databaseContainer.setVisibility(View.VISIBLE);
    sharedPreferenceContainer.setVisibility(View.GONE);
  }

  private void showSharedPreferencesView() {
    currentView = Constants.SHARED_PREFERENCE_VIEW;
    sharedPreferenceContainer.setVisibility(View.VISIBLE);
    databaseContainer.setVisibility(View.GONE);
  }

  private void refreshUI() {
    switch (currentView) {
      case Constants.DATABASE_VIEW:
        firstName.setText(Constants.EMPTY_STRING);
        lastName.setText(Constants.EMPTY_STRING);
        address.setText(Constants.EMPTY_STRING);
        break;
      case Constants.SHARED_PREFERENCE_VIEW:
        sharedPreferenceKey.setText(Constants.EMPTY_STRING);
        sharedPreferenceValue.setText(Constants.EMPTY_STRING);
        sharedPreferenceSpinner.setSelection(Constants.ZERO_INDEX);
        break;
    }
  }

  @OnClick(R.id.submit)
  public void submit() {
    switch (currentView) {
      case Constants.DATABASE_VIEW:
        enterValuesIntoDB();
        break;
      case Constants.SHARED_PREFERENCE_VIEW:
        enterValuesIntoSharedPreferences();
        break;
    }
  }

  private void enterValuesIntoDB() {
    String firstNameStr = firstName.getText().toString();
    String lastNameStr = lastName.getText().toString();
    String addressStr = address.getText().toString();

    if (Utils.isEmpty(firstNameStr) && Utils.isEmpty(lastNameStr) && Utils.isEmpty(addressStr)) {
      showToast(string(R.string.table_empty_values_error_msg));
      return;
    }

    PersonInfo personInfo = new PersonInfo();
    personInfo.setFirstName(firstNameStr);
    personInfo.setLastName(lastNameStr);
    personInfo.setAddress(addressStr);
    long insertResponse = insertIntoDatabase(personInfo);
    if (insertResponse == Constants.INVALID_INSERT_RESPONSE) {
      showToast(string(R.string.table_insert_failure));
    } else {
      showToast(string(R.string.table_insert_success));
    }
  }

  private void enterValuesIntoSharedPreferences() {

    if (!(sharedPreferenceSpinner.getSelectedItem() instanceof String)) {
      return;
    }

    SharedPreferenceDataType selectedDataType = SharedPreferenceDataType.getDataType((String)
        sharedPreferenceSpinner.getSelectedItem());

    if (selectedDataType == SharedPreferenceDataType.UNKNOWN) {
      showToast(getString(R.string.shared_preference_datatype_error));
      return;
    }

    String keyStr = sharedPreferenceKey.getText().toString();
    String valueStr = sharedPreferenceValue.getText().toString();

    if (Utils.isEmpty(keyStr) || Utils.isEmpty(valueStr)) {
      showToast(getString(R.string.shared_preference_key_value_error));
      return;
    }
    insertIntoSharedPreference(keyStr, valueStr, selectedDataType,
        Constants.PERSON_INFO_SHARED_PREFERENCES1);
    showToast(string(R.string.shared_preferences_insert_success));
  }

  private void fillDefaultValuesInTable() {
    String[] defaultFirstNames = getResources().getStringArray(R.array.defaultFirstNames);
    String[] defaultLastNames = getResources().getStringArray(R.array.defaultLastNames);
    String[] defaultAddresses = getResources().getStringArray(R.array.defaultAddresses);

    for (int i = 0; i < defaultFirstNames.length; i++) {
      PersonInfo personInfo = new PersonInfo();
      personInfo.setFirstName(defaultFirstNames[i]);
      personInfo.setLastName(defaultLastNames[i]);
      personInfo.setAddress(defaultAddresses[i]);
      insertIntoDatabase(personInfo);
    }
  }

  private void fillDefaultValuesInSharedPreferences() {
    String[] defaultKeys = getResources().getStringArray(R.array.defaultPersonInfoKeys1);
    String[] defaultValues = getResources().getStringArray(R.array.defaultPersonInfoValues1);

    for (int i = 0; i < defaultKeys.length; i++) {
      insertIntoSharedPreference(defaultKeys[i], defaultValues[i], SharedPreferenceDataType
          .STRING, Constants.PERSON_INFO_SHARED_PREFERENCES1);
    }

    defaultKeys = getResources().getStringArray(R.array.defaultPersonInfoKeys2);
    defaultValues = getResources().getStringArray(R.array.defaultPersonInfoValues2);

    for (int i = 0; i < defaultKeys.length; i++) {
      insertIntoSharedPreference(defaultKeys[i], defaultValues[i], SharedPreferenceDataType
          .STRING, Constants.PERSON_INFO_SHARED_PREFERENCES2);
    }
  }

  private long insertIntoDatabase(PersonInfo personInfo) {
    DataReaderSqliteOpenHelper helper = new DataReaderSqliteOpenHelper(this);
    return helper.insert(personInfo);
  }

  private void insertIntoSharedPreference(String key, String value, SharedPreferenceDataType
      dataType, String fileName) {
    if (Utils.isEmpty(key) || value == null) {
      return;
    }
    SharedPreferences sharedPreferences =
        getSharedPreferences(fileName, Context
            .MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    switch (dataType) {
      case STRING:
        editor.putString(key, value);
        break;
      case INT:
        try {
          int intVal = Integer.parseInt(value);
          editor.putInt(key, intVal);
        } catch (Exception e) {
          showToast(getString(R.string.shared_preferences_error_integer));
          return;
        }
        break;
    }
    editor.commit();
  }

  private String string(int resId) {
    return getResources().getString(resId);
  }

  private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  private ArrayList<String> getSpinnerArray() {
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    for (SharedPreferenceDataType sharedPreferenceDataType : SharedPreferenceDataType.values()) {
      spinnerArrayList.add(sharedPreferenceDataType.getType());
    }
    return spinnerArrayList;
  }
}
