package com.ccrt.onlineshop.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.ccrt.onlineshop.enums.AddressType;

@Entity
@Table(name = "address")
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 20)
  private String addressId;

  @Column(nullable = false, length = 250)
  private String fullName;

  @Column(nullable = false, length = 20)
  private String phoneNo;

  @Column(nullable = false, length = 100)
  private String province;

  @Column(nullable = false, length = 100)
  private String city;

  @Column(nullable = false, length = 100)
  private String village;

  @Column(nullable = false)
  private boolean isValid = true;

  private String address;
  private String landmark;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AddressType addressType;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UserEntity user;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLandmark() {
    return landmark;
  }

  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
