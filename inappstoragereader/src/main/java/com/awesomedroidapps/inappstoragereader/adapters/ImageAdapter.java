package com.awesomedroidapps.inappstoragereader.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
  private ArrayList<ArrayList<String>> arrayLists;

  public ImageAdapter(ArrayList<ArrayList<String>> arrayLists) {
    this.arrayLists = arrayLists;
  }

  public int getCount() {
    return arrayLists.size();
  }

  public Object getItem(int position) {
    return null;
  }

  public long getItemId(int position) {
    return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    TextView textView;
    if (convertView == null) {
      // if it's not recycled, initialize some attributes
      textView = new TextView(convertView.getContext());
      textView.setLayoutParams(new GridView.LayoutParams(85, 85));
      textView.setPadding(8, 8, 8, 8);
    } else {
      textView = (TextView) convertView;
    }


    return textView;
  }

}