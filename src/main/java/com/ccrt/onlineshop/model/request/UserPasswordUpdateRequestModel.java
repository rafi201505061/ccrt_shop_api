package com.ccrt.onlineshop.model.request;

public class UserPasswordUpdateRequestModel {
  private String password;
  private String prevPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPrevPassword() {
    return prevPassword;
  }

  public void setPrevPassword(String prevPassword) {
    this.prevPassword = prevPassword;
  }
}
