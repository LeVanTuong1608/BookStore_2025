package com.example.service;

import com.example.dto.PaymentDTO;
import com.example.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();

    Payment getPaymentById(int id);

    Payment createPayment(PaymentDTO paymentDTO);

    Payment updatePayment(int id, PaymentDTO paymentDTO);

    void deletePayment(int id);
}
