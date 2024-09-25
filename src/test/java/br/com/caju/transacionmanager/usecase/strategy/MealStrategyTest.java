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
class MealStrategyTest {

    @InjectMocks
    private MealStrategy strategy;

    @Test
    void testIsClassification() {
        assertTrue(strategy.isClassification("MEAL"));
        assertFalse(strategy.isClassification("CASH"));
    }

    @Test
    void testProcessTransaction() throws InsufficientFundsException {
        var account = getAccount();
        var accountUpdated = getAccount();
        accountUpdated.setAmountMeal(new BigDecimal("846.95"));
        var transaction = getTransaction();
        var result = (strategy.processTransaction(account, transaction));
        assertEquals(accountUpdated, result);

    }

    @Test
    void tesProcessWithDebitCash() throws InsufficientFundsException {
        var account = getAccount();
        var accountUpdated = getAccount();
        accountUpdated.setAmountCash(new BigDecimal("446.95"));
        account.setAmountMeal(new BigDecimal("0.0"));
        accountUpdated.setAmountMeal(new BigDecimal("0.0"));
        var transaction = getTransaction();
        var result =  strategy.processTransaction(transaction, account);
        assertEquals(result.getAmountCash(), accountUpdated.getAmountCash());

    }

    @Test
    void testDebitBalanceInAccountSufficientFunds() throws InsufficientFundsException {
        var account = getAccount();
        var transaction = getTransaction();
        var accountUpdated = getAccount();
        accountUpdated.setAmountMeal(new BigDecimal("846.95"));
        var result = strategy.debitBalanceInAccount(transaction, account);
        assertEquals(accountUpdated, result);
    }


    @Test
    void testDebitBalanceInAccountInsufficientFunds(){
        var account = getAccount();
        var transaction = getTransaction();
        transaction.setAmount(new BigDecimal("1000.00"));
        assertThrows(InsufficientFundsException.class,
                ()->strategy.debitBalanceInAccount(transaction, account),
                "Unable to debit transaction insufficient balance");

    }

}