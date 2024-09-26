package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.exception.AccountNotFoundException;
import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;

public interface AccountPort {
    CreditGuardAccount getAccount(String acountId) throws AccountNotFoundException;

    CreditGuardAccount update(CreditGuardAccount acountId);


}
