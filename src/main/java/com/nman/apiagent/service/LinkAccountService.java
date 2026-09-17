package com.nman.apiagent.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nman.apiagent.dto.LinkAccountRequest;
import com.nman.apiagent.dto.LinkAccountResponse;
import com.nman.apiagent.entity.LinkedBankAccount;
import com.nman.apiagent.repository.LinkedBankAccountRepository;

@Service
public class LinkAccountService {

    private final LinkedBankAccountRepository bankAccountRepository;

    public LinkAccountService(
            LinkedBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public LinkAccountResponse linkAccount(LinkAccountRequest request) {

        if (!request.isConsent()) {
            throw new IllegalArgumentException(
                    "Customer consent is required");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (request.getMobileNumber() == null
                || request.getMobileNumber().isBlank()) {
            throw new IllegalArgumentException("Mobile number is required");
        }

        if (request.getAccountNumber() == null
                || request.getAccountNumber().isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }

        if (request.getBankIfsc() == null
                || request.getBankIfsc().isBlank()) {
            throw new IllegalArgumentException("IFSC is required");
        }

        // Check if account is already linked
        bankAccountRepository
                .findByMobileNumberAndIfsc(
                        request.getMobileNumber(),
                        request.getBankIfsc())
                .ifPresent(account -> {
                    throw new IllegalArgumentException(
                            "Bank account is already linked");
                });

        String accountId = "ACC-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String bankingName = getBankName(request.getBankIfsc());

        LinkedBankAccount account = new LinkedBankAccount();

        account.setAccountId(accountId);
        account.setCustomerName(request.getName());
        account.setMobileNumber(request.getMobileNumber());
        account.setIfsc(request.getBankIfsc());
        account.setBankName(bankingName);

        /*
         * In a real banking application, do NOT store
         * the full account number as plain text.
         *
         * This example keeps it simple for your local H2
         * development project.
         */
        account.setAccountNumber(request.getAccountNumber());

        bankAccountRepository.save(account);

        String maskedAccount =
                maskAccountNumber(request.getAccountNumber());

        LinkAccountResponse.Data data =
                new LinkAccountResponse.Data(
                        maskedAccount,
                        request.getBankIfsc(),
                        bankingName,
                        request.getName(),
                        "LINKED"
                );

        return new LinkAccountResponse(
                true,
                "Bank account linked successfully",
                request.getReferenceId(),
                data
        );
    }

    private String getBankName(String ifsc) {

        if (ifsc.startsWith("HDFC")) {
            return "HDFC Bank";
        }

        if (ifsc.startsWith("SBIN")) {
            return "State Bank of India";
        }

        if (ifsc.startsWith("ICIC")) {
            return "ICICI Bank";
        }

        return "Unknown Bank";
    }

    private String maskAccountNumber(String accountNumber) {

        if (accountNumber.length() <= 4) {
            return "XXXX";
        }

        return "XXXXXX" +
                accountNumber.substring(
                        accountNumber.length() - 4);
    }
}
