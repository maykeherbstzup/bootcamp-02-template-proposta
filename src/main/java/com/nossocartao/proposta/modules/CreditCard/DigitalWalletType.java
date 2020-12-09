package com.nossocartao.proposta.modules.CreditCard;

public enum DigitalWalletType {
    PAYPAL("paypal"),
    SAMSUNG_PAY("samsungPay");

    private final String walletName;

    DigitalWalletType(String walletName) { this.walletName = walletName; }

    public String getValue() { return walletName; }
}
