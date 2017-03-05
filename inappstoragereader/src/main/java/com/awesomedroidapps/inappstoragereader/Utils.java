package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Utility class
 * Created by anshul on 11/2/17.
 */

public class Utils {

  /**
   * A Helper function to determine whether a collection is empty or not.
   *
   * @param collection
   * @return
   */
  public static boolean isEmpty(Collection collection) {
    return collection == null || collection.isEmpty();
  }

  /**
   * A helper unction to determine whether a string is empty or not
   *
   * @param string
   * @return
   */
  public static boolean isEmpty(String string) {
    return string == null || string.equals("");
  }

  /**
   * A helper function to return the dimensions in Integer.
   *
   * @param context
   * @param resourceId
   * @return
   */
  public static int getDimensionInInteger(Context context, int resourceId) {
    if (context == null) {
      return 0;
    }
    return (int) context.getResources().getDimension(resourceId);
  }

  /**
   * A helper method or progrmatically setting the style to a textview.
   *
   * @param textView
   * @param style
   */
  public static void setTextAppearance(TextView textView, int style) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      textView.setTextAppearance(style);
    } else {
      textView.setTextAppearance(textView.getContext(), style);
    }
  }

  /**
   * Sets the title of the action bar with the number of  items.
   */
  public static void setActionBarTitle(ActionBar actionBar, String title, int size) {
    if (actionBar == null) {
      return;
    }

    if (size == 0) {
      actionBar.setTitle(title);
      return;
    }

    StringBuilder stringBuilder = new StringBuilder(title);
    stringBuilder.append(Constants.SPACE);
    stringBuilder.append(Constants.OPENING_BRACKET);
    stringBuilder.append(size);
    stringBuilder.append(Constants.CLOSING_BRACKET);
    actionBar.setTitle(stringBuilder.toString());
  }

  /**
   * This method is used to return the width of the recyclerview based on individual column's width.
   *
   * @param tableDataColumnWidthList
   * @return
   */
  public static int getTableWidth(List<Integer> tableDataColumnWidthList) {
    int width = 0;
    if (Utils.isEmpty(tableDataColumnWidthList)) {
      return width;
    }
    for (Integer i : tableDataColumnWidthList) {
      width = width + i;
    }
    return width;
  }
}
