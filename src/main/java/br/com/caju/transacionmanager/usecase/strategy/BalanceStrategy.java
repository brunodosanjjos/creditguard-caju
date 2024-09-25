package br.com.caju.transacionmanager.usecase.strategy;


import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;

public interface BalanceStrategy {

    Boolean isClassification(String classification);

    Account processTransaction(Transaction transaction, Account account) throws InsufficientFundsException;


}
