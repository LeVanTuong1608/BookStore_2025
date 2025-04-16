package com.example.service.impl;

import com.example.dto.PaymentDTO;
import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import com.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setUser(new User(paymentDTO.getUserId())); // Giả sử có User với ID
        payment.setCart(new Cart(paymentDTO.getCartId())); // Giả sử có Cart với ID
        payment.setAmount(paymentDTO.getAmount());
        payment.setQrCode(paymentDTO.getQrCode());
        payment.setStatus(paymentDTO.getStatus());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(int id, PaymentDTO paymentDTO) {
        return paymentRepository.findById(id).map(payment -> {
            payment.setUser(new User(paymentDTO.getUserId())); // Giả sử có User với ID
            payment.setCart(new Cart(paymentDTO.getCartId())); // Giả sử có Cart với ID
            payment.setAmount(paymentDTO.getAmount());
            payment.setQrCode(paymentDTO.getQrCode());
            payment.setStatus(paymentDTO.getStatus());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            return paymentRepository.save(payment);
        }).orElse(null);
    }

    @Override
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }
}
