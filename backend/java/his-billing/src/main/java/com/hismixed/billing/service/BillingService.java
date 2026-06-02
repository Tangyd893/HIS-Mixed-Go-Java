package com.hismixed.billing.service;

import com.hismixed.billing.entity.BillItem;
import com.hismixed.billing.entity.Payment;
import com.hismixed.billing.repository.BillItemRepository;
import com.hismixed.billing.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BillingService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    private static final AtomicLong paymentCounter = new AtomicLong(0);
    private static final AtomicLong billCounter = new AtomicLong(0);

    public Page<Payment> listPayments(Long patientId, String status, Pageable pageable) {
        return paymentRepository.findByConditions(patientId, status, pageable);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("支付记录不存在"));
    }

    public Payment getPaymentByNo(String paymentNo) {
        return paymentRepository.findByPaymentNo(paymentNo)
                .orElseThrow(() -> new RuntimeException("支付记录不存在"));
    }

    public List<Payment> getPaymentsByPatient(Long patientId) {
        return paymentRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public Page<BillItem> listBillItems(Long patientId, String status, Pageable pageable) {
        return billItemRepository.findByConditions(patientId, status, pageable);
    }

    public List<BillItem> getBillItemsByPayment(Long paymentId) {
        return billItemRepository.findByPaymentId(paymentId);
    }

    public BigDecimal getUnpaidAmount(Long patientId) {
        return billItemRepository.sumUnpaidAmountByPatient(patientId);
    }

    @Transactional
    public BillItem createBillItem(BillItem billItem) {
        billItem.setBillNo(generateBillNo());
        billItem.setStatus("UNPAID");
        if (billItem.getUnitPrice() != null && billItem.getQuantity() != null) {
            billItem.setAmount(billItem.getUnitPrice().multiply(BigDecimal.valueOf(billItem.getQuantity())));
        }
        return billItemRepository.save(billItem);
    }

    @Transactional
    public Payment createPayment(Long patientId, List<Long> billItemIds, String paymentMethod) {
        List<BillItem> billItems = billItemRepository.findAllById(billItemIds);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BillItem item : billItems) {
            if (item.getAmount() != null) {
                totalAmount = totalAmount.add(item.getAmount());
            }
        }

        Payment payment = new Payment();
        payment.setPaymentNo(generatePaymentNo());
        payment.setPatientId(patientId);
        payment.setTotalAmount(totalAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PAID");
        payment.setPaidAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        for (BillItem item : billItems) {
            item.setStatus("PAID");
            item.setPaymentId(payment.getId());
            item.setPaidAt(LocalDateTime.now());
        }
        billItemRepository.saveAll(billItems);

        return payment;
    }

    public BigDecimal getTodayRevenue() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        BigDecimal revenue = paymentRepository.sumAmountByTimeRange(startOfDay, endOfDay);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public long countByStatus(String status) {
        return paymentRepository.countByStatus(status);
    }

    private String generatePaymentNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = paymentCounter.incrementAndGet() % 10000;
        return String.format("PAY%s%04d", dateStr, seq);
    }

    private String generateBillNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = billCounter.incrementAndGet() % 10000;
        return String.format("BILL%s%04d", dateStr, seq);
    }
}
