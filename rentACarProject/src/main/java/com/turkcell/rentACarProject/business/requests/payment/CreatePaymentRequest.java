package com.turkcell.rentACarProject.business.requests.payment;

import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

    @NotNull
    private CreateCardInfoRequest cardInfo;

    @NotNull
    @Positive
    private long carRentalId;
}
