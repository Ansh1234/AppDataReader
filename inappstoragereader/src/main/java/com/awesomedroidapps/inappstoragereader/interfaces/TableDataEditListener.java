package com.awesomedroidapps.inappstoragereader.interfaces;

import java.util.List;

/**
 * Created by anshul on 27/04/17.
 */

public interface TableDataEditListener {

  void onTableDataEdited(String newValue, int columnIndex, List<String> currentColumnValues);

}
