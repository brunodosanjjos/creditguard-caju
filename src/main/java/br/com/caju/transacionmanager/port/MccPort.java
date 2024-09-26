package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;

public interface MccPort {

    String findClassification(CreditGuardTransaction creditGuardTransaction);
}
