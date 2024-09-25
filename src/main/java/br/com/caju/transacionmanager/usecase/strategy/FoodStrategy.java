package br.com.caju.transacionmanager.usecase.strategy;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FoodStrategy extends BalanceAbstract {

    private final String CLASSIFICATION = "FOOD";

    public Boolean isClassification(String classification) {
        return CLASSIFICATION.equalsIgnoreCase(classification);
    }

    @Override
    public Account processTransaction(Transaction transaction, Account account) throws InsufficientFundsException {
        return super.processTransaction(account, transaction);
    }


    public Account debitBalanceInAccount(Transaction transaction, Account account) throws InsufficientFundsException {
        log.info("preparing to debit {} in account {},  balance of {}", transaction.getAmount(), account.getAccountId(), CLASSIFICATION);

        if (!super.hasCashSufficient(transaction.getAmount(), account.getAmountFood())) {
            throw new InsufficientFundsException(String.format("Insufficient balance in %s", CLASSIFICATION));
        }
        var newBalance = super.debit(account.getAmountFood(), transaction.getAmount());
        account.setAmountFood(newBalance);
        return account;
    }
}
