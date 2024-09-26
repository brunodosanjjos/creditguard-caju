package br.com.caju.transacionmanager.adapters.out.repository;

import br.com.caju.transacionmanager.domain.model.CreditGuardAccount;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<CreditGuardAccount, String> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence..timeout", value = "90")})
    Optional<CreditGuardAccount> findById(String accountNumber);

    <S extends CreditGuardAccount> S save(S entity);

}
