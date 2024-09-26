package br.com.caju.transacionmanager.usecase.strategy;


import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;

public interface BalanceStrategy {

    Boolean isClassification(String classification);

    CreditGuardAccount processTransaction(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException;


}
