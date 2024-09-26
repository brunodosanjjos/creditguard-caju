package br.com.caju.transacionmanager.adapters.out.repository;

import br.com.caju.transacionmanager.domain.model.CreditGuardTransaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<CreditGuardTransaction, String> {
}
