package com.example.service.impl;

import com.example.dto.OrderDTO;
import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUser(new User(orderDTO.getUserId())); // Giả sử có User với ID
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setDiscount(new Discount(orderDTO.getDiscountId())); // Giả sử có Discount với ID
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(int id, OrderDTO orderDTO) {
        return orderRepository.findById(id).map(order -> {
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setStatus(orderDTO.getStatus());
            order.setDiscount(new Discount(orderDTO.getDiscountId())); // Giả sử có Discount với ID
            return orderRepository.save(order);
        }).orElse(null);
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
