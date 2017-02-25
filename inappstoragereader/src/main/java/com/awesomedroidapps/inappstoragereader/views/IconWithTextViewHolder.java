package com.awesomedroidapps.inappstoragereader.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.StorageType;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 15/1/17.
 */

public class IconWithTextViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView itemName;
  private final RelativeLayout itemDatabaseContainer;
  private final WeakReference<Activity> activity;
  private AppDataStorageItem appDataStorageItem;
  private final ImageView itemIcon;

  public IconWithTextViewHolder(WeakReference<Activity> activity, View itemView) {
    super(itemView);
    this.activity = activity;
    this.itemName = (TextView) itemView.findViewById(R.id.text_item);
    this.itemDatabaseContainer = (RelativeLayout) itemView.findViewById(R.id.item_container);
    this.itemDatabaseContainer.setOnClickListener(this);
    this.itemIcon = (ImageView) itemView.findViewById(R.id.icon_item);
  }

  public void updateDatabaseItem(AppDataStorageItem appDataStorageItem) {
    this.appDataStorageItem = appDataStorageItem;
    String databaseName = appDataStorageItem.getStorageName();
    itemName.setText(databaseName);

    StorageType storageType = appDataStorageItem.getStorageType();
    switch (storageType) {
      case SHARED_PREFERENCE:
        itemIcon.setImageResource(R.drawable.com_awesomedroidapps_inappstoragereader_sharedpreferences);
        break;
      case DATABASE:
        itemIcon.setImageResource(R.drawable.com_awesomedroidapps_inappstoragereader_database);
        break;
      case TABLE:
        itemIcon.setImageResource(R.drawable.com_awesomedroidapps_inappstoragereader_tables);
    }
  }

  @Override
  public void onClick(View v) {
    if (activity == null) {
      return;
    }

    if (appDataStorageItem.getStorageType() == StorageType.DATABASE) {
      Intent intent = new Intent(activity.get(), TableListActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString(Constants.BUNDLE_DATABASE_NAME, appDataStorageItem.getStorageName());
      intent.putExtras(bundle);
      activity.get().startActivity(intent);
    } else if (appDataStorageItem.getStorageType() == StorageType.SHARED_PREFERENCE) {
      Intent intent = new Intent(activity.get(), SharedPreferencesActivity.class);
      activity.get().startActivity(intent);
    }
  }
}
