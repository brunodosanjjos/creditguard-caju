package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.AccountRepository;
import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.caju.transacionmanager.mock.MockData.getAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountStrategyTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountStrategy accountStrategy;

    @Test
    void testGetAccountWithSuccess(){
        Account account = getAccount();
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        var result = accountStrategy.getAccount(account.getAccountId());
        assertNotNull(result);
        assertEquals(account.getAccountId(), result.getAccountId());
        verify(accountRepository, times(1)).findById(account.getAccountId());
    }

    @Test
    void testAccountNotFound(){
        Account account = getAccount();
        doThrow(AccountNotFoundException.class).when(accountRepository).findById(account.getAccountId());
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountStrategy.getAccount(account.getAccountId()),
                        "Account 1234 not exists");
        verify(accountRepository, times(1)).findById(account.getAccountId());
    }

    @Test
    void updateAccountWithSuccess() {
        var account = getAccount();
        account.setAmountFood(new BigDecimal("100"));
        when(accountRepository.save(account)).thenReturn(account);
        var result = accountStrategy.update(account);
        assertNotNull(result);
        assertEquals("1234", result.getAccountId());
        assertEquals(new BigDecimal("100"), result.getAmountFood());
        verify(accountRepository, times(1)).save(account);
    }

}