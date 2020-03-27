package com.github.hardelele.cca.models.transfers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditInfo {

    private double percent;
    private double monthCoefficient;
    private double annuityPayment;
    private int paymentTerm;
    private double creditRemainder;
}
