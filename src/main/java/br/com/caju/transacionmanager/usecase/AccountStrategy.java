package br.com.caju.transacionmanager.usecase;

import br.com.caju.transacionmanager.adapters.out.repository.AccountRepository;
import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import br.com.caju.transacionmanager.port.AccountPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;


@Service
@AllArgsConstructor
public class AccountStrategy implements AccountPort {

    private final AccountRepository accountRepository;

    @Override
    public CreditGuardAccount getAccount(String acountId) throws AccountNotFoundException {
        return accountRepository
                .findById(acountId)
                .orElseThrow(() -> new AccountNotFoundException(format("Account %s not exists", acountId)));
    }

    @Override
    public CreditGuardAccount update(CreditGuardAccount acountId) {
        return accountRepository
                .save(acountId);
    }


}
