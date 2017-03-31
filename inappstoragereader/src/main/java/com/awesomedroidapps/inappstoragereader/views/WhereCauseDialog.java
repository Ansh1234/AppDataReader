package com.awesomedroidapps.inappstoragereader.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableColumnsAdapter;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;
import com.awesomedroidapps.inappstoragereader.interfaces.WhereQuerySelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 31/03/17.
 */

public class WhereCauseDialog extends DialogFragment implements WhereQuerySelectListener{

  private String[] columns;
  private RecyclerView dialogRecyclerView;
  private EditText whereClauseQuery;

  public static WhereCauseDialog newInstance(String[] columns) {
    WhereCauseDialog tableColumnsDialog = new WhereCauseDialog();
    tableColumnsDialog.setColumns(columns);
    return tableColumnsDialog;
  }

  public void setColumns(String[] columns) {
    this.columns = columns;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final ArrayAdapter<String> arrayAdapterItems = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_expandable_list_item_1, columns);

    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
    View dialogView = inflater.inflate(R.layout.com_awesomedroidapps_inappstoragereader_where_clause, null);
    dialogRecyclerView = (RecyclerView) dialogView.findViewById(R.id.dialog_recyclerview);
    TableColumnsAdapter tableColumnsAdapter = new TableColumnsAdapter(columns,this);
    dialogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    dialogRecyclerView.setAdapter(tableColumnsAdapter);
    whereClauseQuery = (EditText) dialogView.findViewById(R.id.where_clause_query);
    builder.setView(dialogView);

    return builder.create();
  }

  @Override
  public void onWhereClauseQuerySelected() {
    dialogRecyclerView.setVisibility(View.GONE);
    whereClauseQuery.setVisibility(View.VISIBLE);
  }
}
