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
public class TransactiontUseCase implements TransactionPort {

    private final AccountUseCase accountUseCase;
    private final BalanceAmountPort balanceUseCase;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public ResultDTO authorizeTransaction(TransactionDTO transactionDto) {
        log.info("request to authorize transaction {}", transactionDto);
        ResultDTO result = new ResultDTO();
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        var account = recoverAccount(transaction.getAccount());
        try {
            balanceUseCase.processBalance(transaction, account);
            result.setCode(TransactionResult.APPROVED.getCode());
            transaction.setTransactionResult(TransactionResult.APPROVED.name());

        } catch (InsufficientFundsException e) {
            log.info("Unable to process transaction {} : {}" ,transaction.getTransactionId(),  e.getMessage());
            result.setCode(TransactionResult.DENIED.getCode());
            transaction.setTransactionResult(TransactionResult.DENIED.name());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transaction.setTransactionResult(TransactionResult.UNDETERMINED.name());
            result.setCode(TransactionResult.UNDETERMINED.getCode());
        }
        log.info("Transaction {}, result: {}", transaction, result);
        transactionRepository.save(transaction);
        return result;
    }


    private Account recoverAccount(Account account) {
        log.info("Recovering account {}", account.getAccountId());
        try {
            return accountUseCase.getAccount(account.getAccountId());

        } catch (AccountNotFoundException e) {
            log.info("Error recovering account: " + e.getMessage());
            throw e;
        }
    }
}
