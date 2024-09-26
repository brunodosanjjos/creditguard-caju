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

import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ProcessBalanceStrategy implements BalanceAmountPort {
    private final Set<BalanceStrategy> strategies;
    private final AccountStrategy accountStrategy;
    private final MccPort mccPort;

    @Override
    public void processBalance(Transaction transaction, Account account) throws InsufficientFundsException {
        BalanceStrategy strategy = getClassificationStrategy(transaction);
        Account updatedAccount = strategy.processTransaction(transaction, account);
        accountStrategy.update(updatedAccount);
    }

    private BalanceStrategy getClassificationStrategy(Transaction transaction) {
        log.info("Getting classification for transaction {}", transaction.getTransactionId());
        String merchantClassification = mccPort.findClassification(transaction);

        return strategies.stream()
                .filter(strategy -> strategy.isClassification(merchantClassification))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Classification not found for transaction " + transaction.getTransactionId()));
    }

}
