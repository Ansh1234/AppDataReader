package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 27/03/17.
 */

public enum  DatabaseQueryCommands {
  SELECT("SELECT"),
  UPDATE("UPDATE"),
  DELETE("DELETE"),
  INSERT("INSERT");


  private String command;

  DatabaseQueryCommands(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public static DatabaseQueryCommands getCommand(String command) {
    for (DatabaseQueryCommands commands : DatabaseQueryCommands.values()) {
      if (commands.getCommand().equals(command)) {
        return commands;
      }
    }
    return null;
  }
}
