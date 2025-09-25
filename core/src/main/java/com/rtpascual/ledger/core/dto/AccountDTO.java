package com.rtpascual.ledger.core.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AccountDTO {
    @NonNull
    private String name;
    private BigDecimal balance;

}
