
package com.example.myapp1.controller;

import com.example.myapp1.dto.OrderDTO;
import com.example.myapp1.dto.OrderDTO.StatusRequest;
import com.example.myapp1.model.User;
import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/orders")
public class OrderController {

    private static final String ADMIN_ROLE = "admin";
    private static final String ACCESS_DENIED = "Bạn không có quyền thực hiện thao tác này";
    private static final String ORDER_NOT_FOUND = "Không tìm thấy đơn hàng";
    private static final String LOGIN_REQUIRED = "Vui lòng đăng nhập để thực hiện thao tác này";
    private static final String SYSTEM_ERROR = "Lỗi hệ thống, vui lòng thử lại sau";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        return userDTO != null && ADMIN_ROLE.equalsIgnoreCase(userDTO.getRole());
    }

    private Long getCurrentUserId(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        return userDTO != null ? userDTO.getUserid() : null;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            HttpSession session) {

        // try {
        // Long userId = getCurrentUserId(session);
        // if (userId == null) {
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
        // }
        // if (!isAdmin(session)) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
        // }
        // User user = new User();
        // Page<OrderDTO> ordersPage = isAdmin(session)
        // ? orderService.getAllOrders(page, size)
        // : orderService.getOrdersByUser(user.getUserid(), page, size);

        // return ResponseEntity.ok(ordersPage);
        // } catch (Exception e) {
        // return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        // }
        try {
            Long userId = getCurrentUserId(session);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
            }
            if (!isAdmin(session)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
            }
            Page<OrderDTO> ordersPage = orderService.getAllOrders(page, size);
            return ResponseEntity.ok(ordersPage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id, HttpSession session) {
        try {
            Long userId = getCurrentUserId(session);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
            }

            OrderDTO order = orderService.getOrderById(id);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ORDER_NOT_FOUND);
            }

            // Check if user owns the order or is admin
            if (!order.getUserId().equals(userId) && !isAdmin(session)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
            }

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO, HttpSession session) {
        try {
            Long userId = getCurrentUserId(session);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
            }

            orderDTO.setUserId(userId);
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable int id,
            @RequestBody OrderDTO orderDTO,
            HttpSession session) {

        try {
            Long userId = getCurrentUserId(session);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
            }

            // Check if order exists and belongs to user (or is admin)
            OrderDTO existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ORDER_NOT_FOUND);
            }

            if (!existingOrder.getUserId().equals(userId) && !isAdmin(session)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
            }

            // Prevent changing user ID unless admin
            if (!isAdmin(session)) {
                orderDTO.setUserId(userId);
            }

            OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable int id,
            @RequestBody StatusRequest statusRequest,
            HttpSession session) {

        try {
            if (!isAdmin(session)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
            }

            OrderDTO updatedOrder = orderService.updateOrderStatus(id, statusRequest);
            return updatedOrder != null
                    ? ResponseEntity.ok(updatedOrder)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ORDER_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id, HttpSession session) {
        try {
            Long userId = getCurrentUserId(session);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_REQUIRED);
            }

            // Check if order exists and belongs to user (or is admin)
            OrderDTO existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ORDER_NOT_FOUND);
            }

            if (!existingOrder.getUserId().equals(userId) && !isAdmin(session)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
            }

            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }
}