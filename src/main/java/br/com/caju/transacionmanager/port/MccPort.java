package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.model.Transaction;

public interface MccPort {

    String findClassification(Transaction transaction);
}
