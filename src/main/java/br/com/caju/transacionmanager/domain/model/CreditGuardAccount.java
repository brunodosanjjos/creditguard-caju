package br.com.caju.transacionmanager.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_crgd_account")
@EqualsAndHashCode(onlyExplicitlyIncluded = true )
public class CreditGuardAccount {

    @Id
    @Column(name = "account_id")
    @EqualsAndHashCode.Include
    private String accountId;
    @Column(name = "amount_food")
    private BigDecimal amountFood;

    @Column(name = "amount_meal")
    private BigDecimal amountMeal;

    @Column(name = "amount_cash")
    private BigDecimal amountCash;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditGuardTransaction> creditGuardTransactions;

}
