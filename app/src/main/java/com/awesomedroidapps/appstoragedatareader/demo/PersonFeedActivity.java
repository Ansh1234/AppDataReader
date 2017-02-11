package com.awesomedroidapps.appstoragedatareader.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.awesomedroidapps.appstoragedatareader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.appstoragedatareader.demo.entity.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonFeedActivity extends AppCompatActivity {

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
  }

  @OnClick(R.id.submit)
  public void submit(){
    String nameStr = name.getText().toString();
    String surNameStr = surName.getText().toString();

    if(Utils.isValid(nameStr,surNameStr)){
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
