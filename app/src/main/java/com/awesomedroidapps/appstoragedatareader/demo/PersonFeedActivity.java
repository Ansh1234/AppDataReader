package com.awesomedroidapps.appstoragedatareader.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.awesomedroidapps.appstoragedatareader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.appstoragedatareader.demo.entity.Person;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonFeedActivity extends Activity {

  @BindView(R.id.name)
  EditText name;
  @BindView(R.id.surname)
  EditText surName;
  @BindView(R.id.submit)
  Button submit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    saveInSharedPreferences();
  }

  private void saveInSharedPreferences() {

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context
        .MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("name", "anshul");
    editor.putString("age", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref2", Context
        .MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name", "anshul");
    editor.putString("age", "24");
    editor.putString("NULL", null);
    editor.putStringSet("NULL SET", null);
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_APPEND);
    editor = sharedPreferences.edit();
    editor.putString("name1",
        "anshul FlowR is a wrapper class around the Fragment Manager. It’s mainly used to navigate between different fragments easily while providing a wide range of functionality. The following are the functionalities provided by the Flowr:");
    editor.putString("age2", "24");
    Set set = new HashSet<>();
    set.add("set1");
    set.add("set2");
    set.add("set3");
    editor.putStringSet("SET", set);
    editor.commit();

    sharedPreferences =
        getSharedPreferences("MySharedPref3", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
    editor = sharedPreferences.edit();
    editor.putString("name2", "anshul");
    editor.putString("age3", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_NO_LOCALIZED_COLLATORS);
    editor = sharedPreferences.edit();
    editor.putString("name3", "anshul");
    editor.putString("age4", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_MULTI_PROCESS);
    editor = sharedPreferences.edit();
    editor.putString("name4", "anshul");
    editor.putString("age5", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_WORLD_READABLE);
    editor = sharedPreferences.edit();
    editor.putString("name5", "anshul");
    editor.putString("age6", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_WORLD_WRITEABLE);
    editor = sharedPreferences.edit();
    editor.putString("name6", "anshul");
    editor.putString("age7", "24");
    editor.commit();


  }

  @OnClick(R.id.submit)
  public void submit() {
    String nameStr = name.getText().toString();
    String surNameStr = surName.getText().toString();

    if (Utils.isValid(nameStr, surNameStr)) {
      submit.setEnabled(true);
      Person person = new Person();
      person.setName(nameStr);
      person.setSurName(surNameStr);
      DataReaderSqliteOpenHelper helper = new DataReaderSqliteOpenHelper(this);
      //  helper.getWritableDatabase();
      helper.insert(person);
    }
  }
}
