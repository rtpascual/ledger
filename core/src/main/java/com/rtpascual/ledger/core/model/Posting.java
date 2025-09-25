package com.rtpascual.ledger.core.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Posting {
    @NonNull
    private Account account;
    private BigDecimal amount;

}
