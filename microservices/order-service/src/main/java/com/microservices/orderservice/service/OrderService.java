package com.microservices.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
        .stream()
        .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
        .toList();
    order.setOrderLineItemsList(orderLineItems);

    List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
    // Call inventory service and place order if product is in stock
    InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
        .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
        .retrieve()
        .bodyToMono(InventoryResponse[].class)
        .block();

    boolean allProductsInStock = Arrays.stream(inventoryResponses)
        .allMatch(inventoryResponse -> inventoryResponse.isInStock());
    if (allProductsInStock) {
      orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Product is not in stock");
    }
  }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    var orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItems.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}
