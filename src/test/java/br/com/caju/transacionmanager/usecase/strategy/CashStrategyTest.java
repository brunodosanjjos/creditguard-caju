package br.com.caju.transacionmanager.usecase.strategy;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static br.com.caju.transacionmanager.mock.MockData.getAccount;
import static br.com.caju.transacionmanager.mock.MockData.getTransaction;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CashStrategyTest  {

    @InjectMocks
    private CashStrategy cashStrategy;


    @Test
    void testIsClassification() {
        assertTrue(cashStrategy.isClassification("CASH"));
        assertFalse(cashStrategy.isClassification("FOOD"));
    }

    @Test
    void testProcessTransaction() throws InsufficientFundsException {
        var account = getAccount();
        var accountUpdated = getAccount();
        accountUpdated.setAmountCash(new BigDecimal("446.95"));
        var transaction = getTransaction();
        var result = (cashStrategy.processTransaction(account, transaction));
        assertEquals(accountUpdated, result);

    }

    @Test
    void testDebitBalanceInAccountSufficientFunds() throws InsufficientFundsException {
        var account = getAccount();
        var accountUpdated = getAccount();
        accountUpdated.setAmountCash(new BigDecimal("400.00"));
        var result = cashStrategy.debitBalanceDefault(BigDecimal.valueOf(100.0), account);
        assertEquals(accountUpdated, result);
    }


    @Test
    void testDebitBalanceInAccountInsufficientFunds() {
        var account = getAccount();
        assertThrows(InsufficientFundsException.class,
                ()->cashStrategy.debitBalanceDefault(BigDecimal.valueOf(1000.0), account),
                "Unable to debit transaction insufficient balance");


    }
}