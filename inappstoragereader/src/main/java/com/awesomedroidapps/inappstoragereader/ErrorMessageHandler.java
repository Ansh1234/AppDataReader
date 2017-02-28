package com.awesomedroidapps.inappstoragereader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A helper class for displaying errors based on the error type.
 * Created by anshul on 25/2/17.
 */

public class ErrorMessageHandler {

  /**
   * A helper method for handling the errors.
   * @param errorType
   * @param view
   */
  public void handleError(ErrorType errorType, View view) {
    if (errorType == null || view == null) {
      return;
    }

    TextView textView = (TextView) view.findViewById(R.id.error_text);
    ImageView imageView = (ImageView) view.findViewById(R.id.error_icon);

    textView.setText(errorType.getErrorMessage());

    switch (errorType) {
      case NO_ITEM_FOUND:
        imageView.setImageResource(
            R.drawable.com_awesomedroidapps_inappstoragereader_item_not_found);
        break;
    }
  }

}
