package com.github.hardelele.cca.models.transfers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditPayout {

    private int number;
    private long timestamp;
    private double mainPayout;
    private double percentPayout;
    private double fullPayout;
    private double debt;
}
