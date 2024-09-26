package br.com.caju.transacionmanager.domain.mapper;

import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionMapper {


    @Mapping(source = "account", target = "account", qualifiedByName = "mapIdToAccount")
    @Mapping(source = "totalAmount", target = "amount")
    CreditGuardTransaction toEntity(TransactionDTO transactionDto);


    @Named("mapIdToAccount")
    default CreditGuardAccount mapIdToAccount(String accountId) {
        CreditGuardAccount creditGuardAccount = new CreditGuardAccount();
        creditGuardAccount.setAccountId(accountId);
        return creditGuardAccount;
    }

}
