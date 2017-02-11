package com.awesomedroidapps.inappstoragereader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by anshul on 11/2/17.
 */

public class CustomContentProvider extends ContentProvider {

  @Override
  public boolean onCreate() {
    System.out.println("inside onCreate");
    InAppStorageSDK.getInstance().setContext(getContext());
    return false;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
    return null;
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    return null;
  }

  @Override
  public int delete(Uri uri, String s, String[] strings) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
    return 0;
  }
}
