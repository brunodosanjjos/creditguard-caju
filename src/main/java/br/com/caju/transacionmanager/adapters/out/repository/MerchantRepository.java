package br.com.caju.transacionmanager.adapters.out.repository;

import br.com.caju.transacionmanager.domain.model.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MerchantRepository extends CrudRepository<Merchant, String> {

    @Query(value = "SELECT m.mcc.code FROM Merchant m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<String> findMccByMerchantName(@Param("name") String name);

}
