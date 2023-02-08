package me.pceconomic.shop.domain.forms;

import lombok.Data;

public @Data class PaymentForm {
     // pay form redsys
        private String amount;
        private String order;
        private String merchantCode;
        private String currency;
        private String transactionType;
        private String terminal;
        private String merchantUrl;
        private String urlOk;
        private String urlKo;

}
