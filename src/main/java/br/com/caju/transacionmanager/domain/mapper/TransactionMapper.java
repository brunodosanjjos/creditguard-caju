package br.com.caju.transacionmanager.domain.mapper;

import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.model.Account;
import br.com.caju.transacionmanager.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionMapper {


    @Mapping(source = "account", target = "account", qualifiedByName = "mapIdToAccount")
    @Mapping(source = "totalAmount", target = "amount")
    Transaction toEntity(TransactionDTO transactionDto);


    @Named("mapIdToAccount")
    default Account mapIdToAccount(String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        return account;
    }

}
