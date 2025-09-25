package com.rtpascual.ledger.core.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class TransactionDTO {
    @NonNull
    private LocalDate date;
    @NonNull
    private String description;
    @Singular
    private List<PostingDTO> postings;

}
