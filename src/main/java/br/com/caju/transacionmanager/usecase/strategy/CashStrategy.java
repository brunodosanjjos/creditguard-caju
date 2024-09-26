package br.com.caju.transacionmanager.usecase.strategy;


import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
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
    public CreditGuardAccount processTransaction(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        return this.debitBalanceInAccount(creditGuardTransaction, creditGuardAccount);
    }

    @Override
    public CreditGuardAccount debitBalanceInAccount(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        log.info("preparing to debit {} in account {},  balance of {}", creditGuardTransaction.getAmount(), creditGuardAccount.getAccountId(), CLASSIFICATION);
        return super.debitBalanceDefault(creditGuardTransaction.getAmount(), creditGuardAccount);

    }


}
