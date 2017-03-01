package com.awesomedroidapps.inappstoragereader.views;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public interface TableDataView {

  void onDataFetched(TableDataResponse tableDataResponse);
}
