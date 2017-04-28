package com.awesomedroidapps.inappstoragereader.views;

/**
 * Created by anshul on 25/04/17.
 */

public interface QueryDatabaseView {

  void onSelectCommandSelected();

  void onUpdateCommandSelected();

  void onDeleteCommandSelected();

  void onInsertCommandSelected();

  void onRawQueryCommandSelected();
}
