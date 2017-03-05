package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 *
 * Interface for propogating the response from async tasks to Activity
 */

public interface TableDataView {

  void onDataFetched(TableDataResponse tableDataResponse);
}
