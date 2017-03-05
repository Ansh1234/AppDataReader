package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * A class which extends RecyclerView for dynamically changing the width.
 * Created by anshul on 12/2/17.
 */

public class AppStorageDataRecyclerView extends RecyclerView {

  public int recyclerViewWidth;

  public AppStorageDataRecyclerView(Context context) {
    super(context);
  }

  public AppStorageDataRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AppStorageDataRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setRecyclerViewWidth(int recyclerViewWidth) {
    this.recyclerViewWidth = recyclerViewWidth;
  }

  @Override
  public boolean canScrollHorizontally(int direction) {
    return false;
  }

  @Override
  public int getMinimumWidth() {
    return recyclerViewWidth;
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    super.onMeasure(widthSpec, heightSpec);
    setMeasuredDimension(recyclerViewWidth, getMeasuredHeight());
  }

  @Override
  protected int getSuggestedMinimumWidth() {
    return recyclerViewWidth;
  }
}
