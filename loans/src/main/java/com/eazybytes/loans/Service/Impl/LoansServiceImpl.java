package com.eazybytes.loans.Service.Impl;

import com.eazybytes.loans.Constants.LoanConstants;
import com.eazybytes.loans.Entity.Loans;
import com.eazybytes.loans.Exception.LoanAlreadyExistsException;
import com.eazybytes.loans.Repository.LoansRepository;
import com.eazybytes.loans.Service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoans(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists with the given mobile number" + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));

        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);

        return newLoan;

    }
}