package com.es.phoneshop.model.order;

import java.math.BigDecimal;
import java.util.Currency;

public enum DeliveryMode {
    COURIER(10, "USD"), STORE_PICKUP(0, "USD");
    private BigDecimal cost;
    private Currency currency;

    DeliveryMode(int cost, String currencyString) {
        this.cost = new BigDecimal(cost);
        currency = Currency.getInstance(currencyString);
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }
}
