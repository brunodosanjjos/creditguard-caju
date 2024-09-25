package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.domain.exception.InsufficientFundsException;
import br.com.caju.transacionmanager.port.MccPort;
import br.com.caju.transacionmanager.usecase.strategy.BalanceStrategy;
import br.com.caju.transacionmanager.usecase.strategy.CashStrategy;
import br.com.caju.transacionmanager.usecase.strategy.FoodStrategy;
import br.com.caju.transacionmanager.usecase.strategy.MealStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static br.com.caju.transacionmanager.mock.MockData.getAccount;
import static br.com.caju.transacionmanager.mock.MockData.getTransaction;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceUseCaseTest {

    @Mock
    private MccPort mccPort;
    @Mock
    private CashStrategy cashStrategy;
    @Mock
    private FoodStrategy foodStrategy;
    @Mock
    private MealStrategy mealStrategy;
    @Mock
    private AccountUseCase accountUseCase;

    private Set<BalanceStrategy> strategies;
    @InjectMocks
    private BalanceUseCase balanceUseCase;


    @BeforeEach
    void setUp() {
        strategies = Set.of(cashStrategy, foodStrategy, mealStrategy);
        ReflectionTestUtils.setField(balanceUseCase, "strategies", strategies);

    }

    @Test
    void testProcessBalanceFood() throws InsufficientFundsException {
        var transaction = getTransaction();
        var account = getAccount();
        when(mccPort.findClassification(transaction)).thenReturn("FOOD");
        when(foodStrategy.isClassification("FOOD")).thenReturn(true);
        balanceUseCase.processBalance(transaction, account);
        verify(foodStrategy, times(1)).processTransaction(transaction, account);
        verify(accountUseCase, times(1)).update(any());
    }

    @Test
    void testProcessBalanceMeal() throws InsufficientFundsException {
        var transaction = getTransaction();
        var account = getAccount();
        when(mccPort.findClassification(transaction)).thenReturn("MEAL");
        when(mealStrategy.isClassification("MEAL")).thenReturn(true);
        balanceUseCase.processBalance(transaction, account);
        verify(mealStrategy, times(1)).processTransaction(transaction, account);
        verify(accountUseCase, times(1)).update(any());

    }

    @Test
    void testProcessBalanceCash() throws InsufficientFundsException {
        var transaction = getTransaction();
        var account = getAccount();
        when(mccPort.findClassification(transaction)).thenReturn("CASH");
        when(cashStrategy.isClassification("CASH")).thenReturn(true);
        balanceUseCase.processBalance(transaction, account);
        verify(cashStrategy, times(1)).processTransaction(transaction, account);
        verify(accountUseCase, times(1)).update(any());

    }

    @Test
    void testProcessBalanceCalssificationNotfound()  {
        var transaction = getTransaction();
        var account = getAccount();
        when(mccPort.findClassification(transaction)).thenReturn("CASH");
        assertThrows(RuntimeException.class, () ->
                balanceUseCase.processBalance(transaction, account),
                "classification not found");
        verifyNoInteractions(accountUseCase);

    }
}