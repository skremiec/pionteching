package com.github.skremiec.pionteching.transactions;

import lombok.Data;

@Data
public class Transaction {
    private final String debitAccount;
    private final String creditAccount;
    private final double amount;
}
