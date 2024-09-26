package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.TransactionRepository;
import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.mapper.TransactionMapper;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import br.com.caju.transacionmanager.domain.model.TransactionResult;
import br.com.caju.transacionmanager.port.BalanceAmountPort;
import br.com.caju.transacionmanager.port.TransactionPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class TransactiontStrategy implements TransactionPort {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final BalanceAmountPort balanceUseCase;
    private final AccountStrategy accountStrategy;



    @Override
    public ResultDTO authorizeTransaction(TransactionDTO transactionDto) {
        log.info("Request to authorize transaction {}", transactionDto);
        CreditGuardTransaction creditGuardTransaction = transactionMapper.toEntity(transactionDto);
        CreditGuardAccount creditGuardAccount = recoverAccount(creditGuardTransaction.getAccount());

        ResultDTO result = processTransaction(creditGuardTransaction, creditGuardAccount);
        log.info("Transaction {}, result: {}", creditGuardTransaction, result);

        return result;
    }

    private ResultDTO processTransaction(CreditGuardTransaction creditGuardTransaction, CreditGuardAccount creditGuardAccount) {
        ResultDTO result = new ResultDTO();
        try {
            balanceUseCase.processBalance(creditGuardTransaction, creditGuardAccount);
            updateTransactionResult(creditGuardTransaction, TransactionResult.APPROVED);
            result.setCode(TransactionResult.APPROVED.getCode());
            transactionRepository.save(creditGuardTransaction);
        } catch (InsufficientFundsException e) {
            handleInsufficientFunds(creditGuardTransaction, result, e);
        } catch (Exception e) {
            handleGeneralException(creditGuardTransaction, result, e);
        }
        return result;
    }

    private void updateTransactionResult(CreditGuardTransaction creditGuardTransaction, TransactionResult result) {
        creditGuardTransaction.setTransactionResult(result.name());
        creditGuardTransaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(creditGuardTransaction);
    }

    private void handleInsufficientFunds(CreditGuardTransaction creditGuardTransaction, ResultDTO result, InsufficientFundsException e) {
        log.info("Unable to process transaction {}: {}", creditGuardTransaction.getTransactionId(), e.getMessage());
        updateTransactionResult(creditGuardTransaction, TransactionResult.DENIED);
        result.setCode(TransactionResult.DENIED.getCode());
    }

    private void handleGeneralException(CreditGuardTransaction creditGuardTransaction, ResultDTO result, Exception e) {
        log.error(e.getMessage(), e);
        updateTransactionResult(creditGuardTransaction, TransactionResult.UNDETERMINED);
        result.setCode(TransactionResult.UNDETERMINED.getCode());
    }

    private CreditGuardAccount recoverAccount(CreditGuardAccount creditGuardAccount) {
        log.info("Recovering account {}", creditGuardAccount.getAccountId());
        try {
            return accountStrategy.getAccount(creditGuardAccount.getAccountId());
        } catch (AccountNotFoundException e) {
            log.info("Error recovering account: {}", e.getMessage());
            throw e;
        }
    }
}
