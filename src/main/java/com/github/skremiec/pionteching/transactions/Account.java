package com.github.skremiec.pionteching.transactions;

import lombok.Data;

@Data
public class Account {
    private final String account;
    private long debitCount;
    private long creditCount;
    private long balance;

    public Account(final String account) {
        this(account, 0, 0, 0);
    }

    public Account(final String account, final long debitCount, final long creditCount, final double balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = Math.round(balance * 100);
    }

    public void debit(final double amount) {
        debitCount++;
        balance -= Math.round(amount * 100);
    }

    public void credit(final double amount) {
        creditCount++;
        balance += Math.round(amount * 100);
    }

    @SuppressWarnings("unused")
    public float getBalance() {
        return balance / 100.0f;
    }
}
