package com.ccrt.onlineshop.model.request;

public class ProductRatingRequestModel {
  private String raterUserId;
  private double rating;

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getRaterUserId() {
    return raterUserId;
  }

  public void setRaterUserId(String raterUserId) {
    this.raterUserId = raterUserId;
  }
}
