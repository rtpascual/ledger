package com.rtpascual.ledger.core.mapper;

import com.rtpascual.ledger.core.dto.PostingDTO;
import com.rtpascual.ledger.core.dto.TransactionDTO;
import com.rtpascual.ledger.core.model.Account;
import com.rtpascual.ledger.core.model.Posting;
import com.rtpascual.ledger.core.model.Transaction;
import com.rtpascual.ledger.core.store.AccountRegistry;

import java.util.stream.Collectors;
import java.util.List;

/**
 * A component that maps between Transaction/Posting models and their corresponding DTOs.
 * It requires an AccountRegistry to look up accounts when mapping from DTOs to models.
 */
public class TransactionMapper {

    private final AccountRegistry accountRegistry;

    public TransactionMapper(AccountRegistry accountRegistry) {
        this.accountRegistry = accountRegistry;
    }

    // --- To DTO ---

    public PostingDTO toDTO(Posting posting) {
        return PostingDTO.builder()
                .accountName(posting.getAccount().getName())
                .amount(posting.getAmount())
                .build();
    }

    public TransactionDTO toDTO(Transaction transaction) {
        List<PostingDTO> postingDTOs = transaction.getPostings().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return TransactionDTO.builder()
                .date(transaction.getDate())
                .description(transaction.getDescription())
                .postings(postingDTOs)
                .build();
    }

    // --- To Model/Entity ---

    public Posting toModel(PostingDTO postingDTO) {
        // Use the registry to find the authoritative Account object.
        Account account = accountRegistry.findByName(postingDTO.getAccountName())
                .orElseThrow(() -> new IllegalArgumentException("Account not found in registry: " + postingDTO.getAccountName()));

        return Posting.builder()
                .account(account)
                .amount(postingDTO.getAmount())
                .build();
    }

    public Transaction toModel(TransactionDTO transactionDTO) {
        List<Posting> postings = transactionDTO.getPostings().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return Transaction.builder()
                .date(transactionDTO.getDate())
                .description(transactionDTO.getDescription())
                .postings(postings)
                .build();
    }
}
