package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
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
    public void processBalance(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        BalanceStrategy strategy = getClassificationStrategy(creditGuardTransaction);
        CreditGuardAccount updatedCreditGuardAccount = strategy.processTransaction(creditGuardTransaction, creditGuardAccount);
        accountStrategy.update(updatedCreditGuardAccount);
    }

    private BalanceStrategy getClassificationStrategy(CreditGuardTransaction creditGuardTransaction) {
        log.info("Getting classification for transaction {}", creditGuardTransaction.getTransactionId());
        String merchantClassification = mccPort.findClassification(creditGuardTransaction);

        return strategies.stream()
                .filter(strategy -> strategy.isClassification(merchantClassification))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Classification not found for transaction " + creditGuardTransaction.getTransactionId()));
    }

}
