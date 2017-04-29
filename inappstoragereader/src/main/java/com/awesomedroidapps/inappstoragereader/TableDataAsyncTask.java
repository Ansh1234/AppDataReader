package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.TableDataView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anshul on 1/3/17.
 * An async task for fetching all the table data.
 */

public class TableDataAsyncTask extends AsyncTask<String, Void, TableDataResponse> {

  private final WeakReference<Activity> activtyWeakReference;
  private final TableDataView tableDataView;

  public TableDataAsyncTask(WeakReference activtyWeakReference,
                            TableDataView tableDataView) {
    this.activtyWeakReference = activtyWeakReference;
    this.tableDataView = tableDataView;
  }

  @Override
  protected TableDataResponse doInBackground(String... params) {
    if (activtyWeakReference.get() == null || params == null || params.length != 2) {
      return null;
    }

    TableDataResponse tableDataResponse = new TableDataResponse();

    String databaseName = params[0];
    String tableName = params[1];

    //Get List of columns width
    List<Integer> tableDataColumnWidthList = SqliteDatabaseReader.getTableDataColumnWidth
        (activtyWeakReference.get(), databaseName, tableName);
    tableDataResponse.setRecyclerViewColumnsWidth(tableDataColumnWidthList);
    List<Integer> primaryKeyList = SqliteDatabaseReader.getTableDataPrimaryKey(activtyWeakReference
            .get(), databaseName,tableName);
    tableDataResponse.setPrimaryKeyList(primaryKeyList);

    //Get the width
    int recyclerViewWidth = Utils.getTableWidth(tableDataColumnWidthList);
    tableDataResponse.setRecyclerViewWidth(recyclerViewWidth);

    //Get the actual data.
    List<List<String>> tableData = SqliteDatabaseReader.getAllTableData
        (activtyWeakReference.get(), databaseName, tableName);
    String[] columnNames = SqliteDatabaseReader.getColumnNames(activtyWeakReference.get(),
        databaseName,tableName);
    tableDataResponse.setColumnNames(Arrays.asList(columnNames));
    List<DatabaseColumnType> columnTypes = SqliteDatabaseReader.getTableDataColumnTypes(activtyWeakReference.get(),
        databaseName,tableName);
    tableDataResponse.setColumnTypes(columnTypes);
    tableDataResponse.setTableData(tableData);
    return tableDataResponse;
  }


  protected void onPostExecute(TableDataResponse tableDataResponse) {
    if (tableDataView != null && activtyWeakReference.get() != null) {
      tableDataView.onDataFetched(tableDataResponse);
    }
  }
}
