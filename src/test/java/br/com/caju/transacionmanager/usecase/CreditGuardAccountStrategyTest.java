package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.AccountRepository;
import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
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
class CreditGuardAccountStrategyTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountStrategy accountStrategy;

    @Test
    void testGetAccountWithSuccess(){
        CreditGuardAccount creditGuardAccount = getAccount();
        when(accountRepository.findById(creditGuardAccount.getAccountId())).thenReturn(Optional.of(creditGuardAccount));
        var result = accountStrategy.getAccount(creditGuardAccount.getAccountId());
        assertNotNull(result);
        assertEquals(creditGuardAccount.getAccountId(), result.getAccountId());
        verify(accountRepository, times(1)).findById(creditGuardAccount.getAccountId());
    }

    @Test
    void testAccountNotFound(){
        CreditGuardAccount creditGuardAccount = getAccount();
        doThrow(AccountNotFoundException.class).when(accountRepository).findById(creditGuardAccount.getAccountId());
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountStrategy.getAccount(creditGuardAccount.getAccountId()),
                        "Account 1234 not exists");
        verify(accountRepository, times(1)).findById(creditGuardAccount.getAccountId());
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