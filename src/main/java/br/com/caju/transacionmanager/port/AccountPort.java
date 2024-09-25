package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.model.Account;

public interface AccountPort {
    Account getAccount(String acountId) throws AccountNotFoundException;

    Account update(Account acountId);


}
