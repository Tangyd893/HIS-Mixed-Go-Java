package com.hismixed.billing.controller;

import com.hismixed.billing.entity.BillItem;
import com.hismixed.billing.entity.Payment;
import com.hismixed.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    // 支付记录相关接口

    @GetMapping("/payments")
    public ResponseEntity<Page<Payment>> listPayments(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(billingService.listPayments(patientId, status, pageable));
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getPaymentById(id));
    }

    @GetMapping("/payments/no/{paymentNo}")
    public ResponseEntity<Payment> getPaymentByNo(@PathVariable String paymentNo) {
        return ResponseEntity.ok(billingService.getPaymentByNo(paymentNo));
    }

    @GetMapping("/payments/patient/{patientId}")
    public ResponseEntity<List<Payment>> getPaymentsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(billingService.getPaymentsByPatient(patientId));
    }

    // 账单项目相关接口

    @GetMapping("/bill-items")
    public ResponseEntity<Page<BillItem>> listBillItems(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(billingService.listBillItems(patientId, status, pageable));
    }

    @GetMapping("/bill-items/payment/{paymentId}")
    public ResponseEntity<List<BillItem>> getBillItemsByPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(billingService.getBillItemsByPayment(paymentId));
    }

    @PostMapping("/bill-items")
    public ResponseEntity<BillItem> createBillItem(@RequestBody BillItem billItem) {
        return ResponseEntity.ok(billingService.createBillItem(billItem));
    }

    // 支付操作接口

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(
            @RequestParam Long patientId,
            @RequestBody List<Long> billItemIds,
            @RequestParam String paymentMethod) {
        return ResponseEntity.ok(billingService.createPayment(patientId, billItemIds, paymentMethod));
    }

    // 统计接口

    @GetMapping("/unpaid-amount/{patientId}")
    public ResponseEntity<BigDecimal> getUnpaidAmount(@PathVariable Long patientId) {
        return ResponseEntity.ok(billingService.getUnpaidAmount(patientId));
    }

    @GetMapping("/today-revenue")
    public ResponseEntity<BigDecimal> getTodayRevenue() {
        return ResponseEntity.ok(billingService.getTodayRevenue());
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCountByStatus(@RequestParam String status) {
        return ResponseEntity.ok(Map.of("count", billingService.countByStatus(status)));
    }
}
