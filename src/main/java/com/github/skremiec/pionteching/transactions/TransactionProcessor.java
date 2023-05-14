package com.github.skremiec.pionteching.transactions;

import lombok.val;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionProcessor {
    public List<Account> report(final List<Transaction> transactions) {
        val accounts = new HashMap<String, Account>();

        for (final Transaction transaction : transactions) {
            val debitAccount = getAccount(accounts, transaction.getDebitAccount());
            val creditAccount = getAccount(accounts, transaction.getCreditAccount());

            debitAccount.debit(transaction.getAmount());
            creditAccount.credit(transaction.getAmount());

            accounts.put(transaction.getDebitAccount(), debitAccount);
            accounts.put(transaction.getCreditAccount(), creditAccount);
        }

        return accounts
            .values()
            .stream()
            .sorted(Comparator.comparing(Account::getAccount))
            .collect(Collectors.toList());
    }

    private Account getAccount(final HashMap<String, Account> accounts, final String account) {
        return accounts.containsKey(account) ? accounts.get(account) : new Account(account);
    }
}
