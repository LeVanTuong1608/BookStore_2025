package com.example.service;

import com.example.dto.OrderDTO;
import com.example.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(int id);

    Order createOrder(OrderDTO orderDTO);

    Order updateOrder(int id, OrderDTO orderDTO);

    void deleteOrder(int id);
}
