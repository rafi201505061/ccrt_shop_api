package com.ccrt.onlineshop.enums;

public enum Message {
  FIRST_NAME_NOT_VALID("You must provide a valid first name."),
  LAST_NAME_NOT_VALID("You must provide a valid last name."),
  EMAIL_NOT_VALID("You must provide a valid email address."),
  PASSWORD_NOT_VALID("Password must be at least 6 characters long."),
  FORBIDDEN("You are not allowed to perform this action."),
  BAD_REQUEST("You must provide all necessary fields."),
  WRONG_PASSWORD("You have provided wrong password."),
  STATUS_NOT_VALID("Status is not valid."),
  DUPLICATE_EMAIL("An account already exists with this email address."),
  DONATION_NOT_FOUND("Donation request couldn't be found."),
  PASSWORD_UPDATE_SUCCESSFUL("Password has been updated successfully."),
  PASSWORD_RESET_SUCCESSFUL("Password has been reset successfully."),
  PASSWORD_RESET_CODE_MISMATCH("Pass reset code didn't match."),
  OTP_CODE_EXPIRED("Otp code has expired."),
  ORDER_STATUS_ORDER_NOT_VALID("Order status must follow a valid order."),
  ORDER_NOT_FOUND("Order couldn't be found."),
  RATING_ALREADY_EXISTS("You have already rated the product."),
  RATING_NOT_FOUND("You haven't added a rating for this product."),
  OTP_CODE_MISMATCH("Otp code didn't match."),
  FILE_CREATION_ERROR("Image couldn't be uploaded."),
  TITLE_NOT_VALID("You must provide a valid title."),
  DESCRIPTION_NOT_VALID("You must provide a valid description."),
  PRICE_NOT_VALID("You must provide a positive price."),
  TOTAL_ENTITIES_NOT_VALID("You must provide a positive number as total number of entities."),
  USAGE_STATUS_NOT_VALID("You must provide if the product is user or new."),
  CATEGORY_NOT_FOUND("Category couldn't be found."),
  IMAGE_NOT_VALID("You must provide a valid image."),
  SUB_CATEGORY_NOT_FOUND("Sub category couldn't be found."),
  PRODUCT_NOT_FOUND("Product couldn't be found."),
  DATE_NOT_VALID("You must provide a valid date."),
  DURATION_NOT_VALID("Duration must be a positive number."),
  PRICE_REDUCTION_PERCENTAGE_NOT_VALID("Price reduction percentage must be between 0 to 100"),
  INVALID_TYPE("You must provide a valid type"),
  ADDRESS_NOT_FOUND("Address couldn't be found."),
  CAMPAIGN_NOT_FOUND("Campaign couldn't be found"),
  COVER_NOT_FOUND("Cover couldn't be found."),
  USER_OTP_SERVICE_BLOCKED(
      "Too many requests within a short amount of time. User is blocked temporarily. Please try again later."),
  USER_NOT_FOUND("USER_NOT_FOUND");

  private String message;

  private Message(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
