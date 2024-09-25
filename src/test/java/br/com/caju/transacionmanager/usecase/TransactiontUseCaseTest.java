package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.TransactionRepository;
import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.domain.mapper.TransactionMapper;
import br.com.caju.transacionmanager.domain.model.TransactionResult;
import br.com.caju.transacionmanager.port.BalanceAmountPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.caju.transacionmanager.mock.MockData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class TransactiontUseCaseTest {
    @Mock
    private AccountUseCase accountUseCase;

    @Mock
    private BalanceAmountPort balanceUseCase;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactiontUseCase transactiontUseCase;


    @Test
    void testAuthorizeTransactionApproved() throws InsufficientFundsException {
        var transactionDto = getTransactiondDTO();
        var transaction = getTransaction();
        var account =  getAccount();
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(accountUseCase.getAccount(any())).thenReturn(account);
        doNothing().when(balanceUseCase).processBalance(transaction,account);
        ResultDTO result = transactiontUseCase.authorizeTransaction(transactionDto);

        assertEquals(TransactionResult.APPROVED.getCode(), result.getCode());
        assertEquals(TransactionResult.APPROVED.name(), transaction.getTransactionResult());
        verify(transactionRepository, times(2)).save(transaction);
    }

    @Test
    void testAuthorizeTransactionaccountDenied() throws InsufficientFundsException {
        var transaction = getTransaction();
        var transactionDto =  getTransactiondDTO();

        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        doThrow(InsufficientFundsException.class).when(balanceUseCase).processBalance(any(), any());

        ResultDTO result = transactiontUseCase.authorizeTransaction(transactionDto);
        assertEquals(TransactionResult.DENIED.getCode(), result.getCode());
        assertEquals(TransactionResult.DENIED.name(), transaction.getTransactionResult());
        verify(transactionRepository, times(2)).save(transaction);
    }

    @Test
    void testAuthorizeTransactionUndetermined() throws InsufficientFundsException {
        var transactionDto = getTransactiondDTO();
        var transaction = getTransaction();
        var account =  getAccount();
        when(accountUseCase.getAccount(account.getAccountId())).thenReturn(account);
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        doThrow(RuntimeException.class).when(balanceUseCase).processBalance(transaction, account);
        transactiontUseCase.authorizeTransaction(transactionDto);
        assertEquals(TransactionResult.UNDETERMINED.name(), transaction.getTransactionResult());
        verify(transactionRepository, times(2)).save(transaction);
    }

}