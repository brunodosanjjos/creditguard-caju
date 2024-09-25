package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.MccRepository;
import br.com.caju.transacionmanager.adapters.out.repository.MerchantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.Optional;

import static br.com.caju.transacionmanager.mock.MockData.getTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MccUseCaseTest {

    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private  MccRepository mccRepository;
    @InjectMocks
    private MccUseCase mccUseCase;

    @Test
    void findClassificationWithMerchant() {
        var transaction = getTransaction();
        transaction.setMerchant("UBER TRIP");

        when(merchantRepository.findMccByMerchantName("UBER TRIP")).thenReturn(Optional.of("mcc"));
        when(mccRepository.findClassificationByMcc("mcc")).thenReturn(Optional.of("CASH"));
        String classification = mccUseCase.findClassification(transaction);

        assertEquals("CASH", classification);
        verify(merchantRepository, times(1)).findMccByMerchantName("UBER TRIP");
        verify(mccRepository, times(1)).findClassificationByMcc("mcc");

    }


    @Test
    void findClassificationMccTransaction() {
        var transaction = getTransaction();
        transaction.setMerchant("UBER EATS");

        when(merchantRepository.findMccByMerchantName("UBER EATS")).thenReturn(Optional.empty());
        when(mccRepository.findClassificationByMcc("mcc")).thenReturn(Optional.of("FOOD"));
        String classification = mccUseCase.findClassification(transaction);

        assertEquals("FOOD", classification);
        verify(merchantRepository, times(1)).findMccByMerchantName("UBER EATS");
        verify(mccRepository, times(1)).findClassificationByMcc("mcc");

    }

    @Test
    void classificationDefault() {
        var transaction = getTransaction();
        transaction.setMerchant("UBER EATS");

        when(merchantRepository.findMccByMerchantName("UBER EATS")).thenReturn(Optional.empty());
        when(mccRepository.findClassificationByMcc("mcc")).thenReturn(Optional.empty());
        String classification = mccUseCase.findClassification(transaction);

        assertEquals("CASH", classification);
        verify(merchantRepository, times(1)).findMccByMerchantName("UBER EATS");
        verify(mccRepository, times(1)).findClassificationByMcc("mcc");

    }

    @Test
    void findClassificationWithMerchantReturnTwo() {
        var transaction = getTransaction();
        transaction.setMerchant("UBER");

        when(merchantRepository.findMccByMerchantName("UBER")).thenThrow(IncorrectResultSizeDataAccessException.class);
        when(mccRepository.findClassificationByMcc("mcc")).thenReturn(Optional.of("FOOD"));
        String classification = mccUseCase.findClassification(transaction);

        assertEquals("FOOD", classification);
        verify(merchantRepository, times(1)).findMccByMerchantName("UBER");
        verify(mccRepository, times(1)).findClassificationByMcc("mcc");

    }


}