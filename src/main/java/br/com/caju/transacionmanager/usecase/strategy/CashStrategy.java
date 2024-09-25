package br.com.caju.transacionmanager.usecase.strategy;


import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CashStrategy extends BalanceAbstract {

    final String CLASSIFICATION = "CASH";

    public Boolean isClassification(String classification) {
        return CLASSIFICATION.equalsIgnoreCase(classification);
    }

    @Override
    public Account processTransaction(Transaction transaction, Account account) throws InsufficientFundsException {
        return this.debitBalanceInAccount(transaction, account);
    }

    @Override
    public Account debitBalanceInAccount(Transaction transaction, Account account) throws InsufficientFundsException {
        log.info("preparing to debit {} in account {},  balance of {}", transaction.getAmount(), account.getAccountId(), CLASSIFICATION);
        return super.debitBalanceDefault(transaction.getAmount(), account);

    }


}
