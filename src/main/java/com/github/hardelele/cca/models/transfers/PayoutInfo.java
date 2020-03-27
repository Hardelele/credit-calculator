package com.github.hardelele.cca.models.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayoutInfo {

    private int counter;
    private double mainPayout;
    private double percentagePart;
}
