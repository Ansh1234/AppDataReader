package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class TableItemViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView itemName;
  private final CardView itemDatabaseContainer;
  private final WeakReference<Activity> activity;
  private String tableName, databaseName;

  public TableItemViewHolder(WeakReference<Activity> activity, View itemView, String databaseName) {
    super(itemView);
    this.activity = activity;
    this.databaseName=databaseName;
    this.itemName = (TextView) itemView.findViewById(R.id.text_table_name);
    this.itemDatabaseContainer = (CardView) itemView.findViewById(R.id.item_table_container);
    this.itemDatabaseContainer.setOnClickListener(this);
  }

  public void updateTableItem(String tableName) {
    this.tableName = tableName;
    itemName.setText(tableName);
  }

  @Override
  public void onClick(View v) {
    if (activity == null) {
      return;
    }
    Intent intent = new Intent(activity.get(), TableDataActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, databaseName);
    bundle.putString(Constants.BUNDLE_TABLE_NAME,tableName);
    intent.putExtras(bundle);
    activity.get().startActivity(intent);
  }
}
