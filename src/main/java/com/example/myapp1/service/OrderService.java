package com.example.myapp1.service;

import com.example.myapp1.dto.OrderDTO;
import com.example.myapp1.exception.NotFoundException;
import com.example.myapp1.model.Discount;
import com.example.myapp1.model.Order;
import com.example.myapp1.model.User;
import com.example.myapp1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class OrderService {

    private static final String ORDER_NOT_FOUND = "Không tìm thấy đơn hàng với ID: ";
    private static final String USER_NOT_FOUND = "Không tìm thấy người dùng với ID: ";
    private static final String DISCOUNT_NOT_FOUND = "Không tìm thấy mã giảm giá với ID: ";

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final DiscountService discountService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
            UserService userService,
            DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.discountService = discountService;
    }

    public Page<OrderDTO> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        return orderRepository.findAll(pageable).map(this::convertToDTO);
    }

    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND + id));
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        // order.setOrderDate(LocalDateTime.now()); // Tự động thêm ngày đặt hàng
        // order.setOrderDate();
        if (order.getOrderDate() == null) {
            order.setOrderDate(new Date()); // Set current date if not provided
        }
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    // public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
    // return orderRepository.findById(id)
    // .map(existingOrder -> {
    // Order order = convertToEntity(orderDTO);
    // order.setOrderId(id);
    // return convertToDTO(orderRepository.save(order));
    // })
    // .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND + id));
    // }

    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    updateEntityFromDTO(existingOrder, orderDTO);
                    return convertToDTO(orderRepository.save(existingOrder));
                })
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND + id));
    }

    private void updateEntityFromDTO(Order existingOrder, OrderDTO orderDTO) {
        if (orderDTO.getUserId() != null) {
            User user = userService.getUserEntityById(orderDTO.getUserId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + orderDTO.getUserId()));
            existingOrder.setUser(user);
        }

        if (orderDTO.getOrderDate() != null) {
            existingOrder.setOrderDate(orderDTO.getOrderDate());
        }

        if (orderDTO.getTotalAmount() != null) {
            existingOrder.setTotalAmount(orderDTO.getTotalAmount());
        }

        if (orderDTO.getStatus() != null) {
            existingOrder.setStatus(orderDTO.getStatus());
        }

        if (orderDTO.getDiscountId() != null) {
            Discount discount = discountService.getDiscountEntityById(orderDTO.getDiscountId());
            if (discount == null) {
                throw new NotFoundException(DISCOUNT_NOT_FOUND + orderDTO.getDiscountId());
            }
            existingOrder.setDiscount(discount);
        } else {
            existingOrder.setDiscount(null);
        }
    }

    // public OrderDTO updateOrderStatus(int id, String status) {
    // return orderRepository.findById(id)
    // .map(order -> {
    // order.setStatus(status);
    // return convertToDTO(orderRepository.save(order));
    // })
    // .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND + id));
    // }
    public OrderDTO updateOrderStatus(int id, OrderDTO.StatusRequest statusRequest) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(statusRequest.getStatus());
                    return convertToDTO(orderRepository.save(order));
                })
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND + id));
    }

    public void deleteOrder(int id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException(ORDER_NOT_FOUND + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserid())
                .fullName(order.getUser().getFullname())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .discountId(order.getDiscount() != null ? order.getDiscount().getDiscountId() : null)
                .build();
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        User user = userService.getUserEntityById(orderDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + orderDTO.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());

        if (orderDTO.getDiscountId() != null) {
            Discount discount = discountService.getDiscountEntityById(orderDTO.getDiscountId());
            if (discount == null) {
                throw new NotFoundException(DISCOUNT_NOT_FOUND + orderDTO.getDiscountId());
            }
            // Discount discount =
            // discountService.getDiscountEntityById(orderDTO.getDiscountId())
            // .orElseThrow(() -> new NotFoundException(DISCOUNT_NOT_FOUND +
            // orderDTO.getDiscountId()));
            order.setDiscount(discount);
        }

        return order;
    }

    // Trong OrderService.java
    // public Page<OrderDTO> getOrdersByUser(Long userId, int page, int size) {
    // Pageable pageable = PageRequest.of(page, size,
    // Sort.by("orderDate").descending());
    // return orderRepository.findByUser_Id(userId,
    // pageable).map(this::convertToDTO);
    // }

    // public Page<OrderDTO> getOrdersByUser(User user, int page, int size) {
    // Pageable pageable = PageRequest.of(page, size,
    // Sort.by("orderDate").descending());
    // return orderRepository.findByUser(user, pageable)
    // .map(this::convertToDTO);
    // }

    // trong OrderService
    public Page<OrderDTO> getOrdersByUser(Long userId, int page, int size) {
        User user = userService.getUserEntityById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + userId));
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        return orderRepository.findByUser(user, pageable)
                .map(this::convertToDTO);
    }

    // private Order updateEntityFromDTO(Order existingOrder, OrderDTO orderDTO) {
    // if (orderDTO.getUserId() != null) {
    // User user = userService.getUserEntityById(orderDTO.getUserId())
    // .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND +
    // orderDTO.getUserId()));
    // existingOrder.setUser(user);
    // }

    // if (orderDTO.getOrderDate() != null) {
    // existingOrder.setOrderDate(orderDTO.getOrderDate());
    // }

    // if (orderDTO.getTotalAmount() != null) {
    // existingOrder.setTotalAmount(orderDTO.getTotalAmount());
    // }

    // if (orderDTO.getStatus() != null) {
    // existingOrder.setStatus(orderDTO.getStatus());
    // }

    // if (orderDTO.getDiscountId() != null) {
    // Discount discount =
    // discountService.getDiscountEntityById(orderDTO.getDiscountId())
    // .orElseThrow(() -> new NotFoundException(DISCOUNT_NOT_FOUND +
    // orderDTO.getDiscountId()));
    // existingOrder.setDiscount(discount);
    // } else {
    // existingOrder.setDiscount(null);
    // }

    // return existingOrder;
    // }
}