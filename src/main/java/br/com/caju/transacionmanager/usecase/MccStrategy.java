package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.MccRepository;
import br.com.caju.transacionmanager.adapters.out.repository.MerchantRepository;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import br.com.caju.transacionmanager.port.MccPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MccStrategy implements MccPort {

    private final MccRepository mccRepository;
    private final MerchantRepository merchantRepository;

    private String defaultClassification() {
        return "CASH";
    }

    public String findClassification(CreditGuardTransaction creditGuardTransaction) {
        log.info("Finding classification for merchant: {} or MCC: {}", creditGuardTransaction.getMerchant(), creditGuardTransaction.getMcc());
        String merchantMcc = getMerchantMccForName(creditGuardTransaction);
        return getClassificationByMcc(merchantMcc);
    }

    private String getMerchantMccForName(CreditGuardTransaction creditGuardTransaction) {
        // Strategy L1 and L3 Challenge
        try {
            return merchantRepository.findMccByMerchantName(creditGuardTransaction.getMerchant())
                    .orElseGet(creditGuardTransaction::getMcc);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.warn("Multiple results found for merchant: {}. Using transaction MCC: {}", creditGuardTransaction.getMerchant(), creditGuardTransaction.getMcc());
            return creditGuardTransaction.getMcc();
        }
    }

    private String getClassificationByMcc(String mcc) {
        return mccRepository.findClassificationByMcc(mcc)
                .orElseGet(this::defaultClassification);
    }


}
