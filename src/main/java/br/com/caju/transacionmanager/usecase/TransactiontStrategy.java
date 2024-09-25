package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.TransactionRepository;
import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.mapper.TransactionMapper;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
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

    private final AccountStrategy accountStrategy;
    private final BalanceAmountPort balanceUseCase;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public ResultDTO authorizeTransaction(TransactionDTO transactionDto) {
        log.info("Request to authorize transaction {}", transactionDto);
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        Account account = recoverAccount(transaction.getAccount());

        ResultDTO result = processTransaction(transaction, account);
        log.info("Transaction {}, result: {}", transaction, result);

        return result;
    }

    private ResultDTO processTransaction(Transaction transaction, Account account) {
        ResultDTO result = new ResultDTO();
        try {
            balanceUseCase.processBalance(transaction, account);
            updateTransactionResult(transaction, TransactionResult.APPROVED);
            result.setCode(TransactionResult.APPROVED.getCode());
            transactionRepository.save(transaction);
        } catch (InsufficientFundsException e) {
            handleInsufficientFunds(transaction, result, e);
        } catch (Exception e) {
            handleGeneralException(transaction, result, e);
        }
        return result;
    }

    private void updateTransactionResult(Transaction transaction, TransactionResult result) {
        transaction.setTransactionResult(result.name());
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private void handleInsufficientFunds(Transaction transaction, ResultDTO result, InsufficientFundsException e) {
        log.info("Unable to process transaction {}: {}", transaction.getTransactionId(), e.getMessage());
        updateTransactionResult(transaction, TransactionResult.DENIED);
        result.setCode(TransactionResult.DENIED.getCode());
    }

    private void handleGeneralException(Transaction transaction, ResultDTO result, Exception e) {
        log.error(e.getMessage(), e);
        updateTransactionResult(transaction, TransactionResult.UNDETERMINED);
        result.setCode(TransactionResult.UNDETERMINED.getCode());
    }

    private Account recoverAccount(Account account) {
        log.info("Recovering account {}", account.getAccountId());
        try {
            return accountStrategy.getAccount(account.getAccountId());
        } catch (AccountNotFoundException e) {
            log.info("Error recovering account: {}", e.getMessage());
            throw e;
        }
    }
}
