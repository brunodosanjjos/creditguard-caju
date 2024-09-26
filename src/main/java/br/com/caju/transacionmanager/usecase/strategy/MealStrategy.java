package br.com.caju.transacionmanager.usecase.strategy;


import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MealStrategy extends BalanceAbstract implements BalanceStrategy {

    private final String CLASSIFICATION = "MEAL";

    public Boolean isClassification(String classification) {
        return CLASSIFICATION.equalsIgnoreCase(classification);
    }

    @Override
    public CreditGuardAccount processTransaction(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        return super.processTransaction(creditGuardAccount, creditGuardTransaction);
    }

    public CreditGuardAccount debitBalanceInAccount(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) throws InsufficientFundsException {
        log.info("preparing to debit {} in account {},  balance of {}", creditGuardTransaction.getAmount(), creditGuardAccount.getAccountId(), CLASSIFICATION);
        // Strategy L2 Challenge
        if (!super.hasCashSufficient(creditGuardTransaction.getAmount(), creditGuardAccount.getAmountMeal())) {
            throw new InsufficientFundsException(String.format("Insufficient balance in %s", CLASSIFICATION));
        }
        var newBalance = super.debit(creditGuardAccount.getAmountMeal(), creditGuardTransaction.getAmount());
        creditGuardAccount.setAmountMeal(newBalance);
        return creditGuardAccount;

    }

}
