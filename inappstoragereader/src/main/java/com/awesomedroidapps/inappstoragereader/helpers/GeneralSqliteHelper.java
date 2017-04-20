package com.awesomedroidapps.inappstoragereader.helpers;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by anshul on 21/04/17.
 */

public class GeneralSqliteHelper {

  public static List<String> getListFromString(String name) {
    if (Utils.isEmpty(name)) {
      return null;
    }
    String[] names = name.split(Constants.COMMA);
    return Arrays.asList(names);
  }

  public static boolean[] getCheckedArray(String[] allColumns, List<String> selectedColumns){
    boolean[] previouslySelectedColumns = new boolean[allColumns.length];
    for(int i=0;i<allColumns.length;i++){
      if(selectedColumns.contains(allColumns[i])){
        previouslySelectedColumns[i]=true;
      }else{
        previouslySelectedColumns[i]=false;
      }
    }
    return previouslySelectedColumns;
  }
}
