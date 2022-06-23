package com.suresh.springbatch.model;

public class User {

  private long userId;
  private String namePrefix;
  private String firstName;
  private String lastName;

  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getNamePrefix() {
    return this.namePrefix;
  }

  public void setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User() {}

  public User(
    long userId,
    String namePrefix,
    String firstName,
    String lastName
  ) {
    this.userId = userId;
    this.namePrefix = namePrefix;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
