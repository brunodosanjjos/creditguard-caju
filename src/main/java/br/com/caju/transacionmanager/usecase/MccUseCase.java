package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.MccRepository;
import br.com.caju.transacionmanager.adapters.out.repository.MerchantRepository;
import br.com.caju.transacionmanager.domain.model.Transaction;
import br.com.caju.transacionmanager.port.MccPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MccUseCase implements MccPort {

    private final MccRepository mccRepository;
    private final MerchantRepository merchantRepository;


    private String defaultClassification() {
        return "CASH";
    }

    public String findClassification(Transaction transaction) {
        log.info("find classification of  merchant: {} or  mcc:  {}", transaction.getMcc(), transaction.getMerchant());
        String mercantMcc;
        try {
            mercantMcc = merchantRepository.findMccByMerchantName(transaction.getMerchant())
                    .orElseGet(transaction::getMcc);
        } catch (IncorrectResultSizeDataAccessException e) {
            mercantMcc = transaction.getMcc();
        }

        return mccRepository.findClassificationByMcc(mercantMcc)
                .orElseGet(this::defaultClassification);

    }


}
