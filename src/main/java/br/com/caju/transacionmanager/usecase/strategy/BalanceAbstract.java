package br.com.caju.transacionmanager.usecase.strategy;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
abstract class BalanceAbstract implements BalanceStrategy {

    public abstract Boolean isClassification(String classification);

    public abstract CreditGuardAccount debitBalanceInAccount(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException;

    protected Boolean hasCashSufficient(BigDecimal amountToDebit, BigDecimal cash) {
        return amountToDebit.compareTo(cash) <= 0;
    }

    public CreditGuardAccount processTransaction(CreditGuardAccount creditGuardAccount, CreditGuardTransaction creditGuardTransaction) throws InsufficientFundsException {
        log.info("process transaction in account {} ", creditGuardAccount.getAccountId());
        try {
            return this.debitBalanceInAccount(creditGuardTransaction, creditGuardAccount);
        } catch (InsufficientFundsException e) {
            log.info("Insufficient balance, preparing to debit CASH balance ");
            return debitBalanceDefault(creditGuardTransaction.getAmount(), creditGuardAccount);
        }
    }

    protected BigDecimal debit(BigDecimal amountAccount, BigDecimal amountTransaction) {
        return amountAccount.subtract(amountTransaction);
    }

    protected CreditGuardAccount debitBalanceDefault(BigDecimal amountTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        var hasCash = hasCashSufficient(amountTransaction, creditGuardAccount.getAmountCash());
        if (hasCash) {
            var newBalance = debit(creditGuardAccount.getAmountCash(), amountTransaction);
            log.info("debit cash successfully updated");
            creditGuardAccount.setAmountCash(newBalance);
            return creditGuardAccount;
        }
        throw new InsufficientFundsException("Unable to debit transaction insufficient balance");
    }


}
