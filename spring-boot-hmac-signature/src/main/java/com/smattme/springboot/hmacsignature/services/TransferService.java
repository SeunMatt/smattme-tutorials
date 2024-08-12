package com.smattme.springboot.hmacsignature.services;

import com.smattme.springboot.hmacsignature.requests.TransferRequest;
import com.smattme.springboot.hmacsignature.response.TransferResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    public TransferResponse transferFund(TransferRequest transferRequest) {
        //do the actual debit, and send funds to beneficiary
        //....
        return new TransferResponse(true, HttpStatus.OK.value(),
                "Fund transferred successfully",
                transferRequest.reference());

    }
}
