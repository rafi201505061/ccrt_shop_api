package com.ccrt.onlineshop.model.request;

public class UserPasswordResetRequestModel {
  private String passwordResetToken;
  private String password;

  public String getPasswordResetToken() {
    return passwordResetToken;
  }

  public void setPasswordResetToken(String passwordResetToken) {
    this.passwordResetToken = passwordResetToken;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
