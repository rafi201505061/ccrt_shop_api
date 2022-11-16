package com.ccrt.onlineshop.model.request;

import java.util.Date;

public class CampaignCreationRequestModel {
  private String title;
  private String description;
  private Date startTime;
  private long durationInDays;
  private double priceReductionPercentage;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getDurationInDays() {
    return durationInDays;
  }

  public void setDurationInDays(long durationInDays) {
    this.durationInDays = durationInDays;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
}
