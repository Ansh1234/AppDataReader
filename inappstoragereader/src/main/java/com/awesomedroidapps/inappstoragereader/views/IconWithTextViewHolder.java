package com.awesomedroidapps.inappstoragereader.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.interfaces.AppStorageItemClickListener;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.StorageType;

/**
 * Created by anshul on 15/1/17.
 */

public class IconWithTextViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final TextView itemName;
  private final RelativeLayout itemDatabaseContainer;
  private final AppStorageItemClickListener appStorageItemClickListener;
  private AppDataStorageItem appDataStorageItem;
  private final ImageView itemIcon;

  public IconWithTextViewHolder(AppStorageItemClickListener appStorageItemClickListener,
                                View itemView) {
    super(itemView);
    this.appStorageItemClickListener = appStorageItemClickListener;
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
        itemIcon.setImageResource(
            R.drawable.com_awesomedroidapps_inappstoragereader_sharedpreferences);
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
    if (appStorageItemClickListener != null) {
      appStorageItemClickListener.onItemClicked(appDataStorageItem);
    }
  }
}
