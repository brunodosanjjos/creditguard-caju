package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;

public interface BalanceAmountPort {
    void processBalance(Transaction transaction, Account account) throws InsufficientFundsException;
}
