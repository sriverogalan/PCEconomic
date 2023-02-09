package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.forms.PaymentForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PaymentForm paymentData) {
        String paymentRequest = buildPaymentRequest(paymentData);
        return ResponseEntity.ok(paymentRequest);
    }

    private String buildPaymentRequest(PaymentForm paymentData) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ds_Merchant_Amount=").append(paymentData.getAmount());
        sb.append("&Ds_Merchant_Order=").append(paymentData.getOrder());
        sb.append("&Ds_Merchant_MerchantCode=").append(paymentData.getMerchantCode());
        sb.append("&Ds_Merchant_Currency=").append(paymentData.getCurrency());
        sb.append("&Ds_Merchant_TransactionType=").append(paymentData.getTransactionType());
        sb.append("&Ds_Merchant_Terminal=").append(paymentData.getTerminal());
        sb.append("&Ds_Merchant_MerchantURL=").append(paymentData.getMerchantUrl());
        sb.append("&Ds_Merchant_UrlOK=").append(paymentData.getUrlOk());
        sb.append("&Ds_Merchant_UrlKO=").append(paymentData.getUrlKo());
        return sb.toString();
    }
}