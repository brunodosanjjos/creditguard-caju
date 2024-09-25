package br.com.caju.transacionmanager.mock;

import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public class MockData {

    public static Transaction getTransaction(){
         return Transaction
                .builder()
                .transactionId(UUID.randomUUID().toString())
                .mcc("mcc")
                 .amount(new BigDecimal("53.05"))
                .account(getAccount())
                .build();
    }
    public static Account getAccount(){
        return Account.builder()
                .accountId("1234")
                .amountCash(new BigDecimal("500.00"))
                .amountFood(new BigDecimal("700.00"))
                .amountMeal(new BigDecimal("900.00"))
                .build();
    }

    public static TransactionDTO getTransactiondDTO(){
        return TransactionDTO
                .builder()
                .account("1234")
                .mcc("mcc")
                .merchant("MAC DONALD'S")
                .merchant("MAC DONALD'S")
                .totalAmount(new BigDecimal("53.05"))
                .build();
    }
}
