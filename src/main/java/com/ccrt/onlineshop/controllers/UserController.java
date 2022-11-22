package com.ccrt.onlineshop.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.enums.Role;
import com.ccrt.onlineshop.exceptions.OrderServiceException;
import com.ccrt.onlineshop.exceptions.UserServiceException;
import com.ccrt.onlineshop.model.request.OrderCreationRequestModel;
import com.ccrt.onlineshop.model.request.OrderItemCreationRequestModel;
import com.ccrt.onlineshop.model.request.UserPasswordResetRequestModel;
import com.ccrt.onlineshop.model.request.UserPasswordUpdateRequestModel;
import com.ccrt.onlineshop.model.request.UserSignupRequestModel;
import com.ccrt.onlineshop.model.request.UserUpdateRequestModel;
import com.ccrt.onlineshop.model.response.OrderRest;
import com.ccrt.onlineshop.model.response.ResponseMessage;
import com.ccrt.onlineshop.model.response.UserRest;
import com.ccrt.onlineshop.service.OrderService;
import com.ccrt.onlineshop.service.UserService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.OrderDto;
import com.ccrt.onlineshop.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private OrderService orderService;

  @Autowired
  private Utils utils;

  @GetMapping
  public UserRest retrieveUser(@RequestParam(name = "email", required = true, defaultValue = "") String email) {
    UserDto userDto = userService.getUserByEmail(email);
    return modelMapper.map(userDto, UserRest.class);
  }

  @PostMapping
  public UserRest createUser(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    return userCreationHelper(userSignupRequestModel, Role.USER);
  }

  @PostMapping("/admin")
  public UserRest createAdminUser(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    return userCreationHelper(userSignupRequestModel, Role.ADMIN);
  }

  @GetMapping("/{userId}")
  public UserRest getUserDetails(@PathVariable String userId, Principal principal) {
    if (!principal.getName().equals(userId))
      throw new UserServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    UserDto userDto = userService.getUserByUserId(userId);
    return modelMapper.map(userDto, UserRest.class);
  }

  @PutMapping("/{userId}")
  public UserRest updateUserDetails(@PathVariable String userId, Principal principal,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {
    if (!principal.getName().equals(userId))
      throw new UserServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    checkUserUpdateRequestBody(userUpdateRequestModel);

    UserDto userDto = userService.updateUser(userId, modelMapper.map(userUpdateRequestModel, UserDto.class));
    return modelMapper.map(userDto, UserRest.class);
  }

  @PutMapping("/{userId}/password")
  public ResponseEntity<ResponseMessage> updatePassword(@PathVariable String userId, Principal principal,
      @RequestBody UserPasswordUpdateRequestModel userPasswordUpdateRequestModel) {
    if (!principal.getName().equals(userId))
      throw new UserServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    checkUserPasswordUpdateRequestBody(userPasswordUpdateRequestModel);
    userService.updatePassword(userId, modelMapper.map(userPasswordUpdateRequestModel, UserDto.class));
    return new ResponseEntity<ResponseMessage>(new ResponseMessage(MessageCode.PASSWORD_UPDATE_SUCCESSFUL.name(),
        Message.PASSWORD_UPDATE_SUCCESSFUL.getMessage()), HttpStatus.OK);
  }

  @PostMapping("/{userId}/password-reset-verification-code")
  private ResponseEntity<ResponseMessage> sendPasswordResetVerificationCode(
      @PathVariable(name = "userId") String userId) {
    userService.sendPasswordResetCode(userId);
    return new ResponseEntity<>(new ResponseMessage("EMAIL_SENT",
        "An email has been sent to your email address with the password reset token."), HttpStatus.OK);
  }

  @PutMapping("/{userId}/password-reset")
  private ResponseEntity<ResponseMessage> resetPassword(
      @PathVariable String userId,
      @RequestBody UserPasswordResetRequestModel userPasswordResetRequestModel) {
    userService.resetPassword(userId, modelMapper.map(userPasswordResetRequestModel, UserDto.class));
    return new ResponseEntity<ResponseMessage>(new ResponseMessage(MessageCode.PASSWORD_RESET_SUCCESSFUL.name(),
        Message.PASSWORD_RESET_SUCCESSFUL.getMessage()), HttpStatus.OK);
  }

  @PostMapping("/{userId}/orders")
  public OrderRest createOrder(@PathVariable String userId, Principal principal,
      @RequestBody OrderCreationRequestModel orderCreationRequestModel) {
    if (!principal.getName().equals(userId)) {
      throw new OrderServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    List<OrderItemCreationRequestModel> orderItems = orderCreationRequestModel.getOrderItems();
    List<OrderItemCreationRequestModel> validOrderItems = new ArrayList<>();
    int totalItems = 0;
    for (OrderItemCreationRequestModel orderItem : orderItems) {
      totalItems += orderItem.getNumItems();
      if (orderItem.getNumItems() > 0) {
        validOrderItems.add(orderItem);
      }
    }
    if (totalItems <= 0) {
      throw new OrderServiceException("ORDER_QUANTITY_ERROR", "You must add at least one item.",
          HttpStatus.BAD_REQUEST);
    }
    orderCreationRequestModel.setOrderItems(validOrderItems);
    OrderDto orderDto = modelMapper.map(orderCreationRequestModel, OrderDto.class);
    OrderDto createdOrderDto = orderService.createOrder(userId, orderDto);
    return modelMapper.map(createdOrderDto, OrderRest.class);
  }

  @GetMapping("/{userId}/orders")
  public List<OrderRest> retrieveOrders(@PathVariable String userId, Principal principal,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "limit", required = false, defaultValue = "15") int limit) {
    if (!principal.getName().equals(userId)) {
      throw new OrderServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    List<OrderDto> orderDtos = orderService.retrieveOrders(userId, page, limit);
    List<OrderRest> orderRests = new ArrayList<>();
    for (OrderDto orderDto : orderDtos) {
      orderRests.add(modelMapper.map(orderDto, OrderRest.class));
    }
    return orderRests;
  }

  @DeleteMapping("/{userId}/orders/{orderId}")
  public ResponseEntity<String> cancelOrder(@PathVariable String userId, Principal principal,
      @PathVariable String orderId) {
    if (!principal.getName().equals(userId)) {
      throw new OrderServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    orderService.cancelOrder(userId, orderId);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private UserRest userCreationHelper(UserSignupRequestModel userSignupRequestModel, Role role) {
    checkUserSignupRequestBody(userSignupRequestModel);
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);
    UserDto createdUserDto = userService.createUser(userDto, role);
    return modelMapper.map(createdUserDto, UserRest.class);

  }

  private void checkUserSignupRequestBody(UserSignupRequestModel userSignupRequestModel) {
    String firstName = userSignupRequestModel.getFirstName();
    String lastName = userSignupRequestModel.getLastName();
    String email = userSignupRequestModel.getEmail();
    String password = userSignupRequestModel.getPassword();

    if (!utils.isNonNullAndNonEmpty(firstName))
      throw new UserServiceException(MessageCode.FIRST_NAME_NOT_VALID.name(),
          Message.FIRST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.isNonNullAndNonEmpty(lastName))
      throw new UserServiceException(MessageCode.LAST_NAME_NOT_VALID.name(),
          Message.LAST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validateEmail(email))
      throw new UserServiceException(MessageCode.EMAIL_NOT_VALID.name(),
          Message.EMAIL_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validatePassword(password))
      throw new UserServiceException(MessageCode.PASSWORD_NOT_VALID.name(),
          Message.PASSWORD_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);

  }

  private void checkUserUpdateRequestBody(UserUpdateRequestModel userUpdateRequestModel) {
    String firstName = userUpdateRequestModel.getFirstName();
    String lastName = userUpdateRequestModel.getLastName();
    if (!utils.isNonNullAndNonEmpty(firstName) && !utils.isNonNullAndNonEmpty(lastName)) {
      throw new UserServiceException(MessageCode.BAD_REQUEST.name(),
          "You must provide either first name or last name.", HttpStatus.BAD_REQUEST);
    }

  }

  private void checkUserPasswordUpdateRequestBody(UserPasswordUpdateRequestModel userPasswordUpdateRequestModel) {
    String password = userPasswordUpdateRequestModel.getPassword();
    String prevPassword = userPasswordUpdateRequestModel.getPrevPassword();
    if (!utils.isNonNullAndNonEmpty(password))
      throw new UserServiceException(MessageCode.BAD_REQUEST.name(),
          Message.BAD_REQUEST.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.isNonNullAndNonEmpty(prevPassword))
      throw new UserServiceException(MessageCode.BAD_REQUEST.name(),
          Message.BAD_REQUEST.getMessage(), HttpStatus.BAD_REQUEST);

  }
}
