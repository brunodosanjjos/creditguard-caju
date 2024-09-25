package br.com.caju.transacionmanager.domain.mapper;

import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.model.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-25T15:41:57-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toEntity(TransactionDTO transactionDto) {
        if ( transactionDto == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.account( mapIdToAccount( transactionDto.getAccount() ) );
        transaction.amount( transactionDto.getTotalAmount() );
        transaction.mcc( transactionDto.getMcc() );
        transaction.merchant( transactionDto.getMerchant() );

        return transaction.build();
    }
}
