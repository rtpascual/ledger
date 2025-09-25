package com.rtpascual.ledger.core.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class Transaction {
    @NonNull
    private LocalDate date;
    @NonNull
    private String description;
    @Singular
    private List<Posting> postings;

}
