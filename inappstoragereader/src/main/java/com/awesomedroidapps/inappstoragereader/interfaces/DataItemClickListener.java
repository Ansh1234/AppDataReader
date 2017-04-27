package com.awesomedroidapps.inappstoragereader.interfaces;

import java.util.List;

/**
 * Created by anshul on 26/2/17.
 *
 * Interface when a data item is clicked.
 */

public interface DataItemClickListener {
  void onDataItemClicked(String data);

  void onDataItemClicked(String data, int columnIndex, List<String> columnValues);
}
