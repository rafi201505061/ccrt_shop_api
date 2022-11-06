package com.ccrt.onlineshop.io.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ccrt.onlineshop.enums.Role;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false, length = 30)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, unique = true, length = 120)
  private String email;

  @Column(nullable = false, length = 250)
  private String encryptedPassword;

  private String passwordResetToken;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "uploader")
  private Set<ProductEntity> products = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private AddressEntity defaultShippingAddress;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private AddressEntity defaultBillingAddress;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getPasswordResetToken() {
    return passwordResetToken;
  }

  public void setPasswordResetToken(String passwordResetToken) {
    this.passwordResetToken = passwordResetToken;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  public AddressEntity getDefaultShippingAddress() {
    return defaultShippingAddress;
  }

  public void setDefaultShippingAddress(AddressEntity defaultShippingAddress) {
    this.defaultShippingAddress = defaultShippingAddress;
  }

  public AddressEntity getDefaultBillingAddress() {
    return defaultBillingAddress;
  }

  public void setDefaultBillingAddress(AddressEntity defaultBillingAddress) {
    this.defaultBillingAddress = defaultBillingAddress;
  }

}
