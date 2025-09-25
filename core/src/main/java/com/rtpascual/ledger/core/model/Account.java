package com.rtpascual.ledger.core.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Account {
    @NonNull
    private String name;
    private BigDecimal balance;

}
