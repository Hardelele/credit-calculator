package com.github.hardelele.cca.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditForm {

    double creditAmount;
    int paymentTerm;
}
