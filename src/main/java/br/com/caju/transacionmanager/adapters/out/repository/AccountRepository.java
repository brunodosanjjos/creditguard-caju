package br.com.caju.transacionmanager.adapters.out.repository;

import br.com.caju.transacionmanager.domain.model.Account;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence..timeout", value = "90")})
    Optional<Account> findById(String accountNumber);

    <S extends Account> S save(S entity);

}
