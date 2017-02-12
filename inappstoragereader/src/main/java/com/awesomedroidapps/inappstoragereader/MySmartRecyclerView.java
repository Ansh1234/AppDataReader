package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by anshul on 12/2/17.
 */

public class MySmartRecyclerView extends RecyclerView {

  public int computedWidth = 3000;

  public MySmartRecyclerView(Context context) {
    super(context);
  }

  public MySmartRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MySmartRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public boolean canScrollHorizontally(int direction) {
    return false;
  }

  @Override
  public int getMinimumWidth() {
    return computedWidth;
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    super.onMeasure(widthSpec, heightSpec);
    setMeasuredDimension(computedWidth, getMeasuredHeight());
  }

  @Override
  protected int getSuggestedMinimumWidth() {
    return computedWidth;
  }
}
