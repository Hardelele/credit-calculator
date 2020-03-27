package com.github.hardelele.cca.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditPayout {

    private int number;
    private long timestamp;
    private double mainPayout;
    private double percentPayout;
    private double fullPayout;
    private double debt;
}
