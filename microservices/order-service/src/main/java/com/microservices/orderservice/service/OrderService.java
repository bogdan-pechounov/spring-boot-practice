package com.microservices.orderservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    var orderLineItems = orderRequest.getOrderLineItemsDtoList()
        .stream()
        .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
        .toList();
    order.setOrderLineItemsList(orderLineItems);
    orderRepository.save(order);
  }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    var orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItems.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}
