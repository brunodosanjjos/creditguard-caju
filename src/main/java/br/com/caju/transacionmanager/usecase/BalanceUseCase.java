package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
import br.com.caju.transacionmanager.port.BalanceAmountPort;
import br.com.caju.transacionmanager.port.MccPort;
import br.com.caju.transacionmanager.usecase.strategy.BalanceStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class BalanceUseCase implements BalanceAmountPort {
    private final MccPort mccPort;
    private final Set<BalanceStrategy> strategies;
    private final AccountUseCase accountUseCase;


    @Override
    public void processBalance(Transaction transaction, Account account) throws InsufficientFundsException {
        var strategy = getClassification(transaction);
        var accountUpdate =  strategy.processTransaction(transaction, account);
        accountUseCase.update(accountUpdate);
    }

    private BalanceStrategy getClassification(Transaction transaction) throws RuntimeException {
        log.info("get classification of transaction {}", transaction.getTransactionId());
        String merchantClassification = mccPort.findClassification(transaction);
        log.info("transaction {} type  {}",transaction.getTransactionId(),  merchantClassification);
        Optional<BalanceStrategy> strategyBalance = strategies.stream()
                .filter(strategy -> strategy.isClassification(merchantClassification))
                .findFirst();

        if (strategyBalance.isPresent()) {
            return strategyBalance.get();
        }
        throw new RuntimeException("classification not found");
    }

}
