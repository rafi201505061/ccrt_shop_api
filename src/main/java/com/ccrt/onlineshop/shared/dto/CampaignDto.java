package com.ccrt.onlineshop.shared.dto;

import java.util.Date;
import java.util.List;

public class CampaignDto {
  private String campaignId;
  private Date startTime;
  private Date endTime;
  private double priceReductionPercentage;
  private List<CampaignProductDto> products;
  private String title;
  private String description;

  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }

  public List<CampaignProductDto> getProducts() {
    return products;
  }

  public void setProducts(List<CampaignProductDto> products) {
    this.products = products;
  }

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

}
