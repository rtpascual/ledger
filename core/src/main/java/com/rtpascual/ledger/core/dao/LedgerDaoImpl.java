package com.rtpascual.ledger.core.dao;

import com.rtpascual.ledger.core.model.Account;
import com.rtpascual.ledger.core.model.Posting;
import com.rtpascual.ledger.core.model.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LedgerDaoImpl implements LedgerDao {

    private final String ledgerFilePath;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LedgerDaoImpl(String ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ledgerFilePath))) {
            String line;
            LocalDate date = LocalDate.now();
            String description = "";
            List<Posting> postings = new ArrayList<>();
            Pattern transactionLinePattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2})\s+(.*)");
            Pattern postingLinePattern = Pattern.compile("^\\s+([\\w:]+)\\s*(\\$?(-?[\\d,]+\\.\\d{2}))?");

            while ((line = reader.readLine()) != null) {
                Matcher transactionMatcher = transactionLinePattern.matcher(line);
                if (transactionMatcher.matches()) {
                    if (!postings.isEmpty()) {
                        Transaction transaction = Transaction.builder()
                            .date(date)
                            .description(description)
                            .postings(postings)
                            .build();
                        transactions.add(transaction);
                        postings.clear();
                    }
                    date = LocalDate.parse(transactionMatcher.group(1), DATE_FORMATTER);
                    description = transactionMatcher.group(2);
                } else {
                    Matcher postingMatcher = postingLinePattern.matcher(line);
                    if (postingMatcher.matches()) {
                        String accountName = postingMatcher.group(1);
                        BigDecimal amount = isNullOrEmpty(postingMatcher.group(2)) ?
                            null :
                            new BigDecimal(postingMatcher.group(2).replaceAll("[$,]", ""));
                        Account account = Account.builder()
                            .name(accountName)
                            .balance(amount)
                            .build();
                        Posting posting = Posting.builder()
                            .account(account)
                            .amount(amount)
                            .build();
                        postings.add(posting);
                    }
                }
            }
            // Add last transaction
            Transaction transaction = Transaction.builder()
                .date(date)
                .description(description)
                .postings(postings)
                .build();
            transactions.add(transaction);
        } catch (IOException e) {
            // In a real application, you'd want to log this error
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ledgerFilePath, true))) {
            writer.newLine();
            writer.write(transaction.getDate().format(DATE_FORMATTER) + " " + transaction.getDescription());
            writer.newLine();
            for (Posting posting : transaction.getPostings()) {
                String amountString = String.format("%.2f", posting.getAmount());
                writer.write("    " + posting.getAccount().getName() + "    $" + amountString);
                writer.newLine();
            }
        } catch (IOException e) {
            // In a real application, you'd want to log this error
            e.printStackTrace();
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}