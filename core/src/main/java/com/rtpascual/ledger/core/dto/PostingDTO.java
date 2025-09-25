package com.rtpascual.ledger.core.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PostingDTO {
    @NonNull
    private String accountName;
    private BigDecimal amount;

}
