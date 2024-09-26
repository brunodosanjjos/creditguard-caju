package br.com.caju.transacionmanager.adapters.out.repository;

import br.com.caju.transacionmanager.domain.model.Mcc;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MccRepository extends CrudRepository<Mcc, String> {

    @Query("SELECT c.typeName from MccType as c INNER JOIN Mcc as mcc on c.typeId = mcc.classification.typeId WHERE mcc.code = :mcc")
    Optional<String> findClassificationByMcc(@Param("mcc") String mcc);


}
