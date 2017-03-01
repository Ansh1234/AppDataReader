package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.ErrorType;

/**
 * Created by anshul on 25/2/17.
 *
 * Interface for propogating messages.
 */

public interface ErrorMessageInterface {

  public void handleError(ErrorType errorType);
}
