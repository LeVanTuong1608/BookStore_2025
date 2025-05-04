package com.example.myapp1.service;

import com.example.myapp1.dto.OrderDTO;
import com.example.myapp1.model.Discount;
import com.example.myapp1.model.Order;
import com.example.myapp1.model.User;
import com.example.myapp1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscountService discountService;

    // Lấy tất cả đơn hàng
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo ID
    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Tạo đơn hàng mới
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    // Cập nhật thông tin đơn hàng
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    Order order = convertToEntity(orderDTO);
                    order.setOrderId(id);
                    Order updatedOrder = orderRepository.save(order);
                    return convertToDTO(updatedOrder);
                })
                .orElse(null);
    }

    // Xóa đơn hàng
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    // Chuyển đổi từ Order sang OrderDTO
    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getUser().getUserid(), // Lấy userId từ User
                order.getUser().getFullname(), // Lấy fullName từ User
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getDiscount() != null ? order.getDiscount().getDiscountId() : null);
    }

    // Chuyển đổi từ OrderDTO sang Order
    private Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        User user = userService.getUserEntityById(orderDTO.getUserId()); // Lấy User từ userId
        order.setUser(user);

        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());

        // Gán Discount (nếu có)
        if (orderDTO.getDiscountId() != null) {
            Discount discount = discountService.getDiscountEntityById(orderDTO.getDiscountId());
            order.setDiscount(discount);
        }
        return order;
    }
}
