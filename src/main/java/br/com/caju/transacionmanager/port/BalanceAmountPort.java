package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;

public interface BalanceAmountPort {
    void processBalance(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException;
}
