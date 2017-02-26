package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.Collection;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class Utils {

  public static boolean isEmpty(Collection collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isEmpty(String string) {
    return string == null || string.equals("");
  }

  public static int getDimensionInInteger(Context context, int resourceId) {
    if (context == null) {
      return 0;
    }
    return (int) context.getResources().getDimension(resourceId);
  }

  public static void setTextAppearance(TextView textView, int style) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      textView.setTextAppearance(style);
    } else {
      textView.setTextAppearance(textView.getContext(), style);
    }
  }

  /**
   * Sets the title of the action bar with the number of shared preferences items.
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
}
