package com.github.skremiec.pionteching.transactions;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionProcessorTest {
    private final TransactionProcessor transactionProcessor = new TransactionProcessor();

    @Test
    public void singleTransaction() {
        val accounts = transactionProcessor.report(List.of(
            new Transaction("account1", "account2", 10.10)
        ));

        assertEquals(
            List.of(
                new Account("account1", 1, 0, -10.10),
                new Account("account2", 0, 1,  10.10)
            ),
            accounts
        );
    }

    @Test
    public void twoTransactions() {
        val accounts = transactionProcessor.report(List.of(
            new Transaction("account1", "account2", 10.10),
            new Transaction("account1", "account2", 20.20)
        ));

        assertEquals(
            List.of(
                new Account("account1", 2, 0, -30.30),
                new Account("account2", 0, 2,  30.30)
            ),
            accounts
        );
    }

    @Test
    public void inverseTransactions() {
        val accounts = transactionProcessor.report(List.of(
            new Transaction("account1", "account2", 10.10),
            new Transaction("account2", "account1", 10.10)
        ));

        assertEquals(
            List.of(
                new Account("account1", 1, 1, 0),
                new Account("account2", 1, 1, 0)
            ),
            accounts
        );
    }

    @Test
    public void differentAccounts() {
        val accounts = transactionProcessor.report(List.of(
            new Transaction("account1", "account2", 10.10),
            new Transaction("account2", "account3", 20.20)
        ));

        assertEquals(
            List.of(
                new Account("account1", 1, 0, -10.10),
                new Account("account2", 1, 1, -10.10),
                new Account("account3", 0, 1,  20.20)
            ),
            accounts
        );
    }

    @Test
    public void example() {
        val accounts = transactionProcessor.report(List.of(
            new Transaction("32309111922661937852684864", "06105023389842834748547303", 10.90),
            new Transaction("31074318698137062235845814", "66105036543749403346524547", 200.90),
            new Transaction("66105036543749403346524547", "32309111922661937852684864", 50.10)
        ));

        assertEquals(
            List.of(
                new Account("06105023389842834748547303", 0, 1,   10.90),
                new Account("31074318698137062235845814", 1, 0, -200.90),
                new Account("32309111922661937852684864", 1, 1,   39.20),
                new Account("66105036543749403346524547", 1, 1,  150.80)
            ),
            accounts
        );
    }
}
