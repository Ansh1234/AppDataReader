package com.awesomedroidapps.inappstoragereader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anshul on 25/2/17.
 */

public class ErrorMessageHandler {

  public void handleError(ErrorType errorType, View view){
    TextView textView  = (TextView) view.findViewById(R.id.error_text);
    ImageView imageView = (ImageView) view.findViewById(R.id.error_icon);

    textView.setText(errorType.getErrorMessage());
    switch (errorType){
      case NO_ITEM_FOUND:
        imageView.setImageResource(R.drawable.item_not_found);
        break;
    }
  }

}
