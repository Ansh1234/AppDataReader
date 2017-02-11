package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class DatabaseItemViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView itemName;
  private final CardView itemDatabaseContainer;
  private final WeakReference<Activity> activity;
  private  String databaseName;

  public DatabaseItemViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.activity = activity;
    this.itemName = (TextView) itemView.findViewById(R.id.text_database_name);
    this.itemDatabaseContainer = (CardView) itemView.findViewById(R.id.item_database_container);
    this.itemDatabaseContainer.setOnClickListener(this);
  }

  public void updateDatabaseItem(String databaseName) {
    this.databaseName = databaseName;
    itemName.setText(databaseName);
  }

  @Override
  public void onClick(View v) {
    if (activity == null) {
      return;
    }
    Intent intent = new Intent(activity.get(), TableListActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, databaseName);
    intent.putExtras(bundle);
    activity.get().startActivity(intent);
  }
}
