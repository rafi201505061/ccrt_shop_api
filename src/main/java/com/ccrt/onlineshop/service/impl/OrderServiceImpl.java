package com.ccrt.onlineshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.enums.PaymentStatus;
import com.ccrt.onlineshop.exceptions.OrderServiceException;
import com.ccrt.onlineshop.io.entity.AddressEntity;
import com.ccrt.onlineshop.io.entity.DeliveryCostEntity;
import com.ccrt.onlineshop.io.entity.OrderEntity;
import com.ccrt.onlineshop.io.entity.OrderItemEntity;
import com.ccrt.onlineshop.io.entity.OrderStatusEntity;
import com.ccrt.onlineshop.io.entity.ProductEntity;
import com.ccrt.onlineshop.io.entity.UserEntity;
import com.ccrt.onlineshop.io.repository.AddressRepository;
import com.ccrt.onlineshop.io.repository.DeliveryCostRepository;
import com.ccrt.onlineshop.io.repository.OrderItemRepository;
import com.ccrt.onlineshop.io.repository.OrderRepository;
import com.ccrt.onlineshop.io.repository.OrderStatusRepository;
import com.ccrt.onlineshop.io.repository.ProductRepository;
import com.ccrt.onlineshop.io.repository.UserRepository;
import com.ccrt.onlineshop.service.OrderService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.OrderDto;
import com.ccrt.onlineshop.shared.dto.OrderItemDto;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private DeliveryCostRepository deliveryCostRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private OrderStatusRepository orderStatusRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Transactional
  @Override
  synchronized public OrderDto createOrder(String userId, OrderDto orderDto) {
    OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null)
      throw new OrderServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    AddressEntity billingAddressEntity = addressRepository.findByAddressIdAndIsValid(orderDto.getBillingAddressId(),
        true);
    if (billingAddressEntity == null)
      throw new OrderServiceException(MessageCode.ADDRESS_NOT_FOUND.name(), Message.ADDRESS_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);

    AddressEntity shippingAddressEntity = addressRepository.findByAddressIdAndIsValid(orderDto.getShippingAddressId(),
        true);
    if (shippingAddressEntity == null)
      throw new OrderServiceException(MessageCode.ADDRESS_NOT_FOUND.name(), Message.ADDRESS_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    orderEntity.setOrderId(utils.generateOrderId());
    orderEntity.setUser(userEntity);
    orderEntity.setBillingAddress(billingAddressEntity);
    orderEntity.setShippingAddress(shippingAddressEntity);

    if (orderDto.getPaymentStatus() == PaymentStatus.PAID)
      orderEntity.setOrderStatus(OrderStatus.CONFIRMED);

    else {
      orderEntity.setOrderStatus(OrderStatus.ORDERED);
    }
    List<OrderItemEntity> orderItemEntities = new ArrayList<>();
    double totalProductCost = 0;
    List<ProductEntity> productEntities = new ArrayList<>();

    for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
      OrderItemEntity orderItemEntity = modelMapper.map(orderItemDto, OrderItemEntity.class);
      ProductEntity productEntity = productRepository.findByProductId(orderItemDto.getProductId());
      if (productEntity == null) {
        throw new OrderServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
            HttpStatus.NOT_FOUND);
      }
      orderItemEntity.setProduct(productEntity);
      orderItemEntity.setUnitPrice(productEntity.getPrice());
      double totalCost = productEntity.getPrice() * orderItemDto.getNumItems();
      orderItemEntity.setTotalCost(totalCost);
      totalProductCost += totalCost;
      orderItemEntities.add(orderItemEntity);
      productEntity.setTotalEntities(productEntity.getTotalEntities() - orderItemDto.getNumItems());
      productEntities.add(productEntity);
    }
    productRepository.saveAll(productEntities);
    orderEntity.setTotalProductCost(totalProductCost);
    DeliveryCostEntity deliveryCostEntity = deliveryCostRepository.findByPlace(orderDto.getPlace());
    orderEntity.setDeliveryCost(deliveryCostEntity.getCost());
    orderEntity.setTotalCost(totalProductCost + deliveryCostEntity.getCost());
    OrderEntity createdOrderEntity = orderRepository.save(orderEntity);
    for (OrderItemEntity orderItemEntity : orderItemEntities) {
      orderItemEntity.setOrder(createdOrderEntity);
    }
    orderItemRepository.saveAll(orderItemEntities);
    OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
    orderStatusEntity.setOrder(createdOrderEntity);
    orderStatusEntity.setOrderStatus(OrderStatus.ORDERED);
    orderStatusRepository.save(orderStatusEntity);
    if (orderDto.getPaymentStatus() == PaymentStatus.PAID) {
      OrderStatusEntity orderStatusEntity2 = new OrderStatusEntity();
      orderStatusEntity2.setOrder(createdOrderEntity);
      orderStatusEntity2.setOrderStatus(OrderStatus.CONFIRMED);
      orderStatusRepository.save(orderStatusEntity2);
    }
    return modelMapper.map(createdOrderEntity, OrderDto.class);
  }

  @Override
  public OrderDto retrieveOrder(String orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    if (orderEntity == null) {
      throw new OrderServiceException(MessageCode.ORDER_NOT_FOUND.name(), Message.ORDER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(orderEntity, OrderDto.class);
  }

  @Override
  public List<OrderDto> retrieveOrders(String userId, int page, int limit) {
    Page<OrderEntity> orderEntities = orderRepository.findAllByUser_UserId(userId,
        PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    List<OrderDto> orderDtos = new ArrayList<>();
    for (OrderEntity orderEntity : orderEntities.getContent()) {
      orderDtos.add(modelMapper.map(orderEntity, OrderDto.class));
    }
    return orderDtos;
  }

  @Override
  public List<OrderDto> retrieveOrders(OrderStatus orderStatus, int page, int limit) {

    Page<OrderEntity> orderEntities;
    if (orderStatus == OrderStatus.ALL) {

      orderEntities = orderRepository.findAll(
          PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    } else {
      orderEntities = orderRepository.findAllByOrderStatus(orderStatus,
          PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    }

    List<OrderDto> orderDtos = new ArrayList<>();
    for (OrderEntity orderEntity : orderEntities.getContent()) {
      orderDtos.add(modelMapper.map(orderEntity, OrderDto.class));
    }
    return orderDtos;
  }

  @Transactional
  @Override
  public OrderDto updateStatus(String orderId, OrderStatus orderStatus) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    if (orderEntity == null) {
      throw new OrderServiceException(MessageCode.ORDER_NOT_FOUND.name(), Message.ORDER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    List<OrderStatus> orderStatusOrder = new ArrayList<>();
    orderStatusOrder.add(OrderStatus.ORDERED);
    orderStatusOrder.add(OrderStatus.CONFIRMED);
    orderStatusOrder.add(OrderStatus.HANDED_FOR_DELIVERY);
    orderStatusOrder.add(OrderStatus.DELIVERED);
    int currentStatusIndex = orderStatusOrder.indexOf(orderEntity.getOrderStatus());
    int nextStatusIndex = orderStatusOrder.indexOf(orderStatus);
    if (nextStatusIndex <= currentStatusIndex) {
      throw new OrderServiceException(MessageCode.ORDER_STATUS_ORDER_NOT_VALID.name(),
          Message.ORDER_STATUS_ORDER_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    orderEntity.setOrderStatus(orderStatus);
    OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);
    OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
    orderStatusEntity.setOrder(orderEntity);
    orderStatusEntity.setOrderStatus(orderStatus);
    orderStatusRepository.save(orderStatusEntity);
    return modelMapper.map(updatedOrderEntity, OrderDto.class);
  }

  @Override
  synchronized public void cancelOrder(String userId, String orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    if (orderEntity == null) {
      throw new OrderServiceException(MessageCode.ORDER_NOT_FOUND.name(), Message.ORDER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    if (!orderEntity.getUser().getUserId().equals(userId)) {
      throw new OrderServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    orderEntity.setOrderStatus(OrderStatus.CANCELLED);
    orderEntity.setCancellationTime(new Date());
    orderRepository.save(orderEntity);

    OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
    orderStatusEntity.setOrder(orderEntity);
    orderStatusEntity.setOrderStatus(OrderStatus.CANCELLED);
    orderStatusRepository.save(orderStatusEntity);

    List<ProductEntity> productEntities = new ArrayList<>();
    for (OrderItemEntity orderItemEntity : orderEntity.getOrderItems()) {
      ProductEntity productEntity = orderItemEntity.getProduct();
      productEntity.setTotalEntities(productEntity.getTotalEntities() + orderItemEntity.getNumItems());
      productEntities.add(productEntity);
    }
    productRepository.saveAll(productEntities);

  }

}
