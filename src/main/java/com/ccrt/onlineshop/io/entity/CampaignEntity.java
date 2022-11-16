package com.ccrt.onlineshop.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ccrt.onlineshop.enums.CampaignStatus;

@Entity
@Table(name = "campaign")
public class CampaignEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20, unique = true)
  private String campaignId;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date startTime;

  @Column(nullable = false)
  private long durationInDays;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date endTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CampaignStatus status;

  @Column(nullable = false)
  private double priceReductionPercentage;

  @Column(nullable = false, length = 250)
  private String title;

  @Column(nullable = false, length = 10000)
  private String description;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public long getDurationInDays() {
    return durationInDays;
  }

  public void setDurationInDays(long durationInDays) {
    this.durationInDays = durationInDays;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public CampaignStatus getStatus() {
    return status;
  }

  public void setStatus(CampaignStatus status) {
    this.status = status;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
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
