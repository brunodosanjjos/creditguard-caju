package br.com.caju.transacionmanager.usecase.strategy;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
abstract class BalanceAbstract implements BalanceStrategy {

    public abstract Boolean isClassification(String classification);

    public abstract Account debitBalanceInAccount(Transaction transaction, Account account) throws InsufficientFundsException;

    protected Boolean hasCashSufficient(BigDecimal amountToDebit, BigDecimal cash) {
        return amountToDebit.compareTo(cash) <= 0;
    }

    public Account processTransaction(Account account, Transaction transaction) throws InsufficientFundsException {
        log.info("process transaction in account {} ", account.getAccountId());
        try {
            return this.debitBalanceInAccount(transaction, account);
        } catch (InsufficientFundsException e) {
            log.info("Insufficient balance, preparing to debit CASH balance ");
            return debitBalanceDefault(transaction.getAmount(), account);
        }
    }

    protected BigDecimal debit(BigDecimal amountAccount, BigDecimal amountTransaction) {
        return amountAccount.subtract(amountTransaction);
    }

    protected Account debitBalanceDefault(BigDecimal amountTransaction, Account account) throws InsufficientFundsException {
        var hasCash = hasCashSufficient(amountTransaction, account.getAmountCash());
        if (hasCash) {
            var newBalance = debit(account.getAmountCash(), amountTransaction);
            log.info("debit cash successfully updated");
            account.setAmountCash(newBalance);
            return account;
        }
        throw new InsufficientFundsException("Unable to debit transaction insufficient balance");
    }


}
