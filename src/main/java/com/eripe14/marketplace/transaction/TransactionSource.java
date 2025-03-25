package com.eripe14.marketplace.transaction;

public enum TransactionSource {

    MARKETPLACE("Marketplace"),
    BLACK_MARKET("Black Market"),;

    private final String name;

    TransactionSource(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}